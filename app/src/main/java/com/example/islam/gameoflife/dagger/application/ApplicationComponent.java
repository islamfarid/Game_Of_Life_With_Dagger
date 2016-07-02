package com.example.islam.gameoflife.dagger.application;


import com.example.islam.gameoflife.application.GameContext;

import dagger.Component;

/**
 * Created by islam on 7/1/16.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    GameContext getGameContext();
}
