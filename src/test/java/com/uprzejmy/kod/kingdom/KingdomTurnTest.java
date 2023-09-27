package com.uprzejmy.kod.kingdom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.uprzejmy.kod.TestGame;
import com.uprzejmy.kod.game.Game;
import com.uprzejmy.kod.gameconfig.GameConfig;
import com.uprzejmy.kod.utils.KingdomBuilder;

class KingdomTurnTest
{
    private static Game game;
    private static GameConfig config;

    private KingdomBuilder kingdomBuilder;

    @BeforeAll
    static void beforeAll()
    {
        game = new TestGame().get();
        config = game.getConfig();
    }

    @BeforeEach
    void setUp()
    {
        this.kingdomBuilder = new KingdomBuilder(game);
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

        assertEquals(resourcesBeforeTurn.get(ResourceName.gold) + newProduction, kingdom.getResources().getCount(ResourceName.gold));
    }

    @Test
    void passTurnAllProductionTest()
    {
        for (var unitName : UnitName.getProductionUnits())
        {
            kingdomBuilder.withUnit(unitName, 10);
        }

        var kingdom = kingdomBuilder.build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);
        kingdom.passTurn();

        for (var unitName : UnitName.getProductionUnits())
        {
            var resourceName = config.production().getResource(unitName);
            var countBeforeTurn = resourcesBeforeTurn.get(resourceName);
            var countAfterTurn = kingdom.getResources().getCount(resourceName);
            // TODO move away food eating and iron consumption logic from this test - no "ifs" allowed
            var spentDuringTurn = 0;
            if (resourceName == ResourceName.food)
            {
                spentDuringTurn = kingdom.getFoodUpkeep();
            } else if (resourceName == ResourceName.iron)
            {
                spentDuringTurn = kingdom.getUnits().getCount(UnitName.blacksmith); // TODO as in the normal code, resource spending ratios should be in config file
            }
            assertTrue(countBeforeTurn < countAfterTurn + spentDuringTurn, "Resource " + resourceName + " before turn was " + countBeforeTurn + " and should be smaller than after production " + countAfterTurn);
        }
    }

    @Test
    void passTurnNewPeopleTest()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.unemployed, 0).withBuilding(BuildingName.house, 99999).build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);

        kingdom.passTurn();

        var beforeTurn = resourcesBeforeTurn.get(ResourceName.unemployed);
        var afterTurn = kingdom.getResources().getCount(ResourceName.unemployed);
        assertTrue(beforeTurn < afterTurn, "Expected number of unemployed before turn " + beforeTurn + " to be lower than after turn " + afterTurn);
    }

    @Test
    void passTurn_WithFullHousingCapacity_NewPeopleShouldNotArriveTest()
    {
        var kingdom = kingdomBuilder.withResource(ResourceName.unemployed, 9999).build();
        Map<ResourceName, Integer> resourcesBeforeTurn = new EnumMap<>(kingdom.getResources().resources);

        kingdom.passTurn();

        var beforeTurn = resourcesBeforeTurn.get(ResourceName.unemployed);
        var afterTurn = kingdom.getResources().getCount(ResourceName.unemployed);
        assertTrue(beforeTurn >= afterTurn, "Expected number of unemployed before turn " + beforeTurn + " to NOT be lower than after turn " + afterTurn);
    }

    @Test
    void passMoreTurnsThanMaximumTest()
    {
        var kingdom = kingdomBuilder.build();
        var numberOfTurns = kingdom.getResources().getCount(ResourceName.turns);

        // first we pass every turn that kingdom has
        for (int i = 0; i < numberOfTurns; i++)
        {
            assertTrue(kingdom.passTurn());
        }

        // then we try to pass turn which is not available
        assertFalse(kingdom.passTurn());
    }
}