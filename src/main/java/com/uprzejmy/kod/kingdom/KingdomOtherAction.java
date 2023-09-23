package com.uprzejmy.kod.kingdom;

class LandTransaction
{
    final int amount;
    final int cost;

    public LandTransaction(int amount, int cost)
    {
        this.amount = amount;
        this.cost = cost;
    }
}

public class KingdomOtherAction
{

    private final Kingdom kingdom;

    public KingdomOtherAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    /**
     * @return amount of land that has been bought
     */
    public int buyLand(int count)
    {
        // check more sophisticated math like this: https://math.stackexchange.com/questions/296476/formula-for-calculating-the-sum-of-a-series-of-function-results-over-a-specific

        // dumb approach to keep adding next calculated cost for each piece of land
        var availableGold = kingdom.getResources().getCount(ResourceName.gold);
        var initialLand = kingdom.getResources().getCount(ResourceName.land);

        var transaction = calculateCost(initialLand, count, availableGold);

        kingdom.getResources().subtractCount(ResourceName.gold, transaction.cost);
        kingdom.getResources().addCount(ResourceName.land, transaction.amount);

        return transaction.amount;
    }

    static LandTransaction calculateCost(int initialLand, int count, int availableGold)
    {
        var power = 2; // linear function f(x) = 10x
        var landPrice = 0;
        var landToBuy = 0;
        while (landToBuy < count && landPrice <= availableGold)
        {
            landToBuy++;
            var priceFactor = initialLand + landToBuy;
            landPrice += Math.pow(priceFactor, power) / 10;
        }

        return new LandTransaction(landToBuy, landPrice);
    }

}
