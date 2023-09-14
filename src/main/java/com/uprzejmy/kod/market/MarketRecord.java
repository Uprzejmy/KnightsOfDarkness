package com.uprzejmy.kod.market;

import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.MarketResource;

public class MarketRecord
{
    Kingdom kingdom;
    MarketResource resource;
    int count;
    int price;

    public MarketRecord(Kingdom kingdom, MarketResource resource, int count, int price)
    {
        this.kingdom = kingdom;
        this.resource = resource;
        this.count = count;
        this.price = price;
    }
}
