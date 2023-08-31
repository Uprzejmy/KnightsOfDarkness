package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class KingdomTurnTest
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
    void passTurnSanityTest()
    {
        var kingdom = kingdomBuilder.withUnit(UnitName.goldMiner, 0).build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);

        kingdom.passTurn();

        assertEquals(resourcesBeforeTurn.get(ResourceName.gold), kingdom.getResources().getCount(ResourceName.gold));
    }

    @Test
    void passTurnGoldMinersProductionTest()
    {
        var goldMinersCount = 10;
        var kingdom = kingdomBuilder.withUnit(UnitName.goldMiner, goldMinersCount).build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);

        kingdom.passTurn();

        var newProduction = goldMinersCount * config.production().getProductionRate(UnitName.goldMiner);

        assertEquals(resourcesBeforeTurn.get(ResourceName.gold) + newProduction,
                kingdom.getResources().getCount(ResourceName.gold));
    }

    @Test
    void passTurnAllProductionTest()
    {
        for (var unitName : UnitName.values())
        {
            kingdomBuilder.withUnit(unitName, 10);
        }

        var kingdom = kingdomBuilder.build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);

        kingdom.passTurn();

        for (var resourceName : ResourceName.productionResourceNames())
        {
            if (resourceName == ResourceName.weapons)
            {
                // TODO support tools/weapons production split
                break;
            }
            var countBeforeTurn = resourcesBeforeTurn.get(resourceName);
            var countAfterTurn = kingdom.getResources().getCount(resourceName);
            assertTrue(countBeforeTurn < countAfterTurn, "Resource " + resourceName + " before turn was " + countBeforeTurn + " and should be smaller than after production " + countAfterTurn);
        }
    }
}