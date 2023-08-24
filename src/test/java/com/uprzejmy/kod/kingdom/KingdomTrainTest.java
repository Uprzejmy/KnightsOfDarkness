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
}