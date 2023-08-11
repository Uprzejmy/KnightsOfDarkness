package com.uprzejmy.kingdom;

import com.uprzejmy.gameconfig.GameConfig;

public class KingdomBuilder
{
    private final KingdomResources resources;
    private final KingdomBuildings buildings;
    private final GameConfig config;

    public KingdomBuilder(GameConfig config)
    {
        this.resources = new KingdomResources();
        this.buildings = new KingdomBuildings();
        this.config = config;
    }

    public KingdomBuilder withBuildingPoints(int buildingPoints)
    {
        this.resources.buildingPoints = buildingPoints;
        return this;
    }

    public Kingdom build()
    {
        return new Kingdom(config, resources, buildings);
    }
}
