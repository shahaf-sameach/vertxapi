package com.example.app.api;


import com.example.app.model.entity.Campaign;
import com.example.app.model.repo.CampaignRepo;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.Date;

public class SimpleAPI extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(new SimpleAPI());
    }


    @Override
    public void start() {

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());
        router.get("/campaign").handler(this::handleCreateCampaign);
        router.get("/ad").handler(this::handleServeAd);

        HttpServerOptions options = new HttpServerOptions().setLogActivity(true);

        vertx.createHttpServer(options).requestHandler(router::accept).listen(8080, res -> {
            if (res.succeeded()) {
                System.out.println("Server is online");
            } else {
                System.out.println("Faild to bind!");
            }
        });
    }

    private void handleCreateCampaign(RoutingContext routingContext) {
        String name = routingContext.request().getParam("name");
        String bidString = routingContext.request().getParam("bid");
        String dateString = routingContext.request().getParam("date");
        String category = routingContext.request().getParam("category");


        HttpServerResponse response = routingContext.response();
        if ((name == null) || (bidString == null) || (dateString == null) || (category == null)) {
            sendError(400, response, "missing required params");
        } else {
            double bid = Double.parseDouble(bidString);
            Date date = new Date(Long.parseLong(dateString));
            Campaign campaign = new Campaign(name, category, bid, date);

            if (CampaignRepo.add(campaign))
                response.putHeader("content-type", "application/json").end(campaign.toJson().encodePrettily());
            else
                sendError(400, response, "duplicate campaign error");
        }
    }

    private void handleServeAd(RoutingContext routingContext) {
        String category = routingContext.request().getParam("category");
        HttpServerResponse response = routingContext.response();

        if (category == null)
            sendError(400, response, "category param required");
        else {
            Campaign campaign = CampaignRepo.getHighestBidCampaign(category);
            if (campaign == null) {
                campaign = CampaignRepo.getHighestBidCampaign();
                if (campaign == null) {
                    sendError(404, response, "campaign not found");
                }
            }
            response.putHeader("content-type", "application/json").end(campaign.toJson().encodePrettily());
        }
    }


    private void sendError(int statusCode, HttpServerResponse response, String message) {
        response.setStatusCode(statusCode).end(message);
    }

}
