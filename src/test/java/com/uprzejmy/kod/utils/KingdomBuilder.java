package com.uprzejmy.kod.utils;

import com.uprzejmy.kod.game.Game;
import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.KingdomBuildings;
import com.uprzejmy.kod.kingdom.KingdomResources;
import com.uprzejmy.kod.kingdom.KingdomUnits;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;

public class KingdomBuilder
{
    private final KingdomResources resources;
    private final KingdomBuildings buildings;
    private final KingdomUnits units;
    private final Game game;

    public KingdomBuilder(Game game)
    {
        this.resources = new KingdomResources();
        for (var resource : ResourceName.values())
        {
            this.resources.setCount(resource, 1000000);
        }

        this.buildings = new KingdomBuildings();
        for (var building : BuildingName.values())
        {
            this.buildings.setCount(building, 1000);
        }

        this.units = new KingdomUnits();
        for (var unit : UnitName.values())
        {
            this.units.setCount(unit, 1000);
        }

        this.game = game;
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
        return new Kingdom(game, resources, buildings, units);
    }

}