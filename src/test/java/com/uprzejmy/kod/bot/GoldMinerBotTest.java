package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import com.uprzejmy.kod.kingdom.KingdomBuilder;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        var bot = new GoldMinerBot(kingdom);
        for (var i = 0; i < 10; i++)
        {
            bot.doActions();
            bot.passTurn();
        }

        var goldMinersAfter = kingdom.getUnits().getCount(UnitName.goldMiner);
        assertTrue(goldMinersAfter > goldMinersBefore, "There were supposed to be new trained units, before " + goldMinersBefore + " after " + goldMinersAfter);
        assertTrue(kingdom.getUnusedLand() < unusedLandBefore, "The land was supposed to be used, available before " + unusedLandBefore + " after " + kingdom.getUnusedLand());
    }
}