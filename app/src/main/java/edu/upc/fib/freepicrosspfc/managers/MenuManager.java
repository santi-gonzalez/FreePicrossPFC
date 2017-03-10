package edu.upc.fib.freepicrosspfc.managers;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import edu.upc.fib.freepicrosspfc.R;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class MenuManager {

	//**************************************************//
    //**********     CONSTANTS     *********************//
	
	public static final int MAX_MENU_ITEMS = 3;
	public static final int ITEM_MUSIC = 0;
	public static final int ITEM_SOUND = 1;
	public static final int ITEM_BACK  = 2;
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private static MenuManager instance = null;
	
	//private Context context;
	
	MenuItem[] menuList;
	
	//**************************************************//
    //**********     CONSTRUCTORS     ******************//
	
	protected MenuManager() {
		
	}
	
	//**************************************************//
    //**********     PUBLIC METHODS     **************//
	
	public static synchronized MenuManager getInstance() {
		
		if(instance == null) instance = new MenuManager();
		return instance;
	}
	
	public void init(Context context) {
		
		//this.context = context;
		menuList = new MenuItem[MAX_MENU_ITEMS];
	}
	
	public void setUp(Menu menu, MenuInflater inflater) {
		
	    inflater.inflate(R.menu.menu, menu);
	}
	
	public void prepareMenu(Menu menu, boolean exit, boolean surrender) {
		
			menuList[ITEM_MUSIC] = menu.getItem(0).setEnabled(true);
			if(MusicManager.getInstance().getNoMusic())	{
				menuList[ITEM_MUSIC].setTitle(R.string.choiceEnableMusic);
				menuList[ITEM_MUSIC].setIcon(R.drawable.icon_music_enable);
			}
			else {
				menuList[ITEM_MUSIC].setTitle(R.string.choiceDisableMusic);
				menuList[ITEM_MUSIC].setIcon(R.drawable.icon_music_disable);
			}
			menuList[ITEM_SOUND] = menu.getItem(1).setEnabled(true);
			if(SoundManager.getInstance().getNoSound())	{
				menuList[ITEM_SOUND].setTitle(R.string.choiceEnableSound);
				menuList[ITEM_SOUND].setIcon(R.drawable.icon_sound_enable);
			}
			else {
				menuList[ITEM_SOUND].setTitle(R.string.choiceDisableSound);
				menuList[ITEM_SOUND].setIcon(R.drawable.icon_sound_disable);
			}
			menuList[ITEM_BACK]  = menu.getItem(2).setEnabled(true);
			menuList[ITEM_BACK].setIcon(R.drawable.icon_back);
			if(exit)			menuList[ITEM_BACK].setTitle(R.string.choiceExit);
			else if(surrender) 	menuList[ITEM_BACK].setTitle(R.string.choiceSurrender);
			else				menuList[ITEM_BACK].setTitle(R.string.choiceBack);
	}
	
	public MenuItem getMenuItem(int index) {
		
		return menuList[index];
	}
}
