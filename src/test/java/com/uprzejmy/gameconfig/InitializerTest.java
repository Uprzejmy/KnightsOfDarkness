package com.uprzejmy.gameconfig;

import org.junit.jupiter.api.Test;

class InitializerTest {

    @Test
    void readGameConfig() {
        var initializer = new Initializer();
        var config = initializer.readGameConfig();
        System.out.println(config.toString());
    }
}