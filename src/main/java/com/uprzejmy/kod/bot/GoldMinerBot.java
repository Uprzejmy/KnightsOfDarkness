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
        BotFunctions.buyFoodForUpkeep(kingdom);
        BotFunctions.buyLandToMaintainUnused(kingdom, 2);
        BotFunctions.build(kingdom, BuildingName.house, 1);
        BotFunctions.build(kingdom, BuildingName.goldMine, 1);
        BotFunctions.buyToolsToMaintainCount(kingdom, 5 * 15 + 20); // TODO calculate this from training cost configuration
        BotFunctions.trainUnits(kingdom, UnitName.builder, 1);
        BotFunctions.trainUnits(kingdom, UnitName.goldMiner, 5);
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
