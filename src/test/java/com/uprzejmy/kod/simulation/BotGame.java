package com.uprzejmy.kod.simulation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGame;
import com.uprzejmy.kod.bot.BlacksmithBot;
import com.uprzejmy.kod.bot.Bot;
import com.uprzejmy.kod.bot.FarmerBot;
import com.uprzejmy.kod.bot.GoldMinerBot;
import com.uprzejmy.kod.game.Game;
import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
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

        var blacksmithKingdom = kingdomBuilder.withName("BlacksmithBot").build();
        Bot blacksmithBot = new BlacksmithBot(blacksmithKingdom);
        game.addKingdom(blacksmithKingdom);

        var goldMinerKingdom1 = kingdomBuilder.withName("GoldMinerBot1").build();
        Bot goldMinerBot1 = new GoldMinerBot(goldMinerKingdom1);
        game.addKingdom(goldMinerKingdom1);

        var goldMinerKingdom2 = kingdomBuilder.withName("GoldMinerBot2").build();
        Bot goldMinerBot2 = new GoldMinerBot(goldMinerKingdom2);
        game.addKingdom(goldMinerKingdom2);

        printResourcesHeader();
        printLineSeparator();

        for (var i = 0; i < 10; i++)
        {
            farmerBot.doAllActions();
            blacksmithBot.doAllActions();
            goldMinerBot1.doAllActions();
            goldMinerBot2.doAllActions();
            farmerBot.passTurn();
            blacksmithBot.passTurn();
            goldMinerBot1.passTurn();
            goldMinerBot2.passTurn();
            kingdomInfoPrinter(farmerKingdom);
            kingdomInfoPrinter(blacksmithKingdom);
            kingdomInfoPrinter(goldMinerKingdom1);
            kingdomInfoPrinter(goldMinerKingdom2);
            printLineSeparator();
        }

        assertEquals(0, farmerKingdom.getResources().getCount(ResourceName.turns));
    }

    private void printResourcesHeader()
    {
        System.out.format("Kingdom         | land  | ppl  | unemployed | farmers | blacksmiths | goldMiners | ironMiners |    gold    |    food    |    tools   | marketOffers\n");
    }

    private void printLineSeparator()
    {
        System.out.format("======================================================================================================================================\n");
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

    private void kingdomInfoPrinter(Kingdom kingdom)
    {
        var resources = kingdom.getResources();
        var units = kingdom.getUnits();
        // @formatter:off 
        System.out.format("%-15s | %5d | %4d | %10d | %7d | %11d | %10d | %10d | %10d | %10d | %10d | %2d\n", 
            kingdom.getName(),
            resources.getCount(ResourceName.land),
            kingdom.getTotalPeopleCount(),
            resources.getCount(ResourceName.unemployed),
            units.getCount(UnitName.farmer),
            units.getCount(UnitName.blacksmith),
            units.getCount(UnitName.goldMiner),
            units.getCount(UnitName.ironMiner),
            resources.getCount(ResourceName.gold),
            resources.getCount(ResourceName.food),
            resources.getCount(ResourceName.tools),
            kingdom.getMarketOffers().size()
        );
        // @formatter:on
    }
}
