package com.example.islam.gameoflife.surfaceviewgame.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;


import com.example.islam.gameoflife.application.GameContext;
import com.example.islam.gameoflife.common.UIConstants;
import com.example.islam.gameoflife.dagger.customsurfaceview.DaggerGameSurfaceViewPresenterComponent;
import com.example.islam.gameoflife.dagger.customsurfaceview.GameSurfaceViewPresenterModule;
import com.example.islam.gameoflife.models.Cell;
import com.example.islam.gameoflife.surfaceviewgame.presenter.GameViewPresenter;
import com.example.islam.utils.Utils;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

/**
 * The {@link View} which will hold our game visuals. {@link SurfaceView} is the
 * best implementation for game development.
 * <p>
 * Make sure you read the docs:
 * http://developer.android.com/reference/android/view/SurfaceView.html
 *
 * @author Tomas Varaneckas
 */
public class SurfaceGameViewImpl extends SurfaceView implements SurfaceHolder.Callback, SurfaceGameView {

    /**
     * @see #setGameContext(GameContext)
     */
    private GameContext gameContext;



    /**
     * Scale that tells how many screen pixels will represent one game pixel.
     */
    /**
     * {@link SurfaceHolder} that manages our {@link SurfaceGameViewImpl}. This will be
     * used for getting the {@link Canvas} to draw on.
     */

    private SurfaceHolder surfaceHolder;
    /**
     * Defines background color.
     */
    private Paint bgPaint;

    /**
     * Defines active cell color.
     */
    private Paint cellPaint ;

    /**
     * Defines color of cells that were drawn with finger but not yet flushed
     * into game logic.
     */
    private Paint prePaint;

    /**
     * View dimensions in pixels.
     */
    private int width, height;
    @Inject
    GameViewPresenter gameViewPresenter;
    Canvas canvas;
    public SurfaceGameViewImpl(Context context, AttributeSet attributes) {
        super(context, attributes);

        // SurfaceView needs a SurfaceHolder.Callback work properly.
        // In this case our SurfaceGameView is a SurfaceHolder.Callback,
        // so it registers itself.
        getHolder().addCallback(this);

        // Focus will give us the possibility to get focus and catch touch
        // events.
        setFocusable(true);
        surfaceHolder = getHolder();
        bgPaint = new Paint();
        bgPaint.setColor(Color.WHITE);

        cellPaint = new Paint();
        cellPaint.setColor(Color.BLACK);

        prePaint = new Paint();
        prePaint.setColor(Color.GREEN);
        DaggerGameSurfaceViewPresenterComponent.builder().applicationComponent(((GameContext)((Activity)context).getApplication()).getApplicationComponent()).gameSurfaceViewPresenterModule(new GameSurfaceViewPresenterModule(this)).build().inject(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // While finger is down on screen, we will gather input
        if (event.getAction() != MotionEvent.ACTION_UP) {
            // Adjust event coordinates according to our scale.
            gameViewPresenter.addTouch(
                    event.getX() / UIConstants.SCALE,
                    event.getY() /  UIConstants.SCALE,
                    event.getPressure());

        } else {
            // When finger is released, input will be flushed into Logic.
            gameViewPresenter.flush();
        }

        return true;
    }

    /**
     * The view will need game context to pass itself to video renderer,
     * register new input events and start the main loop when canvas are ready.
     */
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Utils.debug(this, "Size changed");
        width = w;
        height = h;
        gameViewPresenter.sizeChanged( w,  h);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Utils.debug(this, "Surface changed");

        // Resize our video when surface changes (screen rotates)
//        video.setSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Utils.debug(this, "Surface created");
        prepareBackGroundForFirstTime();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Utils.debug(this, "Surface destroyed");
        // We don't want to do anything here, because surface gets destroyed
        // and recreated on screen rotation.
    }

//    /**
//     * Updates all the video:
//     * 1. Clears background and fills it with {@link #bgPaint}.
//     * 2. Draws {@link Cell} objects that come from {@link Logic}.
//     * 3. Draws unprocessed cells that come from {@link Input}.
//     * @see #prepareBackground(Canvas)
//     * @see #drawCells(Canvas)
//     * @see #drawUnprocessedInput(Canvas)
//     */
//    public void update() {
//        Canvas canvas = surfaceHolder.lockCanvas();
//        if (canvas != null) {
//            prepareBackground();
//            drawCells();
//            drawUnprocessedInput();
//            surfaceHolder.unlockCanvasAndPost(canvas);
//        }
//    }

    /**
     * Fills given canvas with background color.
     */
    public void prepareBackground() {
        if(canvas != null) {
            canvas.drawRect(new Rect(0, 0, width, height), bgPaint);
        }
    }

    /**
     * Draws the current generation of cells that provides.
     */
    public void drawCells(List<Cell> cells) {
        if(canvas != null) {

            drawCells(canvas, cells, cellPaint);
        }
    }

    public void drawUnprocessedInput( Collection<Cell> preview) {
        if(canvas != null) {
            drawCells(canvas, preview, prePaint);
        }
    }

    /**
     * Draws a collection of cells using given paint and canvas.
     * Cells are represented as a rectangle.
     * @param canvas Canvas to draw on.
     * @param cells Cells that should be drawn.
     * @param paint Paint that defines cell color.
     */
     public void drawCells(Canvas canvas, Collection<Cell> cells, Paint paint) {
        for (Cell cell : cells) {
            canvas.drawRect(new Rect(
                            Math.round(cell.getX() *  UIConstants.SCALE),
                            Math.round(cell.getY() *  UIConstants.SCALE),
                            Math.round(cell.getX() *  UIConstants.SCALE +  UIConstants.SCALE),
                            Math.round(cell.getY() *  UIConstants.SCALE +  UIConstants.SCALE)),
                    paint);
        }
    }

    /**
     * Changes video size. Will be called externally from our {@link SurfaceGameViewImpl}.
     */
    public void setSize(int width, int height) {
        Utils.debug(this, "Setting video size: %d x %d", width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of our game matrix using scaled game pixels.
     */
    public int getMatrixWidth() {
        return Math.round(width /  UIConstants.SCALE);
    }

    @Override
    public void lockCanvas() {
            canvas = surfaceHolder.lockCanvas();
    }

    @Override
    public void unlockCanvasAndPost() {
        surfaceHolder.unlockCanvasAndPost(canvas);
    }


    /**
     * Gets the height of our game matrix using scaled game pixels.
     */
    public int getMatrixHeight() {
        return Math.round(height /  UIConstants.SCALE);
    }

    public void startGame() {
        gameViewPresenter.startGame();
    }
    public void stopGame() {
        gameViewPresenter.stopGame();
    }
    public void prepareBackGroundForFirstTime(){
        //for first time prepare the background
        if(canvas == null){
            canvas = surfaceHolder.lockCanvas();
            prepareBackground();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
