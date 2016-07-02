package com.example.islam.gameoflife.dagger.application;


import com.example.islam.gameoflife.application.GameContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by islam on 7/1/16.
 */
@Module
public class ApplicationModule {
    GameContext gameContext;
    public ApplicationModule(GameContext gameContext){
        this.gameContext = gameContext;
    }
    @Provides
    public GameContext provideGameContext() {
        return gameContext;
    }
}
