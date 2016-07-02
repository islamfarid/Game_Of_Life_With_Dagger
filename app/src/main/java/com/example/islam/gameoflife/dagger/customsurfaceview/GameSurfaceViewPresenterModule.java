package com.example.islam.gameoflife.dagger.customsurfaceview;


import com.example.islam.gameoflife.application.GameContext;
import com.example.islam.gameoflife.surfaceviewgame.bussiness.Logic;
import com.example.islam.gameoflife.surfaceviewgame.presenter.GameViewPresenter;
import com.example.islam.gameoflife.surfaceviewgame.view.SurfaceGameView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by islam on 7/2/16.
 */
@Module
public class GameSurfaceViewPresenterModule {
    SurfaceGameView gameView;
    public GameSurfaceViewPresenterModule(SurfaceGameView gameView){
        this.gameView = gameView;
    }
    @Provides
    public GameViewPresenter provideGameViewPresenter(GameContext gameContext){
        return  new GameViewPresenter(gameView,gameContext);
    }
    @Singleton
    @Provides
    public Logic provideLogic(GameContext gameContext){
        return  new Logic(gameContext);
    }
}
