package com.uprzejmy.kod.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGame;
import com.uprzejmy.kod.bot.Bot;
import com.uprzejmy.kod.bot.FarmerBot;
import com.uprzejmy.kod.bot.GoldMinerBot;
import com.uprzejmy.kod.game.Game;
import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.utils.KingdomBuilder;

public class BotGame
{
    @Test
    void simulateTenTurnsTest()
    {
        var game = new TestGame().get();
        var kingdomBuilder = new KingdomBuilder(game).withResource(ResourceName.turns, 10);
        kingdomBuilder = setupKingdomStartConfiguration(kingdomBuilder, game);

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
    }

    private KingdomBuilder setupKingdomStartConfiguration(KingdomBuilder kingdomBuilder, Game game)
    {
        var startConfiguration = game.getConfig().kingdomStartConfiguration();
        for (var building : BuildingName.values())
        {
            kingdomBuilder.withBuilding(building, startConfiguration.buildings().getCount(building));
        }

        for (var resource : ResourceName.values())
        {
            kingdomBuilder.withResource(resource, startConfiguration.resources().getCount(resource));
        }

        for (var unit : UnitName.values())
        {
            kingdomBuilder.withUnit(unit, startConfiguration.units().getCount(unit));
        }

        return kingdomBuilder;
    }
}
