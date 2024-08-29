package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;

import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.entity.PurchaseHistory;

public interface PurchaseHistoryService {

    public List<PurchaseHistory> getUserPurchaseHistory(int userId);

    public Map<String, Object> getPurchaseHistoryProduct(int userId, String productName);

    public String addProductInHistory(Product product, int userId);

    public String updateProductInHistory(int id, String flag);

    public PurchaseHistory getLastProductFromHistory(int userId);

    public PurchaseHistory getLastProductFromHistoryWithoutReview(int userId);

    public List<Map<String, Object>> getSellerTotalSum(int sellerId);

}
