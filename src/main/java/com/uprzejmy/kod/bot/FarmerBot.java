package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.BuildingName;
import com.uprzejmy.kod.kingdom.Kingdom;
import com.uprzejmy.kod.kingdom.KingdomBuildings;
import com.uprzejmy.kod.kingdom.KingdomUnits;
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
        var toBuild = new KingdomBuildings();
        toBuild.addCount(BuildingName.house, 1);
        toBuild.addCount(BuildingName.farm, 1);
        var cheaperBuildingCost = Math.min(kingdom.getConfig().buildingPointCosts().farm(), kingdom.getConfig().buildingPointCosts().house());
        while (kingdom.getResources().getCount(ResourceName.buildingPoints) > cheaperBuildingCost)
        {
            if (kingdom.getUnusedLand() == 0)
            {
                kingdom.buyLand(2);
            }
            kingdom.build(toBuild);
        }
    }

    @Override
    public void doTrainAction()
    {
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.farmer, 5);
        toTrain.addCount(UnitName.builder, 1);
        KingdomUnits trainedUnits;
        do
        {
            trainedUnits = kingdom.train(toTrain);
        } while (trainedUnits.countAll() > 0);
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
                kingdom.getBuildings().getCount(BuildingName.farm),
                kingdom.getResources().getCount(ResourceName.gold), kingdom.getResources().getCount(ResourceName.food));
    }
}
