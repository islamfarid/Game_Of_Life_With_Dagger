package com.example.islam.gameoflife.gamemain.presenter;


import com.example.islam.gameoflife.R;
import com.example.islam.gameoflife.application.GameContext;
import com.example.islam.gameoflife.gamemain.view.Game;

import javax.inject.Inject;

/**
 * Created by islam on 7/1/16.
 */
public class GamePresenter {
    Game game;
    GameContext gameContext;
    @Inject
    public GamePresenter(Game game, GameContext gameContext) {
        this.game=game;
        this.gameContext = gameContext;
    }

    public void handleStartAndStopTheGame(String currentText) {
        if (currentText.equals(gameContext.getResources().getString(R.string.stop_button_text))) {
            game.stopGame();
            game.chatngeButtonText(gameContext.getResources().getString(R.string.start_button_txt));

        } else {
            game.startGame();
            game.chatngeButtonText(gameContext.getResources().getString(R.string.stop_button_text));
        }
    }
}
