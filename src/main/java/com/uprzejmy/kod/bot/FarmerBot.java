package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.*;
import com.uprzejmy.kod.market.MarketResource;

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
        doMarketAction();
    }

    public void doMarketAction()
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeepCost = kingdom.getUnits().countAll() * 10;
        var amountToOffer = foodAmount - foodUpkeepCost;

        if (amountToOffer > 0)
        {
            kingdom.postMarketOffer(MarketResource.food, amountToOffer, 20);
        }
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
