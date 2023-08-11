package com.uprzejmy.kingdom;

import com.uprzejmy.gameconfig.GameConfig;
import com.uprzejmy.gameconfig.Initializer;

public class KingdomBuilder
{
    private final KingdomResources resources;
    private final KingdomBuildings buildings;
    private final GameConfig config;

    public KingdomBuilder()
    {
        this.resources = new KingdomResources();
        this.buildings = new KingdomBuildings();
        this.config = Initializer.readGameConfig();
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
