package com.uprzejmy.kod.kingdom;

import java.util.Set;

public enum ResourceName
{
    land, buildingPoints, unemployed, gold, iron, food, tools, weapons, turns;

    static Set<ResourceName> productionResourceNames()
    {
        return Set.of(gold, iron, food, tools, weapons, buildingPoints);
    }
}
