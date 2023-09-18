package com.uprzejmy.kod.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGame;
import com.uprzejmy.kod.bot.FarmerBot;
import com.uprzejmy.kod.bot.GoldMinerBot;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.market.MarketResource;
import com.uprzejmy.kod.utils.KingdomBuilder;

public class BotGame
{
    @Test
    void simulateTenTurnsTest()
    {
        var game = new TestGame().get();
        var kingdomBuilder = new KingdomBuilder(game).withResource(ResourceName.turns, 10);

        var farmerKingdom = kingdomBuilder.build();
        var farmerBot = new FarmerBot(farmerKingdom);
        game.addKingdom(farmerKingdom);

        var goldMinerKingdom = kingdomBuilder.build();
        var goldMinerBot = new GoldMinerBot(goldMinerKingdom);
        game.addKingdom(goldMinerKingdom);

        for (var i = 0; i < 10; i++)
        {
            farmerBot.doAllActions();
            goldMinerBot.doAllActions();
            farmerBot.passTurn();
            goldMinerBot.passTurn();
        }

        assertEquals(0, farmerKingdom.getResources().getCount(ResourceName.turns));
        assertEquals(10, game.getMarket().getOffersByResource(MarketResource.food).size());
    }

}
