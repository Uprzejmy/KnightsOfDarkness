package com.uprzejmy.kod.market;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGame;
import com.uprzejmy.kod.game.Game;
import com.uprzejmy.kod.utils.KingdomBuilder;

class MarketTest
{
    private static Game game;

    @BeforeAll
    static void beforeAll()
    {
        game = new TestGame().get();
    }

    @Test
    void testAddOffer()
    {
        var kingdom = new KingdomBuilder(game).build();
        var market = new Market();

        market.addOffer(kingdom, MarketResource.food, 100, 50);

        assertEquals(1, market.getOffersByResource(MarketResource.food).size());
    }

    @Test
    void testThreeOffers()
    {
        var kingdom = new KingdomBuilder(game).build();
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
        var kingdom = new KingdomBuilder(game).build();
        var market = new Market();
        var offer = market.addOffer(kingdom, MarketResource.food, 100, 50);

        market.removeOffer(offer);

        assertEquals(0, market.getOffersByResource(MarketResource.food).size());
    }

    @Test
    void testRemoveThreeOffers()
    {
        var kingdom = new KingdomBuilder(game).build();
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

    @Test
    void buyingOffer_whenNoOffersExist_shouldNotBuyAnything()
    {
        var kingdom = new KingdomBuilder(game).build();
        var market = new Market();

        var offer = new MarketOffer(kingdom, MarketResource.food, 100, 100);

        var amountBought = market.buyExistingOffer(offer, 100);

        assertEquals(0, market.getOffersByResource(MarketResource.food).size());
        assertEquals(0, amountBought);
    }

    @Test
    void buyingOffer_whenOneOfferExistsAndHasEnoughAmount_shouldSellEntireRequestedAmountAndStillHasOfferAvailable()
    {
        var kingdom = new KingdomBuilder(game).build();
        var market = new Market();

        market.addOffer(kingdom, MarketResource.food, 111, 100);
        var offers = market.getOffersByResource(MarketResource.food);
        assertTrue(!offers.isEmpty());

        var amountBought = market.buyExistingOffer(offers.get(0), 100);

        assertEquals(1, market.getOffersByResource(MarketResource.food).size());
        assertEquals(100, amountBought);
    }

    @Test
    void buyingOffer_whenOneOfferExistsAndHasExactlyTheSameAmount_shouldSellEntireRequestedAmountAndHasNoOffersAvailable()
    {
        var kingdom = new KingdomBuilder(game).build();
        var market = new Market();

        market.addOffer(kingdom, MarketResource.food, 100, 100);
        var offers = market.getOffersByResource(MarketResource.food);
        assertTrue(!offers.isEmpty());

        var amountBought = market.buyExistingOffer(offers.get(0), 100);

        assertEquals(0, market.getOffersByResource(MarketResource.food).size());
        assertEquals(100, amountBought);
    }
}
