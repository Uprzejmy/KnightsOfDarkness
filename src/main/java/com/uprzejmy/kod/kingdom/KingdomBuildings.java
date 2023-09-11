package com.uprzejmy.kod.kingdom;

import java.util.EnumMap;
import java.util.Map;

public class KingdomBuildings
{
    final Map<BuildingName, Integer> buildings = new EnumMap<>(BuildingName.class);

    public KingdomBuildings()
    {
        for (var name : BuildingName.values())
        {
            buildings.put(name, 0);
        }
    }

    public int countAll()
    {
        return buildings.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getCount(BuildingName name)
    {
        return buildings.get(name);
    }

    public void addCount(BuildingName name, int count)
    {
        buildings.put(name, buildings.get(name) + count);
    }

    public void setCount(BuildingName name, int count)
    {
        buildings.put(name, count);
    }
}
