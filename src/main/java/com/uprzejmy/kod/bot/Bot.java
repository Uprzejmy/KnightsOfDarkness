package com.uprzejmy.kod.bot;

public interface Bot
{
	void doAllActions();

	void doMarketAction();

	void doBuildAction();

	void doTrainAction();

	void passTurn();

	String getKingdomInfo();
}