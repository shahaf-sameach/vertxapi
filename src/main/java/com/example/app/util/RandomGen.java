package com.example.app.util;

import com.example.app.model.config.CampaignConfig;
import com.example.app.model.config.ProductConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.Date;
import java.util.Random;

public class RandomGen {

    public static double getRandomPrice(double min, double max){
        return min + (max - min) * new Random().nextDouble();
    }

    public static String getRandomSerialNumber(int length){
        return RandomStringUtils.random(length, true, true);
    }

    public static String getRandomCategory(){
        return ProductConfig.CATEGORIES[new Random().nextInt(ProductConfig.CATEGORIES.length)];
    }

    public static String getRandomName(String prefix){
        return prefix + new Random().nextInt(1000);
    }

    public static Date getRandomDate(int range) {
        long offset = range * CampaignConfig.DAY_IN_MS;
        Date min = new Date(System.currentTimeMillis() - offset);
        Date max = new Date(System.currentTimeMillis() + offset);
        return new Date(new RandomDataGenerator().nextLong(min.getTime(), max.getTime()));
    }

}
