package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class BlacksmithBot implements Bot
{
    private final Kingdom kingdom;

    public BlacksmithBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    @Override
    public void doAllActions()
    {
        BotFunctions.buyFoodForUpkeep(kingdom);
        BotFunctions.buyEnoughIronToMaintainFullProduction(kingdom);
        BotFunctions.buyLandToMaintainUnused(kingdom, 2);
        BotFunctions.build(kingdom, BuildingName.house, 1);
        BotFunctions.build(kingdom, BuildingName.workshop, 1);
        BotFunctions.trainUnits(kingdom, UnitName.builder, 1);
        BotFunctions.trainUnits(kingdom, UnitName.blacksmith, 5);
        postToolsOffer();
        BotFunctions.buyEnoughIronToMaintainFullProduction(kingdom);
    }

    private void postToolsOffer()
    {
        var toolsAmount = kingdom.getResources().getCount(ResourceName.tools);

        if (toolsAmount > 0)
        {
            kingdom.postMarketOffer(MarketResource.tools, toolsAmount, 50);
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
        return String.format("[%s] passed turn, land: %d, houses: %d, workshops: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getResources().getCount(ResourceName.land), kingdom.getBuildings().getCount(BuildingName.house),
                kingdom.getBuildings().getCount(BuildingName.workshop), kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }

    @Override
    public Kingdom getKingdom()
    {
        return kingdom;
    }
}
