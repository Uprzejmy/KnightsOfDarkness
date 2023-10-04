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

    public static int buyLandToMaintainUnused(Kingdom kingdom, int count)
    {
        var unusedLand = kingdom.getUnusedLand();
        assert unusedLand >= 0;
        if (unusedLand < 2)
        {
            return kingdom.buyLand(2);
        }

        return 0;
    }

    public static int build(Kingdom kingdom, BuildingName building, int count)
    {
        return kingdom.build(building, count);
    }

    public static int trainUnits(Kingdom kingdom, UnitName unit, int count)
    {
        var toTrain = new KingdomUnits();
        toTrain.addCount(unit, count);
        var trainedUnits = kingdom.train(toTrain);
        return trainedUnits.countAll();
    }

    public static int buyToolsToMaintainCount(Kingdom kingdom, int count)
    {
        var optionalOffer = kingdom.getMarket().getCheapestOfferByResource(MarketResource.tools);
        if (optionalOffer.isEmpty())
        {
            return 0;
        }

        var offer = optionalOffer.get();
        return kingdom.buyMarketOffer(offer, count);
    }
}
