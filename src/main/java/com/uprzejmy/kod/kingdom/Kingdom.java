package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.gameconfig.GameConfig;

public class Kingdom
{
    private final GameConfig config;
    private final KingdomResources resources;
    private final KingdomBuildings buildings;
    private final KingdomBuildAction kingdomBuildAction = new KingdomBuildAction(this);

    public Kingdom(GameConfig config, KingdomResources resources, KingdomBuildings buildings)
    {
        this.config = config;
        this.resources = resources;
        this.buildings = buildings;
    }

    public void build(KingdomBuildings buildingsToBuild)
    {
        kingdomBuildAction.build(buildingsToBuild);
    }

    public int getUnusedLand()
    {
        return resources.getCount(ResourceName.land) - buildings.countAll();
    }

    public KingdomResources getResources()
    {
        return resources;
    }

    public KingdomBuildings getBuildings()
    {
        return buildings;
    }

    public GameConfig getConfig()
    {
        return config;
    }
}
