package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.TestGameConfig;
import com.uprzejmy.kod.gameconfig.GameConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KingdomTest
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
    void buildSanityTest()
    {
        var kingdom = kingdomBuilder.withBuildingPoints(10000).build();
        var buildingPointsBeforeBuild = kingdom.getResources().buildingPoints;
        var toBuild = new KingdomBuildings();

        kingdom.build(toBuild);

        assertEquals(buildingPointsBeforeBuild, kingdom.getResources().buildingPoints);
    }

    @Test
    void buildHousesAndGoldMinesTest()
    {
        var kingdom = kingdomBuilder.withBuildingPoints(10000).build();
        var housesBeforeBuild = kingdom.getBuildings().getCount(BuildingName.house);
        var goldMinesBeforeBuild = kingdom.getBuildings().getCount(BuildingName.goldMine);
        var buildingPointsBeforeBuild = kingdom.getResources().buildingPoints;

        var housesToBuild = 5;
        var goldMinesToBuild = 2;
        var toBuild = new KingdomBuildings();
        toBuild.addCount(BuildingName.house, housesToBuild);
        toBuild.addCount(BuildingName.goldMine, goldMinesToBuild);

        kingdom.build(toBuild);

        assertEquals(housesBeforeBuild + 5, kingdom.getBuildings().getCount(BuildingName.house));
        assertEquals(goldMinesBeforeBuild + 2, kingdom.getBuildings().getCount(BuildingName.goldMine));

        var housesCost = housesToBuild * config.buildingPointCosts().house();
        var goldMinesCost = goldMinesToBuild * config.buildingPointCosts().goldMine();
        var totalCost = housesCost + goldMinesCost;

        assertEquals(buildingPointsBeforeBuild - totalCost, kingdom.getResources().buildingPoints);
    }
}