package edu.upc.fib.freepicrosspfc.picrossUtils;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class PicrossUtils {
	
	//**************************************************//
    //**********     CONSTANTS     *********************//
	
	public static final String PACKAGE_TAG = "edu.upc.fib.freepicrosspfc.";
	public static final String FIELD_LEVEL = "level";
	public static final String FIELD_LEVEL_ID = "levelID";
	
	//**************************************************//
    //**********     PUBLIC METHODS     ****************//

	public static String secondsToFormattedString(long seconds) {
		
		String mins, secs;
		
		mins = String.valueOf(seconds/60);
		secs = String.valueOf(seconds%60);
		if(secs.length() == 1) secs = "0" + secs;
		
		return (mins + ":" + secs);
	}
}
