package com.example.islam.gameoflife.surfaceviewgame.presenter;


import com.example.islam.gameoflife.application.GameContext;
import com.example.islam.gameoflife.dagger.customsurfaceview.DaggerGameSurfaceViewPresenterComponent;
import com.example.islam.gameoflife.dagger.customsurfaceview.GameSurfaceViewPresenterModule;
import com.example.islam.gameoflife.models.Cell;
import com.example.islam.gameoflife.surfaceviewgame.bussiness.Logic;
import com.example.islam.gameoflife.surfaceviewgame.view.SurfaceGameView;

import java.util.Collection;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by islam on 7/1/16.
 */
public class GameViewPresenter {
    /**
     * This will let us know if game is running already so we don't restart it
     * on canvas rotation.
     */
    private boolean gameRunning;
    GameContext gameContext;
    @Inject
    Logic logic;
    SurfaceGameView gameView;
    Subscription intervalSubscription;

    @Inject
    public GameViewPresenter(SurfaceGameView gameView, GameContext gameContext) {
        this.gameView = gameView;
        this.gameContext = gameContext;
        DaggerGameSurfaceViewPresenterComponent.builder().applicationComponent(gameContext.getApplicationComponent()).
                gameSurfaceViewPresenterModule(new GameSurfaceViewPresenterModule(gameView)).build().inject(this);
    }


    public void startGame() {
        intervalSubscription = logic.tick().subscribeOn(AndroidSchedulers.mainThread()).subscribe(preview -> {
            update(preview);
        });
    }

    public void addTouch(float x, float y, float pressure) {
        logic.addTouch(x, y, pressure);
    }

    public void flush() {
        logic.flush();
    }

    /**
     * start the game when obtaining the size of the surfaceview
     *
     * @param width
     * @param height
     */
    public void sizeChanged(int width, int height) {
        logic.setGamMatrixHeight(width);
        logic.setGamMatrixWidth(height);
        if (!gameRunning) {
            gameRunning = true;
        }
    }

    public void stopGame() {
        if (intervalSubscription != null) {
            intervalSubscription.unsubscribe();
        }
    }

    private void update(Collection<Cell> preview) {
        gameView.lockCanvas();
        gameView.prepareBackground();
        gameView.drawCells(logic.getCells());
        gameView.drawUnprocessedInput(preview);
        gameView.unlockCanvasAndPost();

    }
}
