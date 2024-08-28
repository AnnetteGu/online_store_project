package com.annette.spring.project.online_store.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.entity.PurchaseHistory;
import com.annette.spring.project.online_store.repository.ProductRepository;
import com.annette.spring.project.online_store.repository.PurchaseHistoryRepository;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    @Autowired
    PurchaseHistoryRepository purchaseHistoryRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Map<String, Object>> getUserPurchaseHistory(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUserPurchaseHistory'");
    }

    @Override
    public Map<String, Object> getPurchaseHistoryProduct(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPurchaseHistoryProduct'");
    }

    @Override
    public String addProductInHistory(Product product, int userId) {
        
        PurchaseHistory purchaseHistory = new PurchaseHistory();

        purchaseHistory.setProductId(product.getId());
        purchaseHistory.setCustomerId(userId);
        purchaseHistory.setProductName(product.getName());
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

    @Override
    public String updateProductInHistory(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProductInHistory'");
    }

    @Override
    public PurchaseHistory getLastProductFromHistory(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastProductFromHistory'");
    }

    @Override
    public PurchaseHistory getLastProductFromHistoryWithoutReview(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLastProductFromHistoryWithoutReview'");
    }

    @Override
    public List<Map<String, Object>> getSellerTotalSum(int userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSellerTotalSum'");
    }

}
