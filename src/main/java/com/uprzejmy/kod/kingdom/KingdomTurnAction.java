package com.uprzejmy.kod.kingdom;

public class KingdomTurnAction
{
    private final Kingdom kingdom;

    public KingdomTurnAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public boolean passTurn()
    {
        if (kingdom.getResources().getCount(ResourceName.turns) <= 0)
        {
            return false;
        }

        kingdom.getResources().subtractCount(ResourceName.turns, 1);
        eatFood();
        doProduction();
        getNewPeople();

        return true;
    }

    private void eatFood()
    {
        // TODO consequences of not having enough food
        var foodToEat = Math.min(kingdom.getResources().getCount(ResourceName.food), kingdom.getFoodUpkeepCost());
        kingdom.getResources().subtractCount(ResourceName.food, foodToEat);
    }

    private void doProduction()
    {
        var production = kingdom.getConfig().production();

        for (var unitName : UnitName.getProductionUnits())
        {
            var resourceType = production.getResource(unitName);
            var resourceProduction = kingdom.getUnits().getCount(unitName) * production.getProductionRate(unitName);
            kingdom.getResources().addCount(resourceType, resourceProduction);
        }
    }

    private void getNewPeople()
    {
        var housingCapacity = kingdom.getBuildings().getCount(BuildingName.house) * kingdom.getConfig().buildingCapacity().getCapacity(BuildingName.house);
        var peopleCount = kingdom.getTotalPeopleCount();
        var peopleToAdd = housingCapacity - peopleCount;
        if (peopleToAdd > 0)
        {
            kingdom.getResources().addCount(ResourceName.unemployed, housingCapacity - peopleCount);
        } else
        {
            // TODO fire workers here
            kingdom.getResources().setCount(ResourceName.unemployed, 0);
        }
    }
}