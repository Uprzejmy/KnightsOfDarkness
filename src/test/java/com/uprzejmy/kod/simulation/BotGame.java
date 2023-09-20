package com.uprzejmy.kod.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGame;
import com.uprzejmy.kod.bot.Bot;
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

        var farmerKingdom = kingdomBuilder.withName("FarmerBot").build();
        Bot farmerBot = new FarmerBot(farmerKingdom);
        game.addKingdom(farmerKingdom);

        var goldMinerKingdom1 = kingdomBuilder.withName("GoldMinerBot1").build();
        Bot goldMinerBot1 = new GoldMinerBot(goldMinerKingdom1);
        game.addKingdom(goldMinerKingdom1);

        var goldMinerKingdom2 = kingdomBuilder.withName("GoldMinerBot2").build();
        Bot goldMinerBot2 = new GoldMinerBot(goldMinerKingdom2);
        game.addKingdom(goldMinerKingdom2);

        for (var i = 0; i < 10; i++)
        {
            farmerBot.doAllActions();
            goldMinerBot1.doAllActions();
            goldMinerBot2.doAllActions();
            farmerBot.passTurn();
            goldMinerBot1.passTurn();
            goldMinerBot2.passTurn();
        }

        assertEquals(0, farmerKingdom.getResources().getCount(ResourceName.turns));
        // TODO one offer was taken out by gold miners, those asserts don't make sense in this kind of test
        assertEquals(9, game.getMarket().getOffersByResource(MarketResource.food).size());
    }

}
