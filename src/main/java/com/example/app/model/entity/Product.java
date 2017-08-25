package com.example.app.model.entity;

import com.example.app.model.config.ProductConfig;
import com.example.app.util.RandomGen;
import io.vertx.core.json.JsonObject;


public class Product {
    private String title;
    private String category;
    private double price;
    private String serialNumber;

    public Product() {}

    public Product(String title, String category, double price, String serialNumber) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.serialNumber = serialNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public static Product generateRandProduct(){
        return new Product(RandomGen.getRandomName("product"),
                RandomGen.getRandomCategory(),
                RandomGen.getRandomPrice(ProductConfig.MIN_PRICE, ProductConfig.MAX_PRICE),
                RandomGen.getRandomSerialNumber(ProductConfig.SERIAL_NUMBER_LENGTH));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product))return false;
        Product otherProduct = (Product) obj;
        return this.getSerialNumber().equals(otherProduct.getSerialNumber());
    }

    public JsonObject toJson() {
        return new JsonObject().put("title", title).put("category", category).put("price", price).put("serial_number", serialNumber);
    }
}
