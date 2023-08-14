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
    {   int sum= 0;

        for(Integer entry: buildings.values())
        {
            sum+= entry;
        }

        return sum;
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
