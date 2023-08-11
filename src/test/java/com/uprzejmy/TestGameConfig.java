package com.uprzejmy;

import com.uprzejmy.gameconfig.GameConfig;
import com.uprzejmy.gameconfig.Initializer;

public class TestGameConfig
{
    public GameConfig get()
    {
        return Initializer.readGameConfig();
    }
}
