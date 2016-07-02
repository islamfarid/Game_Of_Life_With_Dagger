package com.example.islam.gameoflife.gamemain.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;


import com.example.islam.gameoflife.R;
import com.example.islam.gameoflife.application.GameContext;
import com.example.islam.gameoflife.dagger.gamemain.DaggerGameComponent;
import com.example.islam.gameoflife.dagger.gamemain.GameModule;
import com.example.islam.gameoflife.gamemain.presenter.GamePresenter;
import com.example.islam.gameoflife.surfaceviewgame.view.SurfaceGameViewImpl;
import com.example.islam.utils.Utils;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * This is the starting point where you should begin to analyze the code. Before
 * <p>
 * In Androd, {@link Activity} represents a single application screen.
 * This Activity will be used as single screen which will be used for whole
 * play time.
 * <p>
 * To find out more about {@link Activity} lifecycle, you should read this:
 * http://developer.android.com/reference/android/app/Activity.html#ActivityLifecycle
 *
 * @author Tomas Varaneckas
 */
public class GameActivity extends Activity implements Game {

    /**
     * We will need {@link GameContext} in our main {@link Activity} so that
     * we can control our game state.
     */
    private GameContext gameContext;
    @Bind(R.id.game_view)
    SurfaceGameViewImpl mGameView;
    @Bind(R.id.start_stop_button)
    Button mStartStopButton;
    @Inject
    GamePresenter gamePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        // Some debugging here and in other places will give you a brief view
        // of event sequence. It's helpful while you are not familiar with
        // Android application lifecycle.
        Utils.debug(this, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // Get the instance of GameContext when main activity gets created.
        gameContext = (GameContext) getApplication();
        // View will require our GameContext, as it will start the main loop
        // when the surface is ready to draw on.
        mGameView.setGameContext(gameContext);
        DaggerGameComponent.builder().applicationComponent(gameContext.getApplicationComponent()).gameModule(new GameModule(this)).build().inject(this);
        // Finally, let our Activity know that we will want to see our SurfaceGameView
    }
    
    /* The methods below will handle the game state. */

    @Override
    protected void onStop() {
        Utils.debug(this, "onStop()");
        super.onStop();

        gamePresenter.handleStartAndStopTheGame(getResources().getString(R.string.stop_button_text));
    }

    @Override
    protected void onPause() {
        Utils.debug(this, "onPause()");
        super.onPause();
        gameContext.setState(GameContext.State.PAUSED);
        gamePresenter.handleStartAndStopTheGame(getResources().getString(R.string.stop_button_text));

    }

    @Override
    protected void onResume() {
        Utils.debug(this, "onResume()");
        super.onResume();
        gameContext.setState(GameContext.State.RUNNING);
    }

    @OnClick(R.id.start_stop_button)
    public void setmStartStopButtonClick() {
        gamePresenter.handleStartAndStopTheGame(mStartStopButton.getText().toString());

    }

    @Override
    public void stopGame() {
        mGameView.stopGame();
    }

    @Override
    public void startGame() {
        mGameView.startGame();
    }

    @Override
    public void chatngeButtonText(String text) {
        mStartStopButton.setText(text);
    }
}