package com.uprzejmy.kod.kingdom;

import java.util.List;

public enum UnitName
{
    goldMiner, ironMiner, farmer, blacksmith, builder, carrier, guard, spy, infantry, bowmen, cavalry;

    static List<UnitName> getProductionUnits()
    {
        return List.of(goldMiner, ironMiner, farmer, blacksmith, builder);
    }
}
