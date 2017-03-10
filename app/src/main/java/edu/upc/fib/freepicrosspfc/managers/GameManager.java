package edu.upc.fib.freepicrosspfc.managers;

import android.content.Context;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class GameManager {
	
	//**************************************************//
    //**********     CONSTANTS     *********************//

	public static final int STATE_VOID = 0;
	public static final int STATE_CLEARED = 1;
	public static final int STATE_SURRENDER = 2;
	public static final int STATE_NOTIME = 3;
	public static final int STATE_NOLIFES = 4;
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private static GameManager instance = null;
	
	//private Context context;
	
	private int level;
	private int levelID;
	
	private int errorsMax;
	private int errorsCur;
	
	private long timeMax;
	private long timeCur;
	
	private boolean cleared;
	private int gameState;
	
	//**************************************************//
    //**********     CONSTRUCORS     *******************//
	
	protected GameManager() {
		
	}
	
	//**************************************************//
    //**********     PUBLIC METHODS     **************//
	
	public static synchronized GameManager getInstance() {
		
		if(instance == null) instance = new GameManager();
		return instance;
	}
	
	public void init(Context context) {
		
		//this.context = context;
		this.gameState = STATE_VOID;
	}
	
	public void setUp() {
		
		this.errorsMax = 3;
		if(level > 6) this.errorsMax++;
		if(level > 12) this.errorsMax++;
		this.errorsCur = 0;
		this.timeMax = 120 * this.level;
		this.cleared = false;
		this.gameState = STATE_VOID;
	}

	public void setErrorsMax(int errorsMax) {
		this.errorsMax = errorsMax;
	}

	public int getErrorsMax() {
		return errorsMax;
	}

	public void setErrorsCur(int errorsCur) {
		this.errorsCur = errorsCur;
	}

	public int getErrorsCur() {
		return errorsCur;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getLevel() {
		return level;
	}

	public void setTimeMax(long timeMax) {
		this.timeMax = timeMax;
	}

	public long getTimeMax() {
		return timeMax;
	}

	public void setTimeCur(long timeCur) {
		this.timeCur = timeCur;
	}

	public long getTimeCur() {
		return timeCur;
	}

	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}

	public int getLevelID() {
		return levelID;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	public int getGameState() {
		return gameState;
	}
}
