package com.uprzejmy.kod.gameconfig;

public record GameConfig(
        BuildingPointCosts buildingPointCosts,
        BuildingCapacity buildingCapacity,
        TrainingCost trainingCost,
        Production production,
        KingdomStartConfiguration kingdomStartConfiguration)
{
}
