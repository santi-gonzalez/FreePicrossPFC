package net.sgonzalez.freepicross.presentation.sound;

import android.content.Context;
import android.media.MediaPlayer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import edu.upc.fib.freepicrosspfc.R;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class MusicManager {

	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private static MusicManager instance = null;
	
	private Context context;
	
	private boolean noMusic;
	private boolean wasMuted;
	private MediaPlayer mediaPlayer;
	private PhoneStateListener mPhoneStateListener;
	
	//**************************************************//
    //**********     CONSTRUCTORS     ******************//
	
	protected MusicManager() {
		
	}
	
	//**************************************************//
    //**********     PUBLIC METHODS     ****************//
	
	public static synchronized MusicManager getInstance() {
		
		if(instance == null) instance = new MusicManager();
		return instance;
	}
	
	public void init(Context context) {
		
		this.context = context;
	}
	
	public void setUp() {
		
		this.noMusic = true;
		this.wasMuted = true;
		
		mediaPlayer = MediaPlayer.create(context, R.raw.music_background1);
		
		mPhoneStateListener = new PhoneStateListener() {
		    @Override
		    public void onCallStateChanged(int state, String incomingNumber) {
		        if (state == TelephonyManager.CALL_STATE_RINGING) {
		            //Incoming call
		        	wasMuted = noMusic;
		        	if(!wasMuted) stopMusic();
		        } else if(state == TelephonyManager.CALL_STATE_IDLE) {
		            //Not in call: Play music
		        	if(!wasMuted) playMusic();
		        }
		        super.onCallStateChanged(state, incomingNumber);
		    }
		};
		
		TelephonyManager mgr = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
		if(mgr != null) {
		    mgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}
	
	public void destroyMediaPlayer() {
		
		mediaPlayer.stop();
    	mediaPlayer.release();
    	
    	TelephonyManager mgr = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
    	if(mgr != null) {
    	    mgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
    	}

	}
	
	public void playMusic() {
		
		this.noMusic = false;
		mediaPlayer.start();
        mediaPlayer.setVolume(0.1f, 0.1f);
        mediaPlayer.setLooping(true);
	}
	
	public void stopMusic() {
		
		this.noMusic = true;
		mediaPlayer.pause();
	}
	
	public void setNoMusic(boolean noMusic) {
		
		this.noMusic = noMusic;
	}
	
	public boolean getNoMusic() {
		
		return this.noMusic;
	}
}
