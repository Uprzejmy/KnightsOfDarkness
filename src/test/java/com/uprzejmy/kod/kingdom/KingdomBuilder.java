package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.gameconfig.GameConfig;

public class KingdomBuilder
{
    private final KingdomResources resources;
    private final KingdomBuildings buildings;
    private final KingdomUnits units;
    private final GameConfig config;

    public KingdomBuilder(GameConfig config)
    {
        this.resources = new KingdomResources(config.kingdomStartConfiguration().resources());
        this.buildings = new KingdomBuildings(config.kingdomStartConfiguration().buildings());
        this.units = new KingdomUnits(config.kingdomStartConfiguration().units());

        this.config = config;
    }

    public KingdomBuilder withBuildingPoints(int buildingPoints)
    {
        this.resources.setCount(ResourceName.buildingPoints, buildingPoints);
        return this;
    }

    public KingdomBuilder withLand(int land)
    {
        this.resources.setCount(ResourceName.land, land);
        return this;
    }

    public KingdomBuilder withGold(int gold)
    {
        this.resources.setCount(ResourceName.gold, gold);
        return this;
    }

    public KingdomBuilder withTools(int tools)
    {
        this.resources.setCount(ResourceName.tools, tools);
        return this;
    }

    public KingdomBuilder withUnemployed(int unemployed)
    {
        this.resources.setCount(ResourceName.unemployed, unemployed);
        return this;
    }

    public Kingdom build()
    {
        return new Kingdom(config, resources, buildings, units);
    }
}
