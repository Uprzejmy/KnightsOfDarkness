package com.uprzejmy.kingdom;

public class KingdomBuildAction
{
    private final Kingdom kingdom;

    public KingdomBuildAction(Kingdom kingdom)
    {
        this.kingdom = kingdom;
    }

    /**
     * simplest algorithm to build from top until the building points are depleted
     */
    public void build(KingdomBuildings buildingsToBuild)
    {
        buildHouses(buildingsToBuild.houses);
        buildGoldMines(buildingsToBuild.goldMines);
        buildIronMines(buildingsToBuild.ironMines);
        buildWorkshops(buildingsToBuild.workshops);
        buildFarms(buildingsToBuild.farms);
        buildMarkets(buildingsToBuild.markets);
        buildBarracks(buildingsToBuild.barracks);
        buildSpyGuilds(buildingsToBuild.spyGuilds);
        buildTowers(buildingsToBuild.towers);
        buildCastles(buildingsToBuild.castles);
    }

    private void buildHouses(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().house();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().houses += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildGoldMines(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().goldMine();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().goldMines += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildIronMines(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().ironMine();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().ironMines += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildWorkshops(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().workshop();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().workshops += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildFarms(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().farm();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().farms += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildMarkets(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().market();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().markets += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildBarracks(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().barracks();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().barracks += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildSpyGuilds(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().spyGuild();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().spyGuilds += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildTowers(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().tower();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().towers += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }

    private void buildCastles(int buildCount)
    {
        var buildingCost = kingdom.getConfig().buildingPointCosts().castle();
        var pointsToPutIntoBuilding = Math.min(kingdom.getResources().buildingPoints, buildCount * buildingCost);
        var fullBuildings = pointsToPutIntoBuilding / buildingCost;
        kingdom.getBuildings().castles += fullBuildings;
        kingdom.getResources().buildingPoints -= pointsToPutIntoBuilding;
    }
}