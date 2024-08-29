package com.annette.spring.project.online_store.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Basket;
import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.entity.PurchaseHistory;
import com.annette.spring.project.online_store.exception_handling.NoSuchProductException;
import com.annette.spring.project.online_store.repository.ProductRepository;
import com.annette.spring.project.online_store.repository.PurchaseHistoryRepository;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketServiceImpl basketServiceImpl;

    @Override
    public List<PurchaseHistory> getUserPurchaseHistory(int userId) {

        return getCustomHistories(userId, 'u');

    }

    @SuppressWarnings("null")
    @Override
    public Map<String, Object> getPurchaseHistoryProduct(int userId, String productName) {
        
        List<PurchaseHistory> userHistories = getCustomHistories(userId, 'u');
        Product product = null;

        for (PurchaseHistory history : userHistories) {

            if (history.getProductName().equals(productName)) {
                product = productRepository.findByName(productName).get();

                if (product == null) 
                    throw new NoSuchProductException("Такого товара нет");
                
                break;
            } else
                throw new NoSuchProductException("Такой товар не покупался");   
                
        }

        Map<String, Object> productMap = fillMap('p');

        productMap.put("id", product.getId());
        productMap.put("sellerId", product.getSellerId());
        productMap.put("name", product.getName());
        productMap.put("price", product.getPrice());
        productMap.put("isAllowed", product.getIsAllowed());

        return productMap;

    }

    @Override
    public String addProductInHistory(Product product, int userId) {
        
        PurchaseHistory purchaseHistory = new PurchaseHistory();

        purchaseHistory.setProductId(product.getId());
        purchaseHistory.setSellerId(product.getSellerId());
        purchaseHistory.setCustomerId(userId);
        purchaseHistory.setProductName(product.getName());

        List<Basket> userBaskets = basketServiceImpl.getUserBaskets(userId);
        int productAmount = 0;

        for (Basket basket : userBaskets) {
            if (product.getId() == basket.getProductId())
                productAmount = basket.getProductAmount();
        }

        purchaseHistory.setProductAmount(productAmount);
        purchaseHistory.setActualPrice(product.getPrice());

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss")
            .format(Calendar.getInstance().getTime());

        purchaseHistory.setPurchaseDate(timeStamp);

        StringBuilder categories = new StringBuilder();

        for (Category category : product.getCategories()) {
            categories.append(category.getName() + " ");
        }

        purchaseHistory.setCategoryName(categories.toString());

        purchaseHistoryRepository.save(purchaseHistory);

        return "Товар был добавлен в историю покупок";

    }

    // не уверена, что этот метод вообще нужен
    @Override
    public String updateProductInHistory(int id, String flag) {

        List<PurchaseHistory> allHistories = purchaseHistoryRepository.findAll();
        PurchaseHistory history;

        switch (flag) {
            case "user":
                for (PurchaseHistory ph : allHistories) {
                    if (ph.getCustomerId() == id) {
                        history = purchaseHistoryRepository.findById(ph.getId()).get();
                        history.setCustomerId(0);
                        purchaseHistoryRepository.save(history);
                    }
                }
                break;
            case "seller":
                for (PurchaseHistory ph : allHistories) {
                    if (ph.getSellerId() == id) {
                        history = purchaseHistoryRepository.findById(ph.getId()).get();
                        history.setSellerId(0);
                        purchaseHistoryRepository.save(history);
                    }
                }
                break;
            case "product":
                for (PurchaseHistory ph : allHistories) {
                    if (ph.getProductId() == id) {
                        history = purchaseHistoryRepository.findById(ph.getId()).get();
                        history.setProductId(0);
                        purchaseHistoryRepository.save(history);
                    }
                }
                break;
            default:
                System.out.println("Неверное поле");
                break;
        }

        String result;

        if (flag.equals("user")) result = "Покупатель";
        else if (flag.equals("seller")) result = "Продавец";
        else result = "Товар";

        return result + " c id = " + id + " был обновлён в истории покупок";

    }

    @Override
    public PurchaseHistory getLastProductFromHistory(int userId) {
        
        List<PurchaseHistory> userHistories = getCustomHistories(userId, 'u');

        return userHistories.get(userHistories.size() - 1);

    }

    @Override
    public PurchaseHistory getLastProductFromHistoryWithoutReview(int userId) {

        // взять список купленных товаров пользователем
        // пойти по списку с конца
        // взять товар
        // взять список отзывов этого товара
        // если среди отзывов нет отзыва нашего пользователя - выводим

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastProductFromHistoryWithoutReview'");
    }

    @Override
    public List<Map<String, Object>> getSellerTotalSum(int sellerId) {

        List<PurchaseHistory> sellerHistories = getCustomHistories(sellerId, 's');
        List<Map<String, Object>> resultSumMap = new ArrayList<>();
        Map<String, Object> resultMap = fillMap('s');
        Set<Integer> markedProducts = new HashSet<>();
        int totalSum = 0;

        Map<String, Integer> sumAndAmount;

        for (PurchaseHistory ph : sellerHistories) {

            if (!markedProducts.contains(ph.getProductId())) {

                resultMap.put("productId", ph.getProductId());
                resultMap.put("productName", ph.getProductName());

                sumAndAmount = getProductTotalSumAndAmount(sellerHistories, ph.getProductId());

                resultMap.put("productAmount", sumAndAmount.get("productAmount"));
                resultMap.put("productRevenue", sumAndAmount.get("productRevenue"));

                totalSum += sumAndAmount.get("productRevenue");

                markedProducts.add(ph.getProductId());
                resultSumMap.add(resultMap);
                resultMap = fillMap('s');

            }
            
        }

        resultSumMap.add(Map.of("totalSum", totalSum));

        return resultSumMap;

    }

    private List<PurchaseHistory> getCustomHistories(int id, char type) {

        List<PurchaseHistory> allHistories = purchaseHistoryRepository.findAll();
        List<PurchaseHistory> customHistories = new ArrayList<>();

        switch (type) {
            case 'u':
                for (PurchaseHistory history : allHistories) {
                    if (history.getCustomerId() == id)
                        customHistories.add(history);
                }
                break;
            case 's':
                for (PurchaseHistory history : allHistories) {
                    if (history.getSellerId() == id)
                        customHistories.add(history);
                }
                break;
            default:
                break;
        }

        return customHistories;

    }

    private Map<String, Object> fillMap(char type) {

        Map<String, Object> map = new LinkedHashMap<>();
        Object tmp = new Object();

        switch (type) {
            case 'p':
                map.put("id", tmp);
                map.put("sellerId", tmp);
                map.put("name", tmp);
                map.put("price", tmp);
                map.put("isAllowed", tmp);
                break;
            case 's':
                map.put("productId", tmp);
                map.put("productName", tmp);
                map.put("productAmount", tmp);
                map.put("productRevenue", tmp);
                break;
            default:
                break;
        }

        return map;

    }

    private Map<String, Integer> getProductTotalSumAndAmount(List<PurchaseHistory> histories, int productId) {

        int productRevenue = 0;
        int productAmount = 0;
        Map<String, Integer> result = new HashMap<>();

        for (PurchaseHistory ph : histories) {
            if (ph.getProductId() == productId) {
                productRevenue += (ph.getActualPrice() * ph.getProductAmount());
                productAmount += ph.getProductAmount();
            }
        }

        result.put("productRevenue", productRevenue);
        result.put("productAmount", productAmount);

        return result;

    }

}
