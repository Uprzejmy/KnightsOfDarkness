package com.uprzejmy.kod.kingdom;

import java.util.List;

import com.uprzejmy.kod.game.Game;
import com.uprzejmy.kod.gameconfig.GameConfig;
import com.uprzejmy.kod.market.Market;
import com.uprzejmy.kod.market.MarketOffer;
import com.uprzejmy.kod.market.MarketResource;

public class Kingdom
{
    private final Market market;
    private final GameConfig config;
    private final KingdomResources resources;
    private final KingdomBuildings buildings;
    private final KingdomUnits units;
    private final KingdomBuildAction kingdomBuildAction = new KingdomBuildAction(this);
    private final KingdomTrainAction kingdomTrainAction = new KingdomTrainAction(this);
    private final KingdomTurnAction kingdomTurnAction = new KingdomTurnAction(this);
    private final KingdomMarketAction kingdomMarketAction = new KingdomMarketAction(this);

    public Kingdom(Game game, KingdomResources resources, KingdomBuildings buildings, KingdomUnits units)
    {
        this.config = game.getConfig();
        this.market = game.getMarket();
        this.resources = resources;
        this.buildings = buildings;
        this.units = units;
    }

    public void build(KingdomBuildings buildingsToBuild)
    {
        kingdomBuildAction.build(buildingsToBuild);
    }

    public KingdomUnits train(KingdomUnits unitsToTrain)
    {
        return kingdomTrainAction.train(unitsToTrain);
    }

    public boolean passTurn()
    {
        return kingdomTurnAction.passTurn();
    }

    public int getUnusedLand()
    {
        return resources.getCount(ResourceName.land) - buildings.countAll();
    }

    public void postMarketOffer(MarketResource resource, int count, int price)
    {
        this.kingdomMarketAction.postOffer(resource, count, price);
    }

    public List<MarketOffer> getMarketOffers()
    {
        return this.kingdomMarketAction.getMyOffers();
    }

    public KingdomResources getResources()
    {
        return resources;
    }

    public KingdomBuildings getBuildings()
    {
        return buildings;
    }

    public KingdomUnits getUnits()
    {
        return units;
    }

    public GameConfig getConfig()
    {
        return config;
    }

    public Market getMarket()
    {
        return market;
    }
}
