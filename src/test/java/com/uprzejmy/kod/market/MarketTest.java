package com.uprzejmy.kod.market;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import com.uprzejmy.kod.kingdom.KingdomBuilder;

public class MarketTest
{
    private static GameConfig config;

    @BeforeAll
    static void beforeAll()
    {
        config = new TestGameConfig().get();
    }

    @Test
    void testAddOffer()
    {
        var kingdom = new KingdomBuilder(config).build();
        var market = new Market();

        market.addOffer(kingdom, MarketResource.food, 100, 50);

        assertEquals(1, market.getOffersByResource(MarketResource.food).size());
    }

    @Test
    void testThreeOffers()
    {
        var kingdom = new KingdomBuilder(config).build();
        var market = new Market();

        market.addOffer(kingdom, MarketResource.food, 100, 50);
        market.addOffer(kingdom, MarketResource.food, 100, 50);
        market.addOffer(kingdom, MarketResource.food, 100, 50);
        market.addOffer(kingdom, MarketResource.food, 100, 51);
        market.addOffer(kingdom, MarketResource.food, 100, 52);

        assertEquals(5, market.getOffersByResource(MarketResource.food).size());
    }

    @Test
    void testRemoveOffer()
    {
        var kingdom = new KingdomBuilder(config).build();
        var market = new Market();
        var offer = market.addOffer(kingdom, MarketResource.food, 100, 50);

        market.removeOffer(offer);

        assertEquals(0, market.getOffersByResource(MarketResource.food).size());
    }

    @Test
    void testRemoveThreeOffers()
    {
        var kingdom = new KingdomBuilder(config).build();
        var market = new Market();
        var offer1 = market.addOffer(kingdom, MarketResource.food, 100, 50);
        var offer2 = market.addOffer(kingdom, MarketResource.food, 100, 50);
        var offer3 = market.addOffer(kingdom, MarketResource.food, 100, 51);
        market.addOffer(kingdom, MarketResource.food, 100, 51);
        market.addOffer(kingdom, MarketResource.food, 100, 51);

        market.removeOffer(offer1);
        market.removeOffer(offer2);
        market.removeOffer(offer3);

        assertEquals(2, market.getOffersByResource(MarketResource.food).size());
    }
}
