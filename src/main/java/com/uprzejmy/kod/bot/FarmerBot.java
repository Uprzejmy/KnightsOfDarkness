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
    public void doAllActions()
    {
        BotFunctions.buyLandToMaintainUnused(kingdom, 2);
        BotFunctions.build(kingdom, BuildingName.house, 1);
        BotFunctions.build(kingdom, BuildingName.farm, 1);
        BotFunctions.buyToolsToMaintainCount(kingdom, 5 * 5 + 20); // TODO calculate this from training cost configuration
        BotFunctions.trainUnits(kingdom, UnitName.builder, 1);
        BotFunctions.trainUnits(kingdom, UnitName.farmer, 5);
        postFoodOffer();
    }

    private void postFoodOffer()
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeep = kingdom.getFoodUpkeep();
        var amountToOffer = foodAmount - foodUpkeep;

        if (amountToOffer > 0)
        {
            kingdom.postMarketOffer(MarketResource.food, amountToOffer, 20);
        }
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
