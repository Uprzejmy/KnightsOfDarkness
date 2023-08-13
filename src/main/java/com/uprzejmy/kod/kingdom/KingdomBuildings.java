package com.uprzejmy.kod.kingdom;

import java.util.HashMap;
import java.util.Map;

public class KingdomBuildings
{
    public Map<BuildingName, Integer> buildings = new HashMap<>();

    public KingdomBuildings()
    {
        for (var name : BuildingName.values())
        {
            buildings.put(name, 0);
        }
    }

    public int countAll()
    {
        // TODO return sum of all counts of buildings, not only houses
        return getCount(BuildingName.house);
    }

    public int getCount(BuildingName name)
    {
        return buildings.get(name);
    }

    public void addCount(BuildingName name, int count)
    {
        buildings.put(name, buildings.get(name) + count);
    }
}
