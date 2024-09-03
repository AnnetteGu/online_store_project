package com.annette.spring.project.online_store.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annette.spring.project.online_store.entity.Category;
import com.annette.spring.project.online_store.entity.Product;
import com.annette.spring.project.online_store.repository.CategoryRepository;
import com.annette.spring.project.online_store.repository.ProductRepoCustom;
import com.annette.spring.project.online_store.repository.ProductRepository;

@Service
public class ProductServiceImpl extends BaseService implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepoCustom productRepoCustom;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Map<String, Object>> getAllProducts() {
        
        return productRepoCustom.findAllCustom();

    }

    @Override
    public List<Map<String, Object>> getAllProductsByCategory(String category) {
        
        return productRepoCustom.findByCategory(category);

    }

    @Override
    public Map<String, Object> getProduct(int id) {
        
        return productRepoCustom.findByIdCustom(id);

    }

    @Override
    public Product getProductByName(String name) {

        Optional<Product> optional = productRepository.findByName(name);
        Product product = null;

        if (optional.isPresent()) product = optional.get();
        
        return product;

    }

    @Override
    public Product addProduct(Product product) {
        
        return productRepository.save(product);

    }

    @Override
    public String addProductCategory(String idData) {

        Map<String, Object> resultMap = jsonToMap(idData);

        int productId = (Integer) resultMap.get("productId");
        int categoryId = (Integer) resultMap.get("categoryId");
        
        Product product = productRepository.findById(productId).get();
        Category category = categoryRepository.findById(categoryId).get();

        List<Product> productsInCategory = category.getProductsInCategory();

        if (productsInCategory.indexOf(product) == -1) {

            List<Category> categories = product.getCategories();

            categories.add(category);

            product.setCategories(categories);

            productRepository.save(product);

            return "Товар " + product.getName() + " был добавлен в категорию " + category.getName();

        }
        else return "Товар " + product.getName() + " уже был добавлен в категорию " + category.getName();

    }

    @Override
    public Product updateProduct(String fields, int id) {
        
        Map<String, Object> resultMap = jsonToMap(fields);

        Product product = productRepository.findById(id).get();

        for (Map.Entry<String, Object> field : resultMap.entrySet()) {
            switch (field.getKey()) {
                case "sellerId":
                    product.setSellerId((Integer) field.getValue());
                    break;
                case "name":
                    product.setName((String) field.getValue());
                    break;
                case "price":
                    product.setPrice((Integer) field.getValue());
                    break;
                case "isAllowed":
                    product.setIsAllowed((Boolean) field.getValue());
                    break;
                default:
                    System.out.println("Такого поля нет");
                    break;
            }
        }

        return productRepository.save(product);

    }

    @Override
    public void deleteProduct(int id) {
        
        productRepository.deleteById(id);

    }

}
