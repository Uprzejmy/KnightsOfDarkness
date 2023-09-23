package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.KingdomBuildings;
import com.uprzejmy.kod.kingdom.KingdomUnits;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class GoldMinerBot implements Bot
{
    private final Kingdom kingdom;

    public GoldMinerBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    @Override
    public void doAllActions()
    {
        doBuildAction();
        doTrainAction();
        doMarketAction();
    }

    @Override
    public void doMarketAction()
    {
        buyFood();
    }

    @Override
    public void doBuildAction()
    {
        var cheaperBuildingCost = Math.min(kingdom.getConfig().buildingPointCosts().goldMine(), kingdom.getConfig().buildingPointCosts().house());
        while (kingdom.getUnusedLand() > 0 && kingdom.getResources().getCount(ResourceName.buildingPoints) > cheaperBuildingCost)
        {
            var toBuild = new KingdomBuildings();
            toBuild.addCount(BuildingName.house, 1);
            toBuild.addCount(BuildingName.goldMine, 1);
            kingdom.build(toBuild);
        }
    }

    @Override
    public void doTrainAction()
    {
        var toTrain = new KingdomUnits();
        KingdomUnits trainedUnits;
        do
        {
            toTrain.addCount(UnitName.goldMiner, 5);
            toTrain.addCount(UnitName.builder, 1);
            trainedUnits = kingdom.train(toTrain);
        } while (trainedUnits.countAll() > 0);
    }

    @Override
    public void passTurn()
    {
        kingdom.passTurn();
        System.out.println(getKingdomInfo());
    }

    private int buyFood()
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeepCost = kingdom.getFoodUpkeepCost();
        var prefferedAmount = foodUpkeepCost * 3;
        var amountToBuy = Math.max(0, prefferedAmount - foodAmount);
        var totalBought = 0;

        // TODO accumulation of amountToBuy and totalBought is the same thing
        while (amountToBuy > 0)
        {
            // TODO find cheapest
            var offers = kingdom.getMarket().getOffersByResource(MarketResource.food);
            if (offers.isEmpty())
            {
                return totalBought;
            }

            var offer = offers.get(0);
            var amountBought = kingdom.buyMarketOffer(offer, amountToBuy);
            amountToBuy -= amountBought;
            totalBought += amountBought;
        }

        return totalBought;
    }

    @Override
    public String getKingdomInfo()
    {
        return String.format("[%s] passed turn, houses: %d, gold mines: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getBuildings().getCount(BuildingName.house), kingdom.getBuildings().getCount(BuildingName.goldMine),
                kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }
}
