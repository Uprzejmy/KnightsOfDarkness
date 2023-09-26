package com.uprzejmy.kod.bot;

import com.uprzejmy.kod.kingdom.Kingdom;

public interface Bot
{
	void doAllActions();

	void passTurn();

	String getKingdomInfo();

	Kingdom getKingdom();
}