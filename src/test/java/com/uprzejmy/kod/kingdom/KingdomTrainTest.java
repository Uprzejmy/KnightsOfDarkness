package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KingdomTrainTest
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
    void trainSanityTest()
    {
        var kingdom = kingdomBuilder.build();
        var toTrain = new KingdomUnits();

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.countAll());
    }

    @Test
    void trainBasicTest()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.gold, 99999999).withResource(ResourceName.tools, 999999).withResource(ResourceName.unemployed, 9999).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 10);

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(10, trainedUnits.getCount(UnitName.goldMiner));
    }

    @Test
    void trainInsufficientGold()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.gold, 0).withResource(ResourceName.tools, 999999).withResource(ResourceName.unemployed, 9999).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 10);

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.getCount(UnitName.goldMiner));
    }

    @Test
    void trainInsufficientTools()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.gold, 999999).withResource(ResourceName.tools, 0).withResource(ResourceName.unemployed, 9999).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 10);

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.getCount(UnitName.goldMiner));
    }

    @Test
    void trainInsufficientUnemployed()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.gold, 999999).withResource(ResourceName.tools, 999999).withResource(ResourceName.unemployed, 0).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 10);

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.getCount(UnitName.goldMiner));
    }

    @Test
    void trainPartiallySufficientGoldTest()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.gold, 10000).withResource(ResourceName.tools, 999999).withResource(ResourceName.unemployed, 9999).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 10);

        var trainedUnits = kingdom.train(toTrain);

        assertTrue(trainedUnits.countAll() > 0, "At least one unit should have been trained");
        assertTrue(trainedUnits.countAll() < 10, "Number of trained units was supposed to be smaller than desired");
    }

    @Test
    void trainAllUnitsTest()
    {
        var kingdom = kingdomBuilder.build();
        var toTrain = new KingdomUnits();
        for (var unitName : UnitName.values())
        {
            toTrain.addCount(unitName, 1);
        }

        var trainedUnits = kingdom.train(toTrain);

        for (var unitName : UnitName.values())
        {
            var trainedCount = trainedUnits.getCount(unitName);
            assertEquals(1, trainedCount, String.format("Expected %d, actual %d when training %s", 1, trainedCount, unitName));
        }
    }

    @Test
    void trainingShouldNotResultInLessUnits()
    {
        var kingdom = kingdomBuilder.withBuilding(BuildingName.goldMine, 0).withUnit(UnitName.goldMiner, 10).withResource(ResourceName.gold, 9999999).withResource(ResourceName.tools, 999999).withResource(ResourceName.unemployed, 100).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 1);

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.getCount(UnitName.goldMiner));
    }

    @Test
    void trainInsufficientBuildingCapacityTest()
    {
        var kingdom = kingdomBuilder.withBuilding(BuildingName.goldMine, 0).withUnit(UnitName.goldMiner, 0).withResource(ResourceName.gold, 9999999).withResource(ResourceName.tools, 999999).withResource(ResourceName.unemployed, 100).build();
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 1);

        var trainedUnits = kingdom.train(toTrain);

        assertEquals(0, trainedUnits.getCount(UnitName.goldMiner));
    }
}