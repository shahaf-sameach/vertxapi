package com.example.app.model.entity;

import com.example.app.model.config.CampaignConfig;
import com.example.app.model.repo.ProductRepo;
import com.example.app.util.RandomGen;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Date;
import java.util.List;

public class Campaign {

    private Date startDate;
    private String category;
    private double bid;
    private String name;

    public Campaign() {}

    public Campaign(String name, String category, double bid, Date startDate) {
        this.startDate = startDate;
        this.category = category;
        this.bid = bid;
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBid() {
        return bid;
    }

    public void setBid(double bid) {
        this.bid = bid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        if ((CampaignConfig.CAMPAIGN_DAYS_VALID_IN_MS - this.getStartDate().getTime() > CampaignConfig.CAMPAIGN_DAYS_VALID_IN_MS ) || (this.getStartDate().getTime() > System.currentTimeMillis()))
            return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Campaign))return false;
        Campaign otherCampaign = (Campaign) obj;
        if ((this.getBid() == otherCampaign.getBid()) &&
                (this.getCategory().equals(otherCampaign.getCategory())) &&
                (this.getStartDate().equals(otherCampaign.getStartDate())))
            return true;
        return false;
    }

    public static Campaign generateRandCampaign(){
        return new Campaign(RandomGen.getRandomName("campaign"),
                RandomGen.getRandomCategory(),
                RandomGen.getRandomPrice(CampaignConfig.MIN_BID, CampaignConfig.MAX_BID),
                RandomGen.getRandomDate(CampaignConfig.DAYS_RANGE));
    }


    public JsonObject toJson() {
        JsonArray products = new JsonArray();
        this.getProducts().forEach(p -> products.add(p.toJson()));
        return new JsonObject().put("name", name).put("category", category).put("bid", bid).put("start_date", startDate.toString()).put("products", products);
    }

    public List<Product> getProducts() {
        return ProductRepo.getProductsByCampaign(this);
    }
}
