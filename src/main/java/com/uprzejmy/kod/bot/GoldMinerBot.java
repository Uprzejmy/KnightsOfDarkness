package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;

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
        BotFunctions.buyFood(kingdom);
        BotFunctions.buyTools(kingdom, 0.35);
    }

    @Override
    public void doBuildAction()
    {
        BotFunctions.buildAndBuyLandIfNeeded(kingdom, BuildingName.goldMine);
    }

    @Override
    public void doTrainAction()
    {
        BotFunctions.trainUnits(kingdom, UnitName.goldMiner);
    }

    @Override
    public void passTurn()
    {
        kingdom.passTurn();
    }

    @Override
    public String getKingdomInfo()
    {
        return String.format("[%s] passed turn, land: %d, houses: %d, gold mines: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getResources().getCount(ResourceName.land), kingdom.getBuildings().getCount(BuildingName.house),
                kingdom.getBuildings().getCount(BuildingName.goldMine),
                kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }

    @Override
    public Kingdom getKingdom()
    {
        return kingdom;
    }
}
