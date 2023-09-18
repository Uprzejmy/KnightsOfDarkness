package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.*;
import com.uprzejmy.kod.market.MarketResource;

public class GoldMinerBot
{
    private final Kingdom kingdom;

    public GoldMinerBot(Kingdom kingdom)
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
        buyFood();

    }

    private void buyFood()
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeepCost = kingdom.getUnits().countAll() * 10;
        var prefferedAmount = foodUpkeepCost * 3;
        var amountToBuy = Math.max(0, prefferedAmount - foodAmount);

        while (amountToBuy > 0)
        {
            // TODO find cheapest
            // TODO stop buying if there are no offers
            var offer = kingdom.getMarket().getOffersByResource(MarketResource.food).get(0);
            var amountBought = kingdom.buyMarketOffer(offer, amountToBuy);
            amountToBuy -= amountBought;
        }
    }

    public void doBuildAction()
    {
        var toBuild = new KingdomBuildings();
        var cheaperBuildingCost = Math.min(kingdom.getConfig().buildingPointCosts().goldMine(), kingdom.getConfig().buildingPointCosts().house());
        while (kingdom.getUnusedLand() > 0 && kingdom.getResources().getCount(ResourceName.buildingPoints) > cheaperBuildingCost)
        {
            toBuild.addCount(BuildingName.house, 1);
            toBuild.addCount(BuildingName.goldMine, 1);
            kingdom.build(toBuild);
        }
    }

    public void doTrainAction()
    {
        var toTrain = new KingdomUnits();
        KingdomUnits trainedUnits;
        do
        {
            toTrain.addCount(UnitName.goldMiner, 10);
            toTrain.addCount(UnitName.builder, 2);
            trainedUnits = kingdom.train(toTrain);
        } while (trainedUnits.countAll() > 0);
    }


    public void passTurn()
    {
        kingdom.passTurn();
    }
}
