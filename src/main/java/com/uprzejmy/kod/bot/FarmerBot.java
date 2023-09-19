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
        var foodUpkeepCost = kingdom.getFoodUpkeepCost();
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
            toTrain.addCount(UnitName.farmer, 5);
            toTrain.addCount(UnitName.builder, 1);
            trainedUnits = kingdom.train(toTrain);
        } while (trainedUnits.countAll() > 0);
    }

    public void passTurn()
    {
        kingdom.passTurn();
        System.out.println(getKingdomInfo());
    }

    private String getKingdomInfo()
    {
        return String.format("[%s] passed turn, houses: %d, farms: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getBuildings().getCount(BuildingName.house), kingdom.getBuildings().getCount(BuildingName.farm),
                kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }
}
