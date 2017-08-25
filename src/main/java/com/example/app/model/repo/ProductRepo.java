package com.example.app.model.repo;

import com.example.app.model.config.ProductConfig;
import com.example.app.model.entity.Campaign;
import com.example.app.model.entity.Product;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class ProductRepo {

    public static CopyOnWriteArrayList<Product> products = new CopyOnWriteArrayList<Product>();


    public static List<Product> getProductsByCampaign(Campaign campaign){
        return products.stream().filter(p -> p.getCategory().equals(campaign.getCategory())).collect(Collectors.toList());
    }

    private static boolean isExists(Product product){
        return products.stream().anyMatch(p -> p.getSerialNumber().equals(product.getSerialNumber()));
    }

    public static boolean add(Product p){
        if (isExists(p))
            return false;
        products.add(p);
        return true;
    }

    public static void seed() {
        while(products.size() < ProductConfig.DB_PRODUCTS_NUMBER){
            add(Product.generateRandProduct());
        }
    }
}
