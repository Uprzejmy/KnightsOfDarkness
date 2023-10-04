package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.KingdomUnits;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class BotFunctions
{
    public static int buyFoodForUpkeep(Kingdom kingdom)
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeep = kingdom.getFoodUpkeep();
        var amountToBuy = Math.max(0, foodUpkeep - foodAmount);
        var totalBought = 0;

        // TODO accumulation of amountToBuy and totalBought is the same thing
        while (amountToBuy > 0)
        {
            var optionalOffer = kingdom.getMarket().getCheapestOfferByResource(MarketResource.food);
            if (optionalOffer.isEmpty())
            {
                return totalBought;
            }

            var offer = optionalOffer.get();
            var amountBought = kingdom.buyMarketOffer(offer, amountToBuy);
            if (amountBought == 0)
            {
                // Could not afford, TODO tests
                return totalBought;
            }
            amountToBuy -= amountBought;
            totalBought += amountBought;
        }

        return totalBought;
    }

    public static int buildAndBuyLandIfNeeded(Kingdom kingdom, BuildingName specialistBuilding)
    {
        int totalBuilt = 0;
        int howManyWereBuilt = 0;
        do
        {
            howManyWereBuilt = 0;
            if (kingdom.getUnusedLand() < 2)
            {
                assert kingdom.getUnusedLand() >= 0;
                kingdom.buyLand(2);
            }
            howManyWereBuilt += kingdom.build(BuildingName.house, 1);
            howManyWereBuilt += kingdom.build(specialistBuilding, 1);
            totalBuilt += howManyWereBuilt;
        }
        while (howManyWereBuilt > 0);

        return totalBuilt;
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
            var optionalOffer = kingdom.getMarket().getCheapestOfferByResource(MarketResource.tools);
            if (optionalOffer.isEmpty())
            {
                return;
            }

            var offer = optionalOffer.get();
            var amountToBuy = goldToSpend / offer.getPrice();
            kingdom.buyMarketOffer(offer, amountToBuy);
        }
    }

    public static int buyEnoughIronToMaintainFullProduction(Kingdom kingdom)
    {
        var blacksmithProduction = kingdom.getUnits().getCount(UnitName.blacksmith) * kingdom.getConfig().production().getProductionRate(UnitName.blacksmith);
        var ironNeeded = blacksmithProduction;

        var ironAmount = kingdom.getResources().getCount(ResourceName.iron);
        var amountToBuy = Math.max(0, ironNeeded - ironAmount);
        var totalBought = 0;

        // TODO accumulation of amountToBuy and totalBought is the same thing
        while (amountToBuy > 0)
        {
            var optionalOffer = kingdom.getMarket().getCheapestOfferByResource(MarketResource.iron);
            if (optionalOffer.isEmpty())
            {
                return totalBought;
            }

            var offer = optionalOffer.get();
            var amountBought = kingdom.buyMarketOffer(offer, amountToBuy);
            if (amountBought == 0)
            {
                // Could not afford, TODO tests
                return totalBought;
            }
            amountToBuy -= amountBought;
            totalBought += amountBought;
        }

        return totalBought;
    }

    public static void buyLandToMaintainUnused(Kingdom kingdom, int count)
    {
        var unusedLand = kingdom.getUnusedLand();
        assert unusedLand >= 0;
        if (unusedLand < 2)
        {
            kingdom.buyLand(2);
        }
    }

    public static void build(Kingdom kingdom, BuildingName building, int count)
    {
        kingdom.build(building, count);
    }

    public static void trainUnits(Kingdom kingdom, UnitName unit, int count)
    {
        var toTrain = new KingdomUnits();
        toTrain.addCount(unit, count);
        kingdom.train(toTrain);
    }

    public static void buyToolsToMaintainCount(Kingdom kingdom, int count)
    {
        var optionalOffer = kingdom.getMarket().getCheapestOfferByResource(MarketResource.tools);
        if (optionalOffer.isEmpty())
        {
            return;
        }

        var offer = optionalOffer.get();
        kingdom.buyMarketOffer(offer, count);
    }
}
