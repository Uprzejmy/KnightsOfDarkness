package com.uprzejmy.kod.kingdom;

import java.util.EnumSet;

public class KingdomBuildAction
{
    private final Kingdom kingdom;

    public KingdomBuildAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    /**
     * simplest algorithm to build from top until the building points are depleted
     */
    public void build(KingdomBuildings buildingsToBuild)
    {
        // by using EnumSet we make sure the names are ordered as specified in the enum declaration
        var buildingNames = EnumSet.copyOf(buildingsToBuild.buildings.keySet());
        for (var buildingName : buildingNames)
        {
            var buildingCost = kingdom.getConfig().buildingPointCosts().getCost(buildingName);
            var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildingsToBuild.getCount(buildingName) * buildingCost);
            var fullBuildings = pointsToPutIntoBuilding / buildingCost;
            kingdom.getBuildings().addCount(buildingName, fullBuildings);
            kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
        }
    }
}