package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.KingdomBuildings;
import com.uprzejmy.kod.kingdom.KingdomUnits;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class BotFunctions
{
    public static int buyFood(Kingdom kingdom)
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

    public static void buildAndBuyLandIfNeeded(Kingdom kingdom, BuildingName specialistBuilding)
    {
        var toBuild = new KingdomBuildings();
        toBuild.addCount(BuildingName.house, 1);
        toBuild.addCount(specialistBuilding, 1);
        var cheaperBuildingCost = Math.min(kingdom.getConfig().buildingPointCosts().getCost(specialistBuilding), kingdom.getConfig().buildingPointCosts().house());
        while (kingdom.getResources().getCount(ResourceName.buildingPoints) > cheaperBuildingCost)
        {
            if (kingdom.getUnusedLand() == 0)
            {
                kingdom.buyLand(2);
            }
            kingdom.build(toBuild);
        }
    }

    public static void trainUnits(Kingdom kingdom, UnitName specialistUnit)
    {
        var toTrain = new KingdomUnits();
        toTrain.addCount(specialistUnit, 5);
        toTrain.addCount(UnitName.builder, 1);
        KingdomUnits trainedUnits;
        do
        {
            trainedUnits = kingdom.train(toTrain);
        } while (trainedUnits.countAll() > 0);
    }

    public static void buyTools(Kingdom kingdom, double goldPercentage)
    {
        // we always want to spend integer amount of gold and no more than we have, that's why we truncate to lower integer
        var goldToSpend = (int) (kingdom.getResources().getCount(ResourceName.gold) * goldPercentage);

        while (goldToSpend > 0)
        {
            // TODO find cheapest
            var offers = kingdom.getMarket().getOffersByResource(MarketResource.tools);
            if (offers.isEmpty())
            {
                return;
            }

            var offer = offers.get(0);
            var amountToBuy = goldToSpend / offer.getPrice();
            kingdom.buyMarketOffer(offer, amountToBuy);
        }
    }
}