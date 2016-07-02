package com.example.islam.gameoflife.surfaceviewgame.view;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.islam.gameoflife.models.Cell;

import java.util.Collection;
import java.util.List;


/**
 * Created by islam on 7/1/16.
 */
public interface SurfaceGameView {
    void drawCells(Canvas canvas, Collection<Cell> cells, Paint paint);
    void prepareBackground();
    void drawCells(List<Cell> cells);
    void drawUnprocessedInput(Collection<Cell> preview);
    int getMatrixHeight();
    int getMatrixWidth();
    void lockCanvas();
    void unlockCanvasAndPost();
}
