package com.example.app.model.config;

public class CampaignConfig {
    public final static double MIN_BID = 0.0;
    public final static double MAX_BID = 100.0;
    public final static int DB_CAMPAIGNS_NNUMBER = 10;
    public final static int DAYS_RANGE = 7;

    public final static long DAY_IN_MS = 1000 * 60 * 60 * 24;
    public final static int CAMPAIGN_DAYS_VALID = 10;
    public final static long CAMPAIGN_DAYS_VALID_IN_MS = DAY_IN_MS * CAMPAIGN_DAYS_VALID;


}
