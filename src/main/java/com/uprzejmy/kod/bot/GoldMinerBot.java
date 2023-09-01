package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.*;

public class GoldMinerBot
{
    private final Kingdom kingdom;

    public GoldMinerBot(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public void doActions()
    {
        build();
        train();
    }

    private void build()
    {
        var toBuild = new KingdomBuildings();
        toBuild.addCount(BuildingName.house, 5);
        toBuild.addCount(BuildingName.goldMine, 5);
        kingdom.build(toBuild);
    }

    private void train()
    {
        var toTrain = new KingdomUnits();
        toTrain.addCount(UnitName.goldMiner, 10);
        toTrain.addCount(UnitName.builder, 2);
        kingdom.train(toTrain);
    }

    public void passTurn()
    {
        kingdom.passTurn();
    }
}
