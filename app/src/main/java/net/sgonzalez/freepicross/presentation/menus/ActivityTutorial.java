package net.sgonzalez.freepicross.presentation.menus;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import net.sgonzalez.freepicross.R;
import net.sgonzalez.freepicross.domain.navigation.MenuManager;
import net.sgonzalez.freepicross.presentation.sound.MusicManager;
import net.sgonzalez.freepicross.presentation.sound.SoundManager;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class ActivityTutorial
extends Activity {
  //**************************************************//
  //**********     CONSTANTS     *********************//

  public static final int BTN_NEXT = 1;
  public static final int BTN_PREVIOUS = 2;

  private static final int MAX_PAGES = 3;
  //**************************************************//
  //**********     GLOBAL VARIABLES     **************//

  private int page;
  private TextView text;
  private ImageView image;

  private Button next;
  private Button previous;
  private Button back;

  private boolean buttonPressed;
  //**************************************************//
  //**********     OVERRIDE ACTIVITY METHODS     *****//

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //Set the content view of the screen
    setContentView(R.layout.tutorial);
    this.page = 1;
    this.text = (TextView) findViewById(R.id.TutoText);
    this.image = (ImageView) findViewById(R.id.TutoImage);
    //Initialize buttons
    next = (Button) findViewById(R.id.buttonNextLesson);
    previous = (Button) findViewById(R.id.buttonPreviousLesson);
    back = (Button) findViewById(R.id.buttonBackToMenu);
    buttonPressed = false;
    previous.setEnabled(false);
    printLesson();
    //Set Button Listeners
    next.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        pressButton(BTN_NEXT);
      }
    });
    previous.setOnClickListener(new View.OnClickListener() {

      public void onClick(View view) {
        pressButton(BTN_PREVIOUS);
      }
    });
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

  private synchronized void pressButton(int button) {
    if (!buttonPressed) {
      buttonPressed = true;
      SoundManager.getInstance().playSound(SoundManager.SFX_CONFIRM);
      switch (button) {
        case BTN_NEXT:
          nextPage();
          break;
        case BTN_PREVIOUS:
          previousPage();
          break;
      }
      buttonPressed = false;
    }
  }

  private void nextPage() {
    this.page++;
    this.previous.setEnabled(true);
    if (this.page == MAX_PAGES) {
      this.next.setEnabled(false);
    }
    this.printLesson();
  }

  private void previousPage() {
    this.page--;
    this.next.setEnabled(true);
    if (this.page == 1) {
      this.previous.setEnabled(false);
    }
    this.printLesson();
  }

  private void printLesson() {
    switch (this.page) {
      case 1:
        this.text.setText(R.string.textLesson01);
        this.image.setImageResource(R.drawable.tex_1);
        break;
      case 2:
        this.text.setText(R.string.textLesson02);
        this.image.setImageResource(R.drawable.tex_2);
        break;
      case 3:
        this.text.setText(R.string.textLesson03);
        this.image.setImageResource(R.drawable.tex_3);
        break;
    }
  }
}
