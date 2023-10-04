package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.ResourceName;
import com.uprzejmy.kod.kingdom.UnitName;
import com.uprzejmy.kod.market.MarketResource;

public class IronMinerBot implements Bot
{
    private final Kingdom kingdom;

    public IronMinerBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    @Override
    public void doAllActions()
    {
        BotFunctions.buyFoodForUpkeep(kingdom);
        BotFunctions.buyLandToMaintainUnused(kingdom, 2);
        BotFunctions.build(kingdom, BuildingName.house, 1);
        BotFunctions.build(kingdom, BuildingName.ironMine, 1);
        BotFunctions.buyToolsToMaintainCount(kingdom, 5 * 15 + 20); // TODO calculate this from training cost configuration
        BotFunctions.trainUnits(kingdom, UnitName.builder, 1);
        BotFunctions.trainUnits(kingdom, UnitName.ironMiner, 5);
        postIronOffer();
    }

    private void postIronOffer()
    {
        var ironAmount = kingdom.getResources().getCount(ResourceName.iron);

        if (ironAmount > 0)
        {
            kingdom.postMarketOffer(MarketResource.iron, ironAmount, 40);
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
        return String.format("[%s] passed turn, land: %d, houses: %d, iron mines: %d, gold: %d, food: %d", kingdom.getName(), kingdom.getResources().getCount(ResourceName.land), kingdom.getBuildings().getCount(BuildingName.house),
                kingdom.getBuildings().getCount(BuildingName.ironMine), kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }

    @Override
    public Kingdom getKingdom()
    {
        return kingdom;
    }
}
