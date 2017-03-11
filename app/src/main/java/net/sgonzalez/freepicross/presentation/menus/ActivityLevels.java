package net.sgonzalez.freepicross.presentation.menus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import edu.upc.fib.freepicrosspfc.R;
import net.sgonzalez.freepicross.presentation.game.ActivityGame;
import net.sgonzalez.freepicross.domain.gameplay.GameManager;
import net.sgonzalez.freepicross.domain.navigation.MenuManager;
import net.sgonzalez.freepicross.presentation.sound.MusicManager;
import net.sgonzalez.freepicross.presentation.sound.SoundManager;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class ActivityLevels extends Activity {
		
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private Button loadLevel[] = new Button[15];
	private Button back;
	private boolean buttonPressed;

	//**************************************************//
    //**********     OVERRIDE ACTIVITY METHODS     *****//
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //Set the content view of the screen
        setContentView(R.layout.levels);

        //Initialize buttons
        loadLevel[ 0] = (Button) findViewById(R.id.ButtonLoadLevel01);
        loadLevel[ 1] = (Button) findViewById(R.id.ButtonLoadLevel02);
        loadLevel[ 2] = (Button) findViewById(R.id.ButtonLoadLevel03);
        loadLevel[ 3] = (Button) findViewById(R.id.ButtonLoadLevel04);
        loadLevel[ 4] = (Button) findViewById(R.id.ButtonLoadLevel05);
        loadLevel[ 5] = (Button) findViewById(R.id.ButtonLoadLevel06);
        loadLevel[ 6] = (Button) findViewById(R.id.ButtonLoadLevel07);
        loadLevel[ 7] = (Button) findViewById(R.id.ButtonLoadLevel08);
        loadLevel[ 8] = (Button) findViewById(R.id.ButtonLoadLevel09);
        loadLevel[ 9] = (Button) findViewById(R.id.ButtonLoadLevel10);
        loadLevel[10] = (Button) findViewById(R.id.ButtonLoadLevel11);
        loadLevel[11] = (Button) findViewById(R.id.ButtonLoadLevel12);
        loadLevel[12] = (Button) findViewById(R.id.ButtonLoadLevel13);
        loadLevel[13] = (Button) findViewById(R.id.ButtonLoadLevel14);
        loadLevel[14] = (Button) findViewById(R.id.ButtonLoadLevel15);
        back          = (Button) findViewById(R.id.buttonBackToMenu);
        
        buttonPressed = false;
        
        //Set Button Listeners
        loadLevel[0].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(0);}});
        
        loadLevel[1].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(1);}});
        
        loadLevel[2].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(2);}});
        
        loadLevel[3].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(3);}});
        
        loadLevel[4].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(4);}});
        
        loadLevel[5].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(5);}});
        
        loadLevel[6].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(6);}});
        
        loadLevel[7].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(7);}});
        
        loadLevel[8].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(8);}});
        
        loadLevel[9].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(9);}});
        
        loadLevel[10].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(10);}});
        
        loadLevel[11].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(11);}});
        
        loadLevel[12].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(12);}});
        
        loadLevel[13].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(13);}});
        
        loadLevel[14].setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {launchGame(14);}});
        
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	
            	SoundManager.getInstance().playSound(SoundManager.SFX_CANCEL);
            	finish();
            }
        });
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
	
	private synchronized void launchGame(int level) {
		
		if (!buttonPressed){
			
			buttonPressed = true;
			
			SoundManager.getInstance().playSound(SoundManager.SFX_CONFIRM);
			
			Intent intent = new Intent(this, ActivityGame.class);
			switch(level) {
			
			case  0: GameManager.getInstance().setLevelID(R.raw.level01); break;
			case  1: GameManager.getInstance().setLevelID(R.raw.level02); break;
			case  2: GameManager.getInstance().setLevelID(R.raw.level03); break;
			case  3: GameManager.getInstance().setLevelID(R.raw.level04); break;
			case  4: GameManager.getInstance().setLevelID(R.raw.level05); break;
			case  5: GameManager.getInstance().setLevelID(R.raw.level06); break;
			case  6: GameManager.getInstance().setLevelID(R.raw.level07); break;
			case  7: GameManager.getInstance().setLevelID(R.raw.level08); break;
			case  8: GameManager.getInstance().setLevelID(R.raw.level09); break;
			case  9: GameManager.getInstance().setLevelID(R.raw.level10); break;
			case 10: GameManager.getInstance().setLevelID(R.raw.level11); break;
			case 11: GameManager.getInstance().setLevelID(R.raw.level12); break;
			case 12: GameManager.getInstance().setLevelID(R.raw.level13); break;
			case 13: GameManager.getInstance().setLevelID(R.raw.level14); break;
			case 14: GameManager.getInstance().setLevelID(R.raw.level15); break;
			} GameManager.getInstance().setLevel(level + 1);
			
	    	startActivity(intent);
	    	finish();
		}
	}
}