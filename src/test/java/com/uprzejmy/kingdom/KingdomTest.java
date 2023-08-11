package com.uprzejmy.kingdom;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class KingdomTest {

    @Test
    void buildTest() {
        var kingdom = new KingdomBuilder().withBuildingPoints(10000).build();
        var housesBeforeBuild = kingdom.getBuildings().houses;
        var buildingPointsBeforeBuild = kingdom.getResources().buildingPoints;

        var toBuild = new KingdomBuildings();
        toBuild.houses = 5;
        toBuild.goldMines = 2;

        kingdom.build(toBuild);

        assertEquals(housesBeforeBuild + 5, kingdom.getBuildings().houses);
        assertNotEquals(buildingPointsBeforeBuild, kingdom.getResources().buildingPoints);
    }
}