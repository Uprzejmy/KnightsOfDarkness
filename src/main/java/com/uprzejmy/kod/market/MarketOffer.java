package com.uprzejmy.kod.market;

import com.uprzejmy.kod.kingdom.Kingdom;

public class MarketOffer
{
    Kingdom kingdom;
    MarketResource resource;
    int count;
    int price;

    public MarketOffer(Kingdom kingdom, MarketResource resource, int count, int price)
    {
        this.kingdom = kingdom;
        this.resource = resource;
        this.count = count;
        this.price = price;
    }
}
