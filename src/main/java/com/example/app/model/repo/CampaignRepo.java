package com.example.app.model.repo;

import com.example.app.model.config.CampaignConfig;
import com.example.app.model.entity.Campaign;

import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

public class CampaignRepo {

    public static CopyOnWriteArrayList<Campaign> campaigns = new CopyOnWriteArrayList<Campaign>();

    public static Campaign getHighestBidCampaign(){
        return campaigns.stream().filter(p -> p.isValid()).max(Comparator.comparingDouble(Campaign::getBid)).orElse(null);
    }

    public static Campaign getHighestBidCampaign(String category){
        return campaigns.stream().filter(p -> p.isValid()).filter(p -> p.getCategory().equals(category)).max(Comparator.comparingDouble(Campaign::getBid)).orElse(null);
    }

    private static boolean isExists(Campaign campaign) {
        return campaigns.stream().anyMatch(c -> c.equals(campaign));
    }

    public static boolean add(Campaign c){
        if (isExists(c))
            return false;
        campaigns.add(c);
        return true;
    }

    public static void seed() {
        while(campaigns.size() < CampaignConfig.DB_CAMPAIGNS_NNUMBER){
            add(Campaign.generateRandCampaign());
        }
    }


}
