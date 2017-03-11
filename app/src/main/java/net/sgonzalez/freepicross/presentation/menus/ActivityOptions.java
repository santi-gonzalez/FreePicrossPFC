package net.sgonzalez.freepicross.presentation.menus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import edu.upc.fib.freepicrosspfc.R;
import net.sgonzalez.freepicross.data.DataManager;
import net.sgonzalez.freepicross.domain.gameplay.GameManager;
import net.sgonzalez.freepicross.domain.navigation.MenuManager;
import net.sgonzalez.freepicross.presentation.sound.MusicManager;
import net.sgonzalez.freepicross.presentation.sound.SoundManager;
import net.sgonzalez.freepicross.utils.PicrossUtils;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class ActivityOptions extends Activity{
	
	//**************************************************//
    //**********     CONSTANTS     *********************//
	
	public static final int BTN_LEVELS   = 1;
	public static final int BTN_SCORES   = 2;
	public static final int BTN_TUTORIAL = 3;
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private boolean buttonPressed;
	
	//**************************************************//
    //**********     OVERRIDE ACTIVITY METHODS     *****//
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set the content view of the screen
        setContentView(R.layout.options);

        //Initialize buttons
        Button levels   = (Button) findViewById(R.id.buttonLevels);
        Button scores   = (Button) findViewById(R.id.buttonScores);
        Button tutorial = (Button) findViewById(R.id.buttonTutorial);
        Button back     = (Button) findViewById(R.id.buttonBackToTitle);
        
        buttonPressed = false;
        
        //Set Button Listeners
        levels.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {pressButton(BTN_LEVELS);}});
        
        scores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {pressButton(BTN_SCORES);}});
        
        tutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {pressButton(BTN_TUTORIAL);}});
        
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	SoundManager.getInstance().playSound(SoundManager.SFX_CANCEL);
            	finish();
            }
        });
    }
	
	@Override
	public void onRestart() {
		super.onRestart();
		
		//If a game has finished, write scores and show result
		if(GameManager.getInstance().getGameState() != GameManager.STATE_VOID) {

			//Write scores if necessary
			writeScores();
			//Show game result
			showScores();
			
			//Set the game state to void
			GameManager.getInstance().setGameState(GameManager.STATE_VOID);
		}
		
		buttonPressed = false;
	}
	
	//**************************************************//
	//**********     MENU HARDKEY METHODS     **********//
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
	    MenuInflater inflater = getMenuInflater();
	    MenuManager.getInstance().setUp(menu, inflater);
	    return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) 
	{
		
		MenuManager.getInstance().prepareMenu(menu, false, false);
		return true;
	}
   
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	
		if (item == MenuManager.getInstance().getMenuItem(MenuManager.ITEM_MUSIC)) {
			
			if(MusicManager.getInstance().getNoMusic()) MusicManager.getInstance().playMusic();
			else										MusicManager.getInstance().stopMusic();
		}
		else if (item == MenuManager.getInstance().getMenuItem(MenuManager.ITEM_SOUND)) {
			
			if(SoundManager.getInstance().getNoSound()) SoundManager.getInstance().setNoSound(false);
			else										SoundManager.getInstance().setNoSound(true);
		}
		else if (item == MenuManager.getInstance().getMenuItem(MenuManager.ITEM_BACK)) {
			
			finish();
		}
		return true;
	}
	
	//**************************************************//
	//**********     DISABLE BACK HARDKEY     **********//
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	        //Intercept the back key and do nothing.
	        return true;
	    }

	    return super.onKeyDown(keyCode, event);
	}
	
	//**************************************************//
	//**********     PRIVATE METHODS     ***************//
	
	private synchronized void pressButton(int button) {
		
		if(!buttonPressed) {
			
			buttonPressed = true;
			
			SoundManager.getInstance().playSound(SoundManager.SFX_CONFIRM);
			
			switch(button) {
			
			case BTN_LEVELS:   startActivity(new Intent(this, ActivityLevels.class));   break;
			case BTN_SCORES:   startActivity(new Intent(this, ActivityScores.class));   break;
			case BTN_TUTORIAL: startActivity(new Intent(this, ActivityTutorial.class)); break;
			}
		}
	}
	
	private void writeScores() {
		
		//Only write scores if game has been cleared
		if(GameManager.getInstance().isCleared()) 
			DataManager.getInstance().writeHightScores(GameManager.getInstance().getLevel(),
													   GameManager.getInstance().getErrorsCur(),
													   GameManager.getInstance().getTimeCur());
	}
	
	private void showScores() {
		
		String title;
		switch(GameManager.getInstance().getGameState()) {
		
		case GameManager.STATE_CLEARED:
			title = "Nivell " + (GameManager.getInstance().getLevel()) + " superat!\n";
			title = title.concat("Errors: " + GameManager.getInstance().getErrorsCur() + "\n");
			title = title.concat("Temps: " + PicrossUtils.secondsToFormattedString(GameManager.getInstance().getTimeCur()));
			break;
		case GameManager.STATE_NOLIFES:
			title = "Nivell " + (GameManager.getInstance().getLevel()) + " fracassat.\nHas comès masses errors.";
			break;
		case GameManager.STATE_NOTIME:
			title = "Nivell " + (GameManager.getInstance().getLevel()) + " fracassat.\nHas superat el temps límit.";
			break;
		case GameManager.STATE_SURRENDER:
			title = "Has abandonat el nivell " + (GameManager.getInstance().getLevel());
			break;
		default:
			title = "Has abandonat el nivell " + (GameManager.getInstance().getLevel());
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(title)
		       .setCancelable(false)
		       .setPositiveButton(R.string.choiceOk, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
}
