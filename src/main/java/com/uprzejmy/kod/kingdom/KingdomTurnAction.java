package com.uprzejmy.kod.kingdom;

public class KingdomTurnAction
{
    private final Kingdom kingdom;

    public KingdomTurnAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    public void passTurn()
    {
        processResources();
        // by using EnumSet we make sure the names are ordered as specified in the enum declaration
//        var unitNames = EnumSet.copyOf(unitsToTrain.units.keySet());
//        for (var unitName : unitNames)
//        {
//            var trainingCost = kingdom.getConfig().trainingCost().getTrainingCost(unitName);
//            var maximumToAfford = calculateMaximumToAfford(kingdom.getResources(), trainingCost);
//            var howManyToTrain = Math.min(unitsToTrain.getCount(unitName), maximumToAfford);
//            kingdom.getResources().subtractCount(ResourceName.gold, howManyToTrain * trainingCost.gold());
//            kingdom.getResources().subtractCount(ResourceName.tools, howManyToTrain * trainingCost.tools());
//            kingdom.getResources().subtractCount(ResourceName.weapons, howManyToTrain * trainingCost.weapons());
//            kingdom.getResources().subtractCount(ResourceName.unemployed, howManyToTrain);
//            kingdom.getUnits().addCount(unitName, howManyToTrain);
//        }
    }

    private void processResources()
    {

    }
}