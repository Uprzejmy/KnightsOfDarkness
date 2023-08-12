package com.uprzejmy.kod.kingdom;

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
        for (var entry : buildingsToBuild.buildings.entrySet())
        {
            var buildingCost = kingdom.getConfig().buildingPointCosts().getCost(entry.getKey());
            var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, entry.getValue() * buildingCost);
            var fullBuildings = pointsToPutIntoBuilding / buildingCost;
            kingdom.getBuildings().addCount(entry.getKey(), fullBuildings);
            kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
        }
    }
}