package com.example.islam.gameoflife.application;


import android.app.Application;

import com.example.islam.gameoflife.dagger.application.ApplicationComponent;
import com.example.islam.gameoflife.dagger.application.ApplicationModule;
import com.example.islam.gameoflife.dagger.application.DaggerApplicationComponent;
import com.example.islam.gameoflife.surfaceviewgame.bussiness.Logic;
import com.example.islam.utils.Utils;


/**
 * This is our game context. It links all game aspects together, different 
 * components that have this context can fetch the required information 
 * without having to talk to other components directly.
 * 
 * You can bring new features to the game and add them to this context, i.e.
 * if you wanted to have sound effects, add Sound class, register it here 
 * and call required methods when certain events happen.
 *  
 * @author Tomas Varaneckas
 */
public class GameContext extends Application {

	private State state;
	
	/**
	 * The main game loop. See it's documentation for more details.
	 */
//	private  MainLoop gameLoop;
	
//	/**
//	 * Video is responsible for drawing the game on device screen. Without it
//	 * everything would happen in pure darkness.
//	 */
//	private  Video video;
	
	/**
	 * Logic does all the calculations and controls where the cells are.
	 */
	private Logic logic;
	
	/**
	 * Handles screen touches.
	 */
	ApplicationComponent applicationComponent;
	@Override
	public void onCreate() {
		super.onCreate();

		 applicationComponent = DaggerApplicationComponent
				.builder()
				.applicationModule(new ApplicationModule(this))
				.build();
		state = State.PAUSED;
//		gameLoop = new MainLoop(this);
		logic = new Logic(this);
	}


	/**
	 * Changes the game state. Becomes effective in next main loop cycle.
	 * @param state New game {@link State}.
	 */
	public void setState(State state) {
		Utils.debug(this, "Setting game state to %s", state);
		this.state = state;
	}
	
	/* Getters for various game aspects */
	
	public State getState() {
		return state;
	}
	
//	public Video getVideo() {
//		return video;
//	}
	
//	public MainLoop getGameLoop() {
//		return gameLoop;
//	}

	public Logic getLogic() {
		return logic;
	}
	

	public ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
	public enum State {

		RUNNING, PAUSED

	}
}
