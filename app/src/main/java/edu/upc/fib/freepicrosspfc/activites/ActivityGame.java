package edu.upc.fib.freepicrosspfc.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import edu.upc.fib.freepicrosspfc.R;
import edu.upc.fib.freepicrosspfc.gl.GLView;
import edu.upc.fib.freepicrosspfc.managers.GameManager;
import edu.upc.fib.freepicrosspfc.managers.MenuManager;
import edu.upc.fib.freepicrosspfc.managers.MusicManager;
import edu.upc.fib.freepicrosspfc.managers.SoundManager;
import edu.upc.fib.freepicrosspfc.picrossUtils.PicrossUtils;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class ActivityGame extends Activity {

	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private GLView         mGLView;
	private CountDownTimer mTimer;
	private TextView       mTimerView;
	private TextView       mErrorView;

	//**************************************************//
    //**********     OVERRIDE ACTIVITY METHODS     *****//
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Show progress dialog while level is loading
		ProgressDialog dialog = ProgressDialog.show(this, "", "Carregant el nivell, espera si us play...", true);
		
		//Always keep screen turned on 
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		//Prepare the level
		GameManager.getInstance().setUp();
		
		//Init Timer and Lifes TextViews
		initTextViews();
		initTimer();
		
		//Init the GLSurfaceView
		mGLView = new GLView(this);
		setContentView(mGLView);
		
		//Add Timer and Lifes TextViews to the current GLSurfaceView
		addTextViews();
		
		//Dismiss the progress dialog
		dialog.dismiss();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		
		mTimer.cancel();
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
		
		MenuManager.getInstance().prepareMenu(menu, false, true);
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
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
    		builder.setMessage(R.string.confirmSurrender)
    		       .setCancelable(false)
    		       .setPositiveButton(R.string.choiceYes, new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		        	   GameManager.getInstance().setGameState(GameManager.STATE_SURRENDER);
    		        	   finish();
    		           }
    		       })
    		       .setNegativeButton(R.string.choiceNo, new DialogInterface.OnClickListener() {
    		           public void onClick(DialogInterface dialog, int id) {
    		                //DO NOTHING, just close the dialog
    		           }
    		       });
    		AlertDialog alert = builder.create();
    		alert.show();
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
	
	private void initTimer() {
		
		//Prepare the CountDown timer
		mTimer = new CountDownTimer(GameManager.getInstance().getTimeMax() * 1000, 1000) {
			
			
			public void onTick(long millisUntilFinished) {
		    	 
				if(!GameManager.getInstance().isCleared()) {
		    		
					GameManager.getInstance().setTimeCur(GameManager.getInstance().getTimeMax() - millisUntilFinished / 1000);
			    	mTimerView.setText("Temps " + PicrossUtils.secondsToFormattedString(millisUntilFinished / 1000));
			    	mErrorView.setText("Errors " + GameManager.getInstance().getErrorsCur() + "/" + GameManager.getInstance().getErrorsMax());
		    	}
			}

		    public void onFinish() {
		    	 
		    	if(!GameManager.getInstance().isCleared()) {
		    	
			    	GameManager.getInstance().setGameState(GameManager.STATE_NOTIME);
			    	finish();
		    	}
		    }
		};
		mTimer.start();
	}
	
	private void initTextViews() {
		
		//Init timer TextView
		mTimerView = new TextView(this);
		mTimerView.setPadding(20, 8, 0, 0);
		mTimerView.setGravity(Gravity.LEFT);
		mTimerView.setTextSize(25);
		mTimerView.setTextColor(Color.BLACK);
		
		//Init errors TextView
		mErrorView = new TextView(this);
		mErrorView.setPadding(0, 8, 15, 0);
		mErrorView.setGravity(Gravity.RIGHT);
		mErrorView.setTextSize(25);
		mErrorView.setTextColor(Color.BLACK);
		
	}
	
	private void addTextViews() {
		
		//Add error and timer TextView to the GLSurfaceView layout
		addContentView(mTimerView, new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		addContentView(mErrorView, new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
	}
}