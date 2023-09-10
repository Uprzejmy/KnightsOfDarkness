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

    public KingdomBuilder withResource(ResourceName resource, int count)
    {
        this.resources.setCount(resource, count);
        return this;
    }

    public KingdomBuilder withUnit(UnitName unit, int count)
    {
        this.units.setCount(unit, count);
        return this;
    }

    public KingdomBuilder withBuilding(BuildingName building, int count)
    {
        this.buildings.setCount(building, count);
        return this;
    }

    public Kingdom build()
    {
        return new Kingdom(config, resources, buildings, units);
    }


}
