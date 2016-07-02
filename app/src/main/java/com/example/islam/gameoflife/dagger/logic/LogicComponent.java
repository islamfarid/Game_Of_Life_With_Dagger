package com.example.islam.gameoflife.dagger.logic;


import com.example.islam.gameoflife.dagger.application.ApplicationComponent;
import com.example.islam.gameoflife.surfaceviewgame.bussiness.Input;
import com.example.islam.gameoflife.surfaceviewgame.bussiness.Logic;

import dagger.Component;

/**
 * Created by islam on 7/2/16.
 */
@Component(modules = LogicModule.class,dependencies = ApplicationComponent.class)
public interface LogicComponent {
    Input getInput();
    void inject(Logic logic);
}
