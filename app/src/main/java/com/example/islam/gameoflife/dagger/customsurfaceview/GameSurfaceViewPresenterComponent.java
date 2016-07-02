package com.example.islam.gameoflife.dagger.customsurfaceview;


import com.example.islam.gameoflife.dagger.application.ApplicationComponent;
import com.example.islam.gameoflife.surfaceviewgame.presenter.GameViewPresenter;
import com.example.islam.gameoflife.surfaceviewgame.view.SurfaceGameViewImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by islam on 7/2/16.
 */
@Singleton
@Component(modules = GameSurfaceViewPresenterModule.class,dependencies = ApplicationComponent.class)
public interface GameSurfaceViewPresenterComponent {
    GameViewPresenter getGameViewPresenter();
    void inject(SurfaceGameViewImpl gameView);
    void inject(GameViewPresenter gameViewPresenter);

}
