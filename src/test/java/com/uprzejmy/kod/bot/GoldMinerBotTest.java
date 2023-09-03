package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import com.uprzejmy.kod.kingdom.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoldMinerBotTest
{
    private static GameConfig config;
    private KingdomBuilder kingdomBuilder;

    @BeforeAll
    static void beforeAll()
    {
        config = new TestGameConfig().get();
    }

    @BeforeEach
    void setUp()
    {
        this.kingdomBuilder = new KingdomBuilder(config);
    }

    @Test
    void simulateTenTurnsTest()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.turns, 10).build();
        var goldMinersBefore = kingdom.getUnits().getCount(UnitName.goldMiner);
        var unusedLandBefore = kingdom.getUnusedLand();
        var goldMinesBefore = kingdom.getBuildings().getCount(BuildingName.goldMine);
        var housesBefore = kingdom.getBuildings().getCount(BuildingName.house);

        var bot = new GoldMinerBot(kingdom);
        for (var i = 0; i < 10; i++)
        {
            bot.doAllActions();
            bot.passTurn();
        }

        var goldMinersAfter = kingdom.getUnits().getCount(UnitName.goldMiner);
        var goldMinesAfter = kingdom.getBuildings().getCount(BuildingName.goldMine);
        var housesAfter = kingdom.getBuildings().getCount(BuildingName.house);
        assertTrue(goldMinersAfter > goldMinersBefore, "There were supposed to be new trained units, before " + goldMinersBefore + " after " + goldMinersAfter);
        assertTrue(kingdom.getUnusedLand() < unusedLandBefore, "The land was supposed to be used, available before " + unusedLandBefore + " after " + kingdom.getUnusedLand());
        assertTrue(goldMinesAfter > goldMinesBefore, "There were supposed to be new gold mines built, before " + goldMinesBefore + " after " + goldMinesAfter);
        assertTrue(housesAfter > housesBefore, "There were supposed to be new houses built, " + housesBefore + " after " + housesAfter);
    }

    @Test
    void afterTrainingNoNewUnitCanBeTrainedTest()
    {
        var kingdom = kingdomBuilder.build();
        var toTrain = new KingdomUnits();
        for (var unitName : UnitName.values())
        {
            toTrain.addCount(unitName, 1);
        }

        var bot = new GoldMinerBot(kingdom);
        bot.doTrainAction();

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.countAll());
    }
}