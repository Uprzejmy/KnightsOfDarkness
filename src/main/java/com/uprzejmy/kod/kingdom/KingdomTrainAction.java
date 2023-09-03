package com.uprzejmy.kod.kingdom;

import com.uprzejmy.kod.gameconfig.UnitTrainingCost;

import java.util.EnumSet;

public class KingdomTrainAction
{
    private final Kingdom kingdom;

    public KingdomTrainAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public KingdomUnits train(KingdomUnits unitsToTrain)
    {
        var trainedUnits = new KingdomUnits();
        // by using EnumSet we make sure the names are ordered as specified in the enum declaration
        var unitNames = EnumSet.copyOf(unitsToTrain.units.keySet());
        for (var unitName : unitNames)
        {
            var trainingCost = kingdom.getConfig().trainingCost().getTrainingCost(unitName);
            var maximumToAfford = calculateMaximumToAfford(kingdom.getResources(), trainingCost);
            var howManyToTrain = Math.min(unitsToTrain.getCount(unitName), maximumToAfford);
            kingdom.getResources().subtractCount(ResourceName.gold, howManyToTrain * trainingCost.gold());
            kingdom.getResources().subtractCount(ResourceName.tools, howManyToTrain * trainingCost.tools());
            kingdom.getResources().subtractCount(ResourceName.weapons, howManyToTrain * trainingCost.weapons());
            kingdom.getResources().subtractCount(ResourceName.unemployed, howManyToTrain);
            kingdom.getUnits().addCount(unitName, howManyToTrain);
            trainedUnits.addCount(unitName, howManyToTrain);
        }

        return trainedUnits;
    }

    private int calculateMaximumToAfford(KingdomResources resources, UnitTrainingCost trainingCost)
    {
        int gold = resources.getCount(ResourceName.gold);
        int tools = resources.getCount(ResourceName.tools);
        int weapons = resources.getCount(ResourceName.weapons);
        int unemployed = resources.getCount(ResourceName.unemployed);

        var maxForGold = gold / trainingCost.gold();
        var maxForEquipment = trainingCost.tools() > 0 ? tools / trainingCost.tools() : weapons / trainingCost.weapons();

        return Math.min(Math.min(maxForGold, maxForEquipment), unemployed);
    }
}