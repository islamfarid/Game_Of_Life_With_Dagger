package com.example.islam.gameoflife.dagger.gamemain;


import com.example.islam.gameoflife.application.GameContext;
import com.example.islam.gameoflife.gamemain.presenter.GamePresenter;
import com.example.islam.gameoflife.gamemain.view.Game;

import dagger.Module;
import dagger.Provides;

/**
 * Created by islam on 7/1/16.
 */
@Module
public class GameModule {
    Game game;
    public GameModule(Game game){
        this.game = game;
    }

    @Provides
    public  GamePresenter provideGamePresenter(GameContext gameContext){
        return  new GamePresenter(game,gameContext);
    }
}
