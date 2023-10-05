package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class FarmerBot implements Bot
{
    private final Kingdom kingdom;

    public FarmerBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    @Override
    public boolean doAllActions()
    {
        int actionResultsAggregate = 0;

        actionResultsAggregate += BotFunctions.buyToolsToMaintainCount(kingdom, 5 * 5 + 20); // TODO calculate this from training cost configuration
        actionResultsAggregate += BotFunctions.trainUnits(kingdom, UnitName.builder, 1);
        actionResultsAggregate += BotFunctions.trainUnits(kingdom, UnitName.farmer, 5);
        actionResultsAggregate += BotFunctions.buyLandToMaintainUnused(kingdom, 2);
        actionResultsAggregate += BotFunctions.build(kingdom, BuildingName.house, 1);
        actionResultsAggregate += BotFunctions.build(kingdom, BuildingName.farm, 1);
        actionResultsAggregate += postFoodOffer();

        boolean hasAnythingHappen = actionResultsAggregate > 0;
        return hasAnythingHappen;
    }

    private int postFoodOffer()
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeep = kingdom.getFoodUpkeep();
        var amountToOffer = Math.max(0, foodAmount - foodUpkeep);

        if (amountToOffer > 0)
        {
            kingdom.postMarketOffer(MarketResource.food, amountToOffer, 20);
        }

        return amountToOffer;
    }

    @Override
    public void passTurn()
    {
        kingdom.passTurn();
    }

    @Override
    public String getKingdomInfo()
    {
        return String.format("[%s] passed turn, land: %d, houses: %d, farms: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getResources().getCount(ResourceName.land), kingdom.getBuildings().getCount(BuildingName.house),
                kingdom.getBuildings().getCount(BuildingName.farm),
                kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }

    @Override
    public Kingdom getKingdom()
    {
        return kingdom;
    }
}
