package com.uprzejmy.kod.market;

import java.util.ArrayList;
import java.util.List;

import com.uprzejmy.kod.kingdom.Kingdom;

public class Market
{
    List<MarketRecord> offers = new ArrayList<>();

    public MarketRecord addOffer(Kingdom kingdom, MarketResource resource, int count, int price)
    {
        var offer = new MarketRecord(kingdom, resource, count, price);
        offers.add(offer);

        return offer;
    }

    public void removeOffer(MarketRecord offer)
    {
        offers.remove(offer);
    }

    public List<MarketRecord> getOffersByResource(MarketResource resource)
    {
        return offers.stream().filter(offer -> offer.resource == resource).toList();
    }

    public List<MarketRecord> getOffersByKingdom(Kingdom kingdom)
    {
        return offers.stream().filter(offer -> offer.kingdom == kingdom).toList();
    }
}
