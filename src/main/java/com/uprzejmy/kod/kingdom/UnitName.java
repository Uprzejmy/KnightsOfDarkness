package com.uprzejmy.kod.kingdom;

import java.util.Set;

public enum UnitName
{
    goldMiner, ironMiner, farmer, blacksmith, builder, carrier, guard, spy, infantry, bowmen, cavalry;

    static Set<UnitName> getProductionUnits()
    {
        return Set.of(goldMiner, ironMiner, farmer, blacksmith, builder);
    }
}
