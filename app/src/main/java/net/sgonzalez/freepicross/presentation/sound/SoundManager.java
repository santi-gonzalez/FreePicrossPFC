package net.sgonzalez.freepicross.presentation.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import java.util.HashMap;
import net.sgonzalez.freepicross.R;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class SoundManager {
  //**************************************************//
  //**********     CONSTANTS     *********************//

  public static final int SFX_CONFIRM = 0;
  public static final int SFX_CANCEL = 1;
  public static final int SFX_MARK = 2;
  public static final int SFX_DESTROY = 3;
  public static final int SFX_ERROR = 4;
  public static final int SFX_TOOL = 5;
  public static final int SFX_ZOOM = 6;
  //**************************************************//
  //**********     GLOBAL VARIABLES     **************//

  private static SoundManager instance = null;

  private Context context;

  private boolean noSound;
  private SoundPool soundPool;
  private HashMap<Integer, Integer> soundPoolMap;
  private AudioManager audioManager;
  //**************************************************//
  //**********     CONSTRUCTORS     ******************//

  protected SoundManager() {
  }
  //**************************************************//
  //**********     PUBLIC METHODS     ****************//

  public static synchronized SoundManager getInstance() {
    if (instance == null) {
      instance = new SoundManager();
    }
    return instance;
  }

  public void init(Context context) {
    this.context = context;
  }

  public void setUp() {
    noSound = true;
    soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
    soundPoolMap = new HashMap<Integer, Integer>();
    soundPoolMap.put(SFX_CONFIRM, soundPool.load(context, R.raw.sfx_confirm, 1));
    soundPoolMap.put(SFX_CANCEL, soundPool.load(context, R.raw.sfx_cancel, 1));
    soundPoolMap.put(SFX_MARK, soundPool.load(context, R.raw.sfx_mark, 1));
    soundPoolMap.put(SFX_DESTROY, soundPool.load(context, R.raw.sfx_destroy, 1));
    soundPoolMap.put(SFX_ERROR, soundPool.load(context, R.raw.sfx_error, 1));
    soundPoolMap.put(SFX_TOOL, soundPool.load(context, R.raw.sfx_tool, 1));
    soundPoolMap.put(SFX_ZOOM, soundPool.load(context, R.raw.sfx_zoom, 1));
    audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
  }

  public void playSounds() {
    this.noSound = false;
  }

  public void stopSounds() {
    this.noSound = true;
  }

  public void playSound(int index) {
    if (!noSound) {
      float streamVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
      streamVolume = streamVolume / audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
      soundPool.play(soundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
    }
  }

  public boolean getNoSound() {
    return this.noSound;
  }

  public void setNoSound(boolean noSound) {
    this.noSound = noSound;
  }
}
