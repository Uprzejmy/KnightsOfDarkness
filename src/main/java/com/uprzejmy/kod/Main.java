package com.uprzejmy.kod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uprzejmy.kod.gameconfig.Production;
import com.uprzejmy.kod.gameconfig.UnitProduction;
import com.uprzejmy.kod.kingdom.ResourceName;

public class Main
{
    public static void main(String[] args) throws JsonProcessingException
    {
        System.out.println("Hello world!");

        var production = new Production(
                new UnitProduction(10, ResourceName.gold),
                new UnitProduction(10, ResourceName.iron),
                new UnitProduction(10, ResourceName.food),
                new UnitProduction(10, ResourceName.tools),
                new UnitProduction(10, ResourceName.buildingPoints));

        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(production);

        System.out.println(json);
    }
}