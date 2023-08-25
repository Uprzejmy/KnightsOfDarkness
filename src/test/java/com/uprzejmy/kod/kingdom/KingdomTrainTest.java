package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import com.uprzejmy.kod.gameconfig.KingdomStartingUnits;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

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
        var resourcesBeforeBuild = new EnumMap<>(kingdom.getResources().resources);
        var toTrain = new KingdomUnits();

        kingdom.train(toTrain);

        assertEquals(resourcesBeforeBuild, kingdom.getResources().resources);
    }

    @Test
    void trainBasicTest()
    {
        var kingdom = kingdomBuilder.withGold(99999999).withTools(999999).withUnemployed(9999).build();
        Map<ResourceName, Integer> resourcesBeforeBuild = new EnumMap<>(kingdom.getResources().resources);
        var toTrain = new KingdomStartingUnits(10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 0);

        kingdom.train(new KingdomUnits(toTrain));

        assertEquals(resourcesBeforeBuild.get(ResourceName.unemployed) - 50, kingdom.getResources().getCount(ResourceName.unemployed));
    }

    @Test
    void trainInsufficientGoldTest()
    {
        var kingdom = kingdomBuilder.withGold(0).withTools(999999).withUnemployed(9999).build();
        Map<ResourceName, Integer> resourcesBeforeBuild = new EnumMap<>(kingdom.getResources().resources);
        var toTrain = new KingdomStartingUnits(10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 0);

        kingdom.train(new KingdomUnits(toTrain));

        assertEquals(resourcesBeforeBuild.get(ResourceName.gold), kingdom.getResources().getCount(ResourceName.gold));
        assertEquals(resourcesBeforeBuild.get(ResourceName.unemployed), kingdom.getResources().getCount(ResourceName.unemployed));
    }

    @Test
    void trainPartiallySufficientGoldTest()
    {
        var kingdom = kingdomBuilder.withGold(10000).withTools(999999).withUnemployed(9999).build();
        Map<ResourceName, Integer> resourcesBeforeBuild = new EnumMap<>(kingdom.getResources().resources);
        var toTrain = new KingdomStartingUnits(10, 10, 10, 10, 10, 0, 0, 0, 0, 0, 0);

        kingdom.train(new KingdomUnits(toTrain));

        assertTrue(resourcesBeforeBuild.get(ResourceName.gold) > kingdom.getResources().getCount(ResourceName.gold), "The amount of gold before the training: " + resourcesBeforeBuild.get(ResourceName.gold) + " is not higher than after: " + kingdom.getResources().getCount(ResourceName.gold));
        assertTrue(resourcesBeforeBuild.get(ResourceName.unemployed) > kingdom.getResources().getCount(ResourceName.unemployed), "The amount of unemployed before the training: " + resourcesBeforeBuild.get(ResourceName.unemployed) + " is not higher than after: " + kingdom.getResources().getCount(ResourceName.unemployed));
    }

}