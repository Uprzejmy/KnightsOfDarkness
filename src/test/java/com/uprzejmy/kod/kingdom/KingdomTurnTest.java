package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void passTurn()
    {
        var goldMinersCount = 10;
        var kingdom = kingdomBuilder.withGoldMiners(goldMinersCount).build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);

        kingdom.passTurn();

        var newProduction = goldMinersCount * config.production().getProductionRate(UnitName.goldMiner);

        assertEquals(resourcesBeforeTurn.get(ResourceName.gold) + newProduction, kingdom.getResources().getCount(ResourceName.gold));
    }
}