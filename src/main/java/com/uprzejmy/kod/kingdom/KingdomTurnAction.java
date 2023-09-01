package com.uprzejmy.kod.kingdom;

public class KingdomTurnAction
{
    private final Kingdom kingdom;

    public KingdomTurnAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public void passTurn()
    {
        doProduction();
        getNewPeople();
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
        var peopleCount = kingdom.getUnits().countAll() + kingdom.getResources().getCount(ResourceName.unemployed);
        kingdom.getResources().addCount(ResourceName.unemployed, housingCapacity - peopleCount);
    }
}