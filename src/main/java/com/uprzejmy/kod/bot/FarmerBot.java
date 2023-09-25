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
        doBuildAction();
        doTrainAction();
        doMarketAction();
    }

    @Override
    public void doMarketAction()
    {
        postFoodOffer();
        BotFunctions.buyTools(kingdom, 0.2);
    }

    private void postFoodOffer()
    {
        var foodAmount = kingdom.getResources().getCount(ResourceName.food);
        var foodUpkeepCost = kingdom.getFoodUpkeepCost();
        var amountToOffer = foodAmount - foodUpkeepCost;

        if (amountToOffer > 0)
        {
            kingdom.postMarketOffer(MarketResource.food, amountToOffer, 20);
        }
    }

    @Override
    public void doBuildAction()
    {
        BotFunctions.buildAndBuyLandIfNeeded(kingdom, BuildingName.farm);
    }

    @Override
    public void doTrainAction()
    {
        BotFunctions.trainUnits(kingdom, UnitName.farmer);
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
