package com.uprzejmy.kod.kingdom;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class KingdomSpecialBuilding
{
    SpecialBuildingName buildingType;
    int level = 0;

    public KingdomSpecialBuilding(SpecialBuildingName specialBuilding)
    {
        buildingType = specialBuilding;
    }
}

public class KingdomSpecialBuildings
{
    List<KingdomSpecialBuilding> specialBuildings;

    public KingdomSpecialBuildings()
    {
        specialBuildings = Arrays.asList(new KingdomSpecialBuilding(SpecialBuildingName.emptyBuilding), new KingdomSpecialBuilding(SpecialBuildingName.emptyBuilding), new KingdomSpecialBuilding(SpecialBuildingName.emptyBuilding),
                new KingdomSpecialBuilding(SpecialBuildingName.emptyBuilding), new KingdomSpecialBuilding(SpecialBuildingName.emptyBuilding));
    }

    public int countAll()
    {
        return specialBuildings.size();
    }

    public Optional<Integer> startNew(SpecialBuildingName specialBuilding, int buildingPlace)
    {
        int listIndex = buildingPlace - 1;
        // TODO move hardcoded value to config
        if (buildingPlace < 1 || buildingPlace > 5)
        {
            return Optional.empty();
        }

        if (specialBuildings.get(listIndex).buildingType != SpecialBuildingName.emptyBuilding)
        {
            return Optional.empty();
        }

        specialBuildings.set(listIndex, new KingdomSpecialBuilding(specialBuilding));

        return Optional.of(buildingPlace);
    }

    public void deleteBuilding(int buildingPlace)
    {
        int listIndex = buildingPlace - 1;
        // TODO move hardcoded value to config
        if (buildingPlace < 1 || buildingPlace > 5)
        {
            return;
        }

        specialBuildings.set(listIndex, new KingdomSpecialBuilding(SpecialBuildingName.emptyBuilding));
    }
}
