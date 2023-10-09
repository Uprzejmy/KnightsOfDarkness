package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class BlacksmithBot implements Bot
{
    private final Kingdom kingdom;
    private final double builderToSpecialistRatio = 0.1;
    private final double housesToSpecialistBuildingRatio = 0.55;

    public BlacksmithBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    @Override
    public boolean doAllActions()
    {
        boolean hasAnythingHappened = true;
        do
        {
            hasAnythingHappened = doActionCycle();
        } while (hasAnythingHappened);

        return hasAnythingHappened;
    }

    private boolean doActionCycle()
    {
        int actionResultsAggregate = 0;
        actionResultsAggregate += BotFunctions.buyFoodForUpkeep(kingdom);
        actionResultsAggregate += BotFunctions.buyEnoughIronToMaintainFullProduction(kingdom);
        withdrawToolsOffer();
        actionResultsAggregate += BotFunctions.trainUnits(kingdom, UnitName.blacksmith, 3);
        actionResultsAggregate += BotFunctions.trainBuilders(kingdom, 1, builderToSpecialistRatio);
        actionResultsAggregate += BotFunctions.trainUnits(kingdom, UnitName.blacksmith, 2);
        actionResultsAggregate += BotFunctions.buyLandToMaintainUnused(kingdom, 2);
        actionResultsAggregate += BotFunctions.buildSpecialistBuilding(kingdom, BuildingName.workshop, 1);
        actionResultsAggregate += BotFunctions.buildHouses(kingdom, 1, housesToSpecialistBuildingRatio);
        postToolsOffer();
        actionResultsAggregate += BotFunctions.buyEnoughIronToMaintainFullProduction(kingdom);

        boolean hasAnythingHappen = actionResultsAggregate > 0;
        return hasAnythingHappen;
    }

    private int withdrawToolsOffer()
    {
        var kingdomOffers = kingdom.getMarketOffers();
        var count = kingdomOffers.size();
        for (var offer : kingdomOffers)
        {
            kingdom.withdrawMarketOffer(offer);
        }

        return count;
    }

    private int postToolsOffer()
    {
        var toolsAmount = kingdom.getResources().getCount(ResourceName.tools);

        if (toolsAmount > 0)
        {
            kingdom.postMarketOffer(MarketResource.tools, toolsAmount, 100);
        }

        return toolsAmount;
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
