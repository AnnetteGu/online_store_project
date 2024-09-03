package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Basket;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.entity.Settings;
import com.annette.spring.project.online_store.entity.User;
import com.annette.spring.project.online_store.exception_handling.UserBadAuthoritiesException;
import com.annette.spring.project.online_store.repository.UserRepository;
import com.annette.spring.project.online_store.repository.BasketRepository;
import com.annette.spring.project.online_store.repository.ProductRepository;
import com.annette.spring.project.online_store.repository.UserRepoCustom;

@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private UserRepoCustom userRepoCustom;

    @Autowired
    private BasketServiceImpl basketServiceImpl;

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @Override
    public List<Map<String, Object>> getAllUsers() {

        return userRepoCustom.findAllCustom();

    }

    @Override
    public Map<String, Object> getUser(int id) {

        return userRepoCustom.findByIdCustom(id);
       
    }

    @Override
    public Settings getUserSettings(int id) {

        Optional<User> optional = userRepository.findById(id);

        User user = new User();

        if (optional.isPresent()) user = optional.get();

        return user.getSettings();

    }

    @Override
    public User getUserByLogin(String login) {

        Optional<User> optional = userRepository.findByLogin(login);

        User user = null;

        if (optional.isPresent()) user = optional.get();

        return user;
        
    }

    @Override
    public User addUser(User user) {

        Settings settings = new Settings();
        settings.setTheme("day");
        settings.setLanguage("ru");

        user.setSettings(settings);
        
        userRepository.save(user);

        return user;
        
    }

    @Override
    public User updateUser(String fields, int id) {

        Map<String, Object> resultMap = jsonToMap(fields);

        User currentUser = userRepository.findById(id).get();

        for (Map.Entry<String, Object> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "name":
                    currentUser.setName((String) field.getValue());    
                    break;
                case "login":
                    currentUser.setLogin((String) field.getValue());     
                    break;
                case "password":
                    currentUser.setPassword((String) field.getValue());     
                    break;
                case "balance":
                    throw new UserBadAuthoritiesException("Вы не можете изменить это поле"); 
                case "role":
                    currentUser.setRole((String) field.getValue());   
                    break;
                case "accessMode":
                    currentUser.setAccessMode((Boolean) field.getValue());     
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        userRepository.save(currentUser);

        return currentUser;
        
    }

    @Override
    public User updateUserSettings(String fields, int id) {

        Map<String, Object> resultMap = jsonToMap(fields);

        User currentUser = userRepository.findById(id).get();

        Settings settings = currentUser.getSettings();

        for (Map.Entry<String, Object> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "theme":
                    settings.setTheme((String) field.getValue());
                    break;
                case "language":
                    settings.setLanguage((String) field.getValue());
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        currentUser.setSettings(settings);
        userRepository.save(currentUser);

        return currentUser;

    }

    @Override
    public void deleteUser(int id) {

        userRepository.deleteById(id);
        
    }

    @Override
    public String refillBalance(String userData) {
        
        Map<String, Object> resultMap = jsonToMap(userData);

        int userId = (Integer) resultMap.get("userId");
        double userNewBalance = (Double) resultMap.get("userBalance");

        User user = null;

        try {
            user = userRepository.findById(userId).get();
            user.setBalance(userNewBalance);
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("Что-то не так с юзером");
        }

        return "Баланс пользователя с id = " + userId + " был пополнен";

    }

    @Override
    public String purchaseProducts(int userId) {

        User user = userRepository.findById(userId).get();
        List<Basket> userBaskets = basketServiceImpl.getUserBaskets(userId);
        Product product;
        int productId;

        double userBalance = user.getBalance();
        double totalSum = basketServiceImpl.getTotalSum(userId);

        if (userBalance >= totalSum) {
            userBalance -= totalSum;
            user.setBalance(userBalance);
            userRepository.save(user);

            for (Basket basket : userBaskets) {

                productId = basket.getProductId();
                product = productRepository.findById(productId).get();

                try {
                    purchaseHistoryService.addProductInHistory(product, userId);
                    basketRepository.deleteById(basket.getId());
                } catch (Exception e) {
                    System.out.println("Что-то пошло не так");
                    e.printStackTrace();
                }
                
            }

            return "Покупка успешно совершена";
        } else
            return "На балансе недостаточно средств";
        
    }

}
