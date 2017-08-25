package com.example.app;

import com.example.app.api.SimpleAPI;
import com.example.app.model.repo.CampaignRepo;
import com.example.app.model.repo.ProductRepo;
import io.vertx.core.Vertx;

public class Main {

    public static void main(String[] args) {
        ProductRepo.seed();
        CampaignRepo.seed();
        System.out.println(System.currentTimeMillis());

        Vertx.vertx().deployVerticle(new SimpleAPI(), res -> {
            if (res.succeeded()) {
                System.out.println("Deployment succeed");
            } else {
                System.out.println("Deployment failed! " + res.cause());
            }
        });
    }
}
