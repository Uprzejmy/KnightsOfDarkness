package com.uprzejmy.kod.gameconfig;

import com.uprzejmy.kod.kingdom.BuildingName;

public record BuildingPointCosts(int house, int goldMine, int ironMine, int workshop, int farm, int market, int barracks, int spyGuild, int tower, int castle)
{
    public int getCost(BuildingName name)
    {
        switch (name)
        {
            case house:
                return house;
            case goldMine:
                return goldMine;
            case ironMine:
                return ironMine;
            case workshop:
                return workshop;
            case farm:
                return farm;
        }

        throw new RuntimeException("BuildingName value not handled for " + name);
    }
}
