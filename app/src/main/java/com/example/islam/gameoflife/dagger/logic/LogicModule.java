package com.example.islam.gameoflife.dagger.logic;


import com.example.islam.gameoflife.surfaceviewgame.bussiness.Input;

import dagger.Module;
import dagger.Provides;

/**
 * Created by islam on 7/2/16.
 */
@Module
public class LogicModule {
    @Provides
    public Input provideInput( ){
        return  new Input();
    }
}
