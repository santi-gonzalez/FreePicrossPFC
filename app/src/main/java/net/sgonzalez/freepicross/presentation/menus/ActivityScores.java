package net.sgonzalez.freepicross.presentation.menus;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.upc.fib.freepicrosspfc.R;
import net.sgonzalez.freepicross.data.DataManager;
import net.sgonzalez.freepicross.domain.navigation.MenuManager;
import net.sgonzalez.freepicross.presentation.sound.MusicManager;
import net.sgonzalez.freepicross.presentation.sound.SoundManager;
import net.sgonzalez.freepicross.utils.PicrossUtils;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class ActivityScores
extends Activity {
  //**************************************************//
  //**********     OVERRIDE ACTIVITY METHODS     *****//

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Set the content view of the screen
    setContentView(R.layout.scores);
    //Initialize buttons
    Button back = (Button) findViewById(R.id.buttonBackToMenu);
    //Set Button Listeners
    back.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        SoundManager.getInstance().playSound(SoundManager.SFX_CANCEL);
        finish();
      }
    });
    //Fill the scores
    fillScores();
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
  public boolean onPrepareOptionsMenu(Menu menu) {
    MenuManager.getInstance().prepareMenu(menu, false, false);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item == MenuManager.getInstance().getMenuItem(MenuManager.ITEM_MUSIC)) {
      if (MusicManager.getInstance().getNoMusic()) {
        MusicManager.getInstance().playMusic();
      } else {
        MusicManager.getInstance().stopMusic();
      }
    } else if (item == MenuManager.getInstance().getMenuItem(MenuManager.ITEM_SOUND)) {
      if (SoundManager.getInstance().getNoSound()) {
        SoundManager.getInstance().setNoSound(false);
      } else {
        SoundManager.getInstance().setNoSound(true);
      }
    } else if (item == MenuManager.getInstance().getMenuItem(MenuManager.ITEM_BACK)) {
      finish();
    }
    return true;
  }
  //**************************************************//
  //**********     DISABLE BACK HARDKEY     **********//

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      //Intercept the back key and do nothing.
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
  //**************************************************//
  //**********     PRIVATE METHODS     ***************//

  private void fillScores() {
    int i;
    int errors[] = new int[15];
    long time[] = new long[15];
    String[] strErrors = new String[15];
    String[] strTime = new String[15];
    for (i = 0; i < 15; i++) {
      errors[i] = DataManager.getInstance().readErrors(i + 1);
      time[i] = DataManager.getInstance().readTime(i + 1);
      strErrors[i] = String.valueOf(errors[i]);
      strTime[i] = PicrossUtils.secondsToFormattedString(time[i]);
    }
    TextView tvErrors, tvTime;
    for (i = 0; i < 15; i++) {
      switch (i) {
        case 0:
          tvErrors = (TextView) findViewById(R.id.TextLevel01Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel01Time);
          break;
        case 1:
          tvErrors = (TextView) findViewById(R.id.TextLevel02Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel02Time);
          break;
        case 2:
          tvErrors = (TextView) findViewById(R.id.TextLevel03Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel03Time);
          break;
        case 3:
          tvErrors = (TextView) findViewById(R.id.TextLevel04Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel04Time);
          break;
        case 4:
          tvErrors = (TextView) findViewById(R.id.TextLevel05Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel05Time);
          break;
        case 5:
          tvErrors = (TextView) findViewById(R.id.TextLevel06Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel06Time);
          break;
        case 6:
          tvErrors = (TextView) findViewById(R.id.TextLevel07Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel07Time);
          break;
        case 7:
          tvErrors = (TextView) findViewById(R.id.TextLevel08Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel08Time);
          break;
        case 8:
          tvErrors = (TextView) findViewById(R.id.TextLevel09Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel09Time);
          break;
        case 9:
          tvErrors = (TextView) findViewById(R.id.TextLevel10Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel10Time);
          break;
        case 10:
          tvErrors = (TextView) findViewById(R.id.TextLevel11Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel11Time);
          break;
        case 11:
          tvErrors = (TextView) findViewById(R.id.TextLevel12Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel12Time);
          break;
        case 12:
          tvErrors = (TextView) findViewById(R.id.TextLevel13Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel13Time);
          break;
        case 13:
          tvErrors = (TextView) findViewById(R.id.TextLevel14Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel14Time);
          break;
        case 14:
          tvErrors = (TextView) findViewById(R.id.TextLevel15Errors);
          tvTime = (TextView) findViewById(R.id.TextLevel15Time);
          break;
        default:
          tvErrors = null;
          tvTime = null;
      }
      if (errors[i] != -1) {
        tvErrors.setText("Errors: " + strErrors[i]);
      }
      if (time[i] != -1) {
        tvTime.setText("Temps: " + strTime[i]);
      }
    }
  }
}
