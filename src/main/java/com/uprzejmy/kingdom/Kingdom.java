package com.uprzejmy.kingdom;

import com.uprzejmy.gameconfig.GameConfig;

public class Kingdom
{
    private final GameConfig config;
    private final KingdomResources resources;
    private final KingdomBuildings buildings;

    public Kingdom(GameConfig config, KingdomResources resources, KingdomBuildings buildings)
    {
        this.config = config;
        this.resources = resources;
        this.buildings = buildings;
    }

    public void build(KingdomBuildings buildingToBuild)
    {
        // simplest algorithm to build from top until the building points are depleted
        var buildingCost = config.buildingPointCosts().house();
        var pointsToPutIntoBuilding = Math.min(resources.buildingPoints, buildingToBuild.houses * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;

        resources.buildingPoints -= pointsToPutIntoBuilding;
        buildings.houses += fullBuildings;
    }

    public KingdomResources getResources()
    {
        return resources;
    }

    public KingdomBuildings getBuildings()
    {
        return buildings;
    }
}
