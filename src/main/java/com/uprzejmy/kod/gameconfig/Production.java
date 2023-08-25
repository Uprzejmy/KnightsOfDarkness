package com.uprzejmy.kod.gameconfig;

import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;

public record Production(UnitProduction goldMiner, UnitProduction ironMiner, UnitProduction farmer, UnitProduction blacksmith, UnitProduction builder)
{
    public int getProductionRate(UnitName name)
    {
        return switch (name)
        {
            case goldMiner -> goldMiner.rate();
            case ironMiner -> ironMiner.rate();
            case farmer -> farmer.rate();
            case blacksmith -> blacksmith.rate();
            case builder -> builder.rate();
            default -> throw new RuntimeException("This unit doesn't have production capabilities");
        };
    }

    public ResourceName getResource(UnitName name)
    {
        return switch (name)
        {
            case goldMiner -> goldMiner.resource();
            case ironMiner -> ironMiner.resource();
            case farmer -> farmer.resource();
            case blacksmith -> blacksmith.resource();
            case builder -> builder.resource();
            default -> throw new RuntimeException("This unit doesn't have production capabilities");
        };
    }
}