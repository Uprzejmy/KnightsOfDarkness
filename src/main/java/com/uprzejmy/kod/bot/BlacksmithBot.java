package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;

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
        doBuildAction();
        doTrainAction();
        doMarketAction();
    }

    @Override
    public void doMarketAction()
    {
        BotFunctions.buyFood(kingdom);
    }

    @Override
    public void doBuildAction()
    {
        BotFunctions.buildAndBuyLandIfNeeded(kingdom, BuildingName.workshop);
    }

    @Override
    public void doTrainAction()
    {
        BotFunctions.trainUnits(kingdom, UnitName.blacksmith);
    }

    @Override
    public void passTurn()
    {
        kingdom.passTurn();
        System.out.println(getKingdomInfo());
    }

    @Override
    public String getKingdomInfo()
    {
        return String.format("[%s] passed turn, land: %d, houses: %d, farms: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getResources().getCount(ResourceName.land), kingdom.getBuildings().getCount(BuildingName.house),
                kingdom.getBuildings().getCount(BuildingName.farm), kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }
}
