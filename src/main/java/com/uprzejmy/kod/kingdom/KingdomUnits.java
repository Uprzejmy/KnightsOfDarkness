package com.uprzejmy.kod.kingdom;

import java.util.EnumMap;
import java.util.Map;

public class KingdomUnits
{
    final Map<UnitName, Integer> units = new EnumMap<>(UnitName.class);

    public KingdomUnits()
    {
        for (var name : UnitName.values())
        {
            units.put(name, 0);
        }
    }

    public int getCount(UnitName name)
    {
        return units.get(name);
    }

    public void addCount(UnitName name, int count)
    {
        units.put(name, units.get(name) + count);
    }

    public void subtractCount(UnitName name, int count)
    {
        units.put(name, units.get(name) - count);
    }

    public void setCount(UnitName name, int count)
    {
        units.put(name, count);
    }

    public int countAll()
    {
        return units.values().stream().mapToInt(Integer::intValue).sum();
    }
}
