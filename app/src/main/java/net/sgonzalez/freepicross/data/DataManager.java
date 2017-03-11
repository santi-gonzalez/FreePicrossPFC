package net.sgonzalez.freepicross.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class DataManager {
  //**************************************************//
  //**********     CONSTANTS     *********************//

  private static final String fileName = "FPScores";
  private static final String errorFieldPrefix = "errors";
  private static final String timeFieldPrefix = "time";
  //**************************************************//
  //**********     GLOBAL VARIABLES     **************//

  private static DataManager instance = null;

  private Context context;

  private SharedPreferences scores;
  private SharedPreferences.Editor editor;
  //**************************************************//
  //**********     CONSTRUCTORS     ******************//

  protected DataManager() {
  }
  //**************************************************//
  //**********     PUBLIC METHODS     ****************//

  public static synchronized DataManager getInstance() {
    if (instance == null) {
      instance = new DataManager();
    }
    return instance;
  }

  public void init(Context context) {
    this.context = context;
  }

  public void setUp() {
    scores = context.getSharedPreferences(fileName, 0);
    editor = scores.edit();
  }

  public int readErrors(int level) {
    return scores.getInt(errorFieldPrefix + String.valueOf(level - 1), -1);
  }

  public void writeErrors(int level, int errors) {
    editor.putInt(errorFieldPrefix + String.valueOf(level - 1), errors);
    editor.commit();
  }

  public long readTime(int level) {
    return scores.getLong(timeFieldPrefix + String.valueOf(level - 1), -1);
  }

  public void writeTime(int level, long time) {
    editor.putLong(timeFieldPrefix + String.valueOf(level - 1), time);
    editor.commit();
  }

  public void writeHightScores(int level, int errors, long time) {
    //Get current high scores of the level cleared
    int currErrors = readErrors(level);
    long currTime = readTime(level);
    //Overwrite errors only if they are better
    if (currErrors > errors || currErrors == -1) {
      writeErrors(level, errors);
    }
    //Overwrite time only if this is better
    if (currTime > time || currTime == -1) {
      writeTime(level, time);
    }
  }
}
