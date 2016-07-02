package com.example.islam.gameoflife.dagger.gamemain;


import com.example.islam.gameoflife.dagger.application.ApplicationComponent;
import com.example.islam.gameoflife.gamemain.presenter.GamePresenter;
import com.example.islam.gameoflife.gamemain.view.GameActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by islam on 7/1/16.
 */
@Singleton
@Component(modules = GameModule.class , dependencies = ApplicationComponent.class)
public interface GameComponent {
    GamePresenter getGamePresenter();
    void inject(GameActivity gameActivity);
}
