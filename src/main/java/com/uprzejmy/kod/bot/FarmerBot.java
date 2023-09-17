package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.*;

public class FarmerBot
{
    private final Kingdom kingdom;

    public FarmerBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public void doAllActions()
    {
        doBuildAction();
        doTrainAction();
    }

    public void doBuildAction()
    {
        var toBuild = new KingdomBuildings();
        var cheaperBuildingCost = Math.min(kingdom.getConfig().buildingPointCosts().farm(), kingdom.getConfig().buildingPointCosts().house());
        while (kingdom.getUnusedLand() > 0 && kingdom.getResources().getCount(ResourceName.buildingPoints) > cheaperBuildingCost)
        {
            toBuild.addCount(BuildingName.house, 1);
            toBuild.addCount(BuildingName.farm, 1);
            kingdom.build(toBuild);
        }
    }

    public void doTrainAction()
    {
        var toTrain = new KingdomUnits();
        KingdomUnits trainedUnits;
        do
        {
            toTrain.addCount(UnitName.farmer, 10);
            toTrain.addCount(UnitName.builder, 2);
            trainedUnits = kingdom.train(toTrain);
        } while (trainedUnits.countAll() > 0);
    }

    public void passTurn()
    {
        kingdom.passTurn();
    }
}
