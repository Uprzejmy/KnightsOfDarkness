package com.uprzejmy.kod.market;

import java.util.ArrayList;
import java.util.List;

import com.uprzejmy.kod.kingdom.Kingdom;

public class Market
{
    List<MarketOffer> offers = new ArrayList<>();

    public MarketOffer addOffer(Kingdom kingdom, MarketResource resource, int count, int price)
    {
        var offer = new MarketOffer(kingdom, resource, count, price);
        offers.add(offer);

        return offer;
    }

    public void removeOffer(MarketOffer offer)
    {
        offers.remove(offer);
    }

    public List<MarketOffer> getOffersByResource(MarketResource resource)
    {
        return offers.stream().filter(offer -> offer.resource == resource).toList();
    }

    public List<MarketOffer> getOffersByKingdom(Kingdom kingdom)
    {
        return offers.stream().filter(offer -> offer.kingdom == kingdom).toList();
    }
}
