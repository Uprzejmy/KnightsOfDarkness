package com.uprzejmy.kod.gameconfig;

import com.uprzejmy.kod.kingdom.ResourceName;

public record KingdomStartingResources(int land, int buildingPoints, int gold, int iron, int tools, int weapons)
{
    public int getCount(ResourceName name)
    {
        return switch (name)
        {
            case land -> land;
            case buildingPoints -> buildingPoints;
            case gold -> gold;
            case iron -> iron;
            case tools -> tools;
            case weapons -> weapons;
        };
    }
}
