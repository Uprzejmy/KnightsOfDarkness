package com.uprzejmy.kod.kingdom;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class KingdomOtherActionTest
{
    @Test
    void testBuyLand_101()
    {
        var transaction = KingdomOtherAction.calculateCost(100, 1, 5000);
        assertEquals(1, transaction.amount);
        assertEquals(1020, transaction.cost);
    }

    @Test
    void testBuyLand_1001()
    {
        var transaction = KingdomOtherAction.calculateCost(1000, 1, 5000);
        assertEquals(1, transaction.amount);
        assertEquals(100200, transaction.cost);
    }

    @Test
    void testBuyLand_from100_to200()
    {
        var transaction = KingdomOtherAction.calculateCost(1000, 200, 99999999);
        assertEquals(200, transaction.amount);
        assertEquals(24288580, transaction.cost);
    }
}
