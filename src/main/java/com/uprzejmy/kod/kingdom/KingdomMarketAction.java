package com.uprzejmy.kod.kingdom;

import java.util.List;

import com.uprzejmy.kod.market.MarketOffer;
import com.uprzejmy.kod.market.MarketResource;

public class KingdomMarketAction
{
    private final Kingdom kingdom;

    public KingdomMarketAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public void postOffer(MarketResource resource, int count, int price)
    {
        // TODO in game configuration add limit on number of offers from a single kingdom
        var maxToPost = Math.min(count, kingdom.getResources().getCount(ResourceName.from(resource)));
        if (maxToPost > 0)
        {
            kingdom.getMarket().addOffer(kingdom, resource, count, price);
            kingdom.getResources().subtractCount(ResourceName.from(resource), count);
        }
        // TODO test above scenarios
    }

    public List<MarketOffer> getMyOffers()
    {
        return kingdom.getMarket().getOffersByKingdom(kingdom);
    }

    public int buyMarketOffer(MarketOffer offer, int amount)
    {
        var amountBought = kingdom.getMarket().buyExistingOffer(offer, amount);
        kingdom.getResources().subtractCount(ResourceName.gold, amountBought * offer.getPrice());
        kingdom.getResources().addCount(ResourceName.from(offer.getResource()), amountBought);

        return amountBought;
    }

    public void acceptOffer(int goldValue)
    {
        kingdom.getResources().addCount(ResourceName.gold, goldValue);
    }
}
