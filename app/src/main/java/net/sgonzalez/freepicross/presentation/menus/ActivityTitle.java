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
import net.sgonzalez.freepicross.R;
import net.sgonzalez.freepicross.data.DataManager;
import net.sgonzalez.freepicross.domain.gameplay.GameManager;
import net.sgonzalez.freepicross.domain.gameplay.LevelManager;
import net.sgonzalez.freepicross.domain.navigation.MenuManager;
import net.sgonzalez.freepicross.presentation.opengl.TextureManager;
import net.sgonzalez.freepicross.presentation.sound.MusicManager;
import net.sgonzalez.freepicross.presentation.sound.SoundManager;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class ActivityTitle
extends Activity {
  //**************************************************//
  //**********     CONSTANTS     *********************//

  public static final int BTN_LAUNCH = 1;
  //**************************************************//
  //**********     GLOBAL VARIABLES     **************//

  private boolean buttonPressed;
  //**************************************************//
  //**********     OVERRIDE ACTIVITY METHODS     *****//

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Set the content view of the screen
    setContentView(R.layout.title);
    //Init singleton classes
    MenuManager.getInstance().init(this);
    MusicManager.getInstance().init(this);
    SoundManager.getInstance().init(this);
    DataManager.getInstance().init(this);
    GameManager.getInstance().init(this);
    LevelManager.getInstance().init(this);
    TextureManager.getInstance().init(this);
    //Set up singleton classes
    MusicManager.getInstance().setUp();
    SoundManager.getInstance().setUp();
    DataManager.getInstance().setUp();
    MusicManager.getInstance().playMusic();
    //MusicManager.getInstance().stopMusic();
    SoundManager.getInstance().playSounds();
    //SoundManager.getInstance().stopSounds();
    //Initialize buttons
    Button launch = (Button) findViewById(R.id.start_game);
    Button exit = (Button) findViewById(R.id.buttonExitGame);
    buttonPressed = false;
    //Set Button Listeners
    launch.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        pressButton(BTN_LAUNCH);
      }
    });
    exit.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        SoundManager.getInstance().playSound(SoundManager.SFX_CANCEL);
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTitle.this);
        builder.setMessage(R.string.confirmExit)
               .setCancelable(false)
               .setPositiveButton(R.string.choiceYes, new DialogInterface.OnClickListener() {

                 public void onClick(DialogInterface dialog, int id) {
                   finish();
                 }
               })
               .setNegativeButton(R.string.choiceNo, new DialogInterface.OnClickListener() {

                 public void onClick(DialogInterface dialog, int id) {
                 }
               });
        AlertDialog alert = builder.create();
        alert.show();
      }
    });
  }

  @Override
  public void onRestart() {
    super.onRestart();
    buttonPressed = false;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    MusicManager.getInstance().destroyMediaPlayer();
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
    MenuManager.getInstance().prepareMenu(menu, true, false);
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
      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(R.string.confirmExit)
             .setCancelable(false)
             .setPositiveButton(R.string.choiceYes, new DialogInterface.OnClickListener() {

               public void onClick(DialogInterface dialog, int id) {
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
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
      //Intercept the back key and do nothing.
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }
  //**************************************************//
  //**********     PRIVATE METHODS     ***************//

  private synchronized void pressButton(int button) {
    if (!buttonPressed) {
      buttonPressed = true;
      SoundManager.getInstance().playSound(SoundManager.SFX_CONFIRM);
      switch (button) {
        case BTN_LAUNCH:
          startActivity(new Intent(this, ActivityOptions.class));
      }
    }
  }
}
