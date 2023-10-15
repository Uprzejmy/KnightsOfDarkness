package com.uprzejmy.kod.kingdom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class KingdomSpecialBuildingsTest
{
    @Test
    void afterSpecialBuildingDeletion_buildingAtDeletedPlaceShouldBeEmpty()
    {
        int buildingPlace = 1;
        var specialBuildings = new KingdomSpecialBuildings();
        specialBuildings.deleteBuilding(buildingPlace);
        assertEquals(SpecialBuildingName.emptyBuilding, specialBuildings.getAt(buildingPlace).buildingType);
    }

    @Test
    void afterSpecialBuildingStartAndDeletion_buildingAtDeletedPlaceShouldBeEmpty()
    {
        int buildingPlace = 1;
        var specialBuildings = new KingdomSpecialBuildings();
        specialBuildings.startNew(SpecialBuildingName.goldShaft, 1);
        specialBuildings.deleteBuilding(buildingPlace);
        assertEquals(SpecialBuildingName.emptyBuilding, specialBuildings.getAt(buildingPlace).buildingType);
    }

    @Test
    void startBuilding_whenCurrentBuildingAtPlaceIsEmpty_shouldStartNewOne()
    {
        int buildingPlace = 1;
        var specialBuildings = new KingdomSpecialBuildings();
        specialBuildings.startNew(SpecialBuildingName.goldShaft, 1);
        assertEquals(SpecialBuildingName.goldShaft, specialBuildings.getAt(buildingPlace).buildingType);
    }

    @Test
    void startBuilding_whenCurrentBuildingAtPlaceIsNotEmpty_shouldDoNothing()
    {
        int buildingPlace = 1;
        var specialBuildings = new KingdomSpecialBuildings();
        specialBuildings.startNew(SpecialBuildingName.goldShaft, 1);
        specialBuildings.startNew(SpecialBuildingName.ironShaft, 1);
        assertEquals(SpecialBuildingName.goldShaft, specialBuildings.getAt(buildingPlace).buildingType);
    }
}
