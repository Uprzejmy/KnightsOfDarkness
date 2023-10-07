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
        var foodToEat = Math.min(kingdom.getResources().getCount(ResourceName.food), kingdom.getFoodUpkeep());
        kingdom.getResources().subtractCount(ResourceName.food, foodToEat);
    }

    private void doProduction()
    {
        var production = kingdom.getConfig().production();

        for (var unitName : UnitName.getProductionUnits())
        {
            var resourceType = production.getResource(unitName);
            var resourceProduction = kingdom.getUnits().getCount(unitName) * production.getProductionRate(unitName);
            if (unitName == UnitName.blacksmith)
            {
                var neededIron = resourceProduction; // TODO have the rate somewhere in the config
                var maxIronToSpend = Math.min(neededIron, kingdom.getResources().getCount(ResourceName.iron));
                resourceProduction = Math.min(resourceProduction, maxIronToSpend);
                kingdom.getResources().subtractCount(ResourceName.iron, maxIronToSpend);
            }
            kingdom.getResources().addCount(resourceType, (int) Math.round(resourceProduction * getProductionBonus()));
        }
    }

    private void getNewPeople()
    {
        var housingCapacity = kingdom.getBuildings().getCount(BuildingName.house) * kingdom.getConfig().buildingCapacity().getCapacity(BuildingName.house);
        var peopleCount = kingdom.getTotalPeopleCount();
        if (housingCapacity > peopleCount)
        {
            kingdom.getResources().addCount(ResourceName.unemployed, housingCapacity - peopleCount);
        } else if (housingCapacity < peopleCount)
        {
            // TODO test
            // TODO fire workers here
            kingdom.getResources().subtractCount(ResourceName.unemployed, peopleCount - housingCapacity);
        }
    }

    private double getProductionBonus()
    {
        var land = kingdom.getResources().getCount(ResourceName.land);
        if (land > 1000)
        {
            return 1.0;
        }

        var landFactor = 1000 - Math.max(100, land); // we don't give a bonus for land below 100 to avoid exploits
        var bonus = getBonusFactorBasedOnLand(landFactor);

        return bonus;
    }

    /**
     * the bonus factor decreases exponentially, max is 5 at 100 land, min is 1 at
     * 1000 land
     * 
     * @param land
     * @return
     */
    private double getBonusFactorBasedOnLand(int land)
    {
        var bonus = 6.5 * Math.exp(-0.0047 * land) - 0.06;
        var minBonus = Math.max(0, bonus);
        return 1 + minBonus;
    }
}