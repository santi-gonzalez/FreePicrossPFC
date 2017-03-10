package edu.upc.fib.freepicrosspfc.gl;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import edu.upc.fib.freepicrosspfc.managers.GameManager;
import edu.upc.fib.freepicrosspfc.managers.SoundManager;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class GLView extends GLSurfaceView {
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private Context context;
	
	private GLRenderer mRenderer;
	
	private float oldX;
    private float oldY;
    
    //private float distX; Currently not used
    private float distY;
    
    private boolean zooming;
    private boolean hidding;
    private boolean spinning;
    private boolean hidden;
    
	private final float TOUCH_SCALE = 0.4f;
	
	//**************************************************//
    //**********     CONSTRUCTORS     ******************//
	
	public GLView(Context context) {
		super(context);
		
		this.context = context;
		
		//Set the ConfigChooser
		this.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		
		//Set the renderer
		if (mRenderer == null) mRenderer = new GLRenderer();
		this.setRenderer(mRenderer);
		
		//Set the PixelFormat
		this.getHolder().setFormat(PixelFormat.RGBA_8888);
		
		//Request focus, otherwise buttons won't react
		this.requestFocus();
		this.setFocusableInTouchMode(true);	
		
		// Render the view only when there is a change
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	//**************************************************//
    //**********     TOUCH EVENT OVERRIDE     **********//
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//Process the touch event
		processTouchEvent(event);
        
		//Render the result
        render();
        
        //Wait for render to finish
        while(!mRenderer.isRendered()) {}
        
        //Event has been handled
		return true;
	}
	
	//**************************************************//
	//**********     PRIVATE METHODS     ***************//
	
	private void processTouchEvent(MotionEvent event) {
		
		//Get where user is touching
		float x = event.getX();
        float y = event.getY();
        
        //Send data to the Renderer about the touched point
		mRenderer.setTouchX((int)x);
		mRenderer.setTouchY((int)y);
        
        //Define touch areas
        int upperArea = this.getHeight() / 10;
        int lowerArea = this.getHeight() - upperArea;
    	int leftmostArea = this.getWidth() / 10;
    	int rightmostArea = this.getWidth() - leftmostArea;
    	int middleEdge = this.getWidth() / 2;
        
        //A touch-move on the screen
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	
        	//Calculate the change
        	float dx = x - oldX;
        	float dy = y - oldY;
        	
        	//Accumulate finger distance
        	//distX += dx;
            distY += dy;
            
	        if(!GameManager.getInstance().isCleared()) {
        	
	        	//Zoom in/out if the touch move has been made in the upper
	        	if(y < upperArea) {if(!hidding && !spinning) zoom(dx);}
	        	//Hide Z rows if the touch move has been made in the left
	        	else if (x < leftmostArea) {if(!zooming && !spinning) hideZ();}
	        	//Hide X rows if the touch move has been made in the right
	        	else if (x > rightmostArea) {if(!zooming && !spinning) hideX();}
	        	//Rotate around the axis otherwise
	        	else {if(!zooming && !hidding && mRenderer.getMode() == 0) spin(dx, dy);}        
	        }
        
        //A press (release) on the screen
        } else if(event.getAction() == MotionEvent.ACTION_UP) {
        	
        	if(!GameManager.getInstance().isCleared()) {
        		
        		//Select a tool
        		if(!zooming && !hidding) {
		        	
        			if(y > lowerArea) {
		        		
		        		if(x < middleEdge) brush();
		        		else 			   hammer();
		        	}
		        	else {
		        		
		        		hit();
		        	}
        		}
        	}
        	else {
        		
        		//If level has been finished, exit on screen touch
        		((Activity) context).finish();
        	}
        	
        	//Reset the flags and the global variables
        	reset();
        }
        
        //Remember the values
        this.oldX = x;
        this.oldY = y;
	}
	
	private void zoom(float dx) {
		
		zooming = true;
		
		mRenderer.setZ(mRenderer.getZ() + dx * TOUCH_SCALE / 1);
		if(mRenderer.getZ() < -75) mRenderer.setZ(-75);
		if(mRenderer.getZ() > -10) mRenderer.setZ(-10);
		
		SoundManager.getInstance().playSound(SoundManager.SFX_ZOOM);
	}
	
	private void hideZ() {
		
		hidding = true;
		
		if(distY > 100) {
			
			if(!hidden) mRenderer.setHideZ(mRenderer.getHideZ() - 1);
			hidden = true;
		}
		else if(distY < -100) {
			
			if(!hidden) mRenderer.setHideZ(mRenderer.getHideZ() + 1);
			hidden = true;
		}
		
		if(hidden) {
			
			mRenderer.setHideX(0);
    		if(mRenderer.getHideZ() < 0) mRenderer.setHideZ(0);
    		if(mRenderer.getHideZ() > mRenderer.getNCubesZ() - 1) mRenderer.setHideZ(mRenderer.getNCubesZ() - 1);
		}
	}
	
	private void hideX() {
		
		hidding = true;
		
		if(distY > 100) {
			
			if(!hidden) mRenderer.setHideX(mRenderer.getHideX() - 1);
			hidden = true;
		}
		else if(distY < -100) {
		
			if(!hidden) mRenderer.setHideX(mRenderer.getHideX() + 1);
			hidden = true;
		}
		
		if(hidden) {
			
			mRenderer.setHideZ(0);
    		if(mRenderer.getHideX() < 0) mRenderer.setHideX(0);
    		if(mRenderer.getHideX() > mRenderer.getNCubesX() - 1) mRenderer.setHideX(mRenderer.getNCubesX() - 1);
		}
	}
	
	private void spin(float dx, float dy) {
		
		spinning = true;
		
		mRenderer.setXRot(mRenderer.getXRot() + dy * TOUCH_SCALE);
		mRenderer.setYRot(mRenderer.getYRot() + dx * TOUCH_SCALE);
		
		if(mRenderer.getXRot() >  90.0f) mRenderer.setXRot(90.0f);
		if(mRenderer.getXRot() < -90.0f) mRenderer.setXRot(-90.0f);
	}
	
	private void brush() {
		
		if(mRenderer.getMode() != 1) mRenderer.setMode(1);
		else						 mRenderer.setMode(0);
		
		SoundManager.getInstance().playSound(SoundManager.SFX_TOOL);
	}
	
	private void hammer() {
		
		if(mRenderer.getMode() != 2) mRenderer.setMode(2);
		else						 mRenderer.setMode(0);
		
		SoundManager.getInstance().playSound(SoundManager.SFX_TOOL);
	}
	
	private void hit() {
		
		switch(mRenderer.touchCube()){
		
		case 0: break;
		case 1: SoundManager.getInstance().playSound(SoundManager.SFX_MARK); break;
		case 2: SoundManager.getInstance().playSound(SoundManager.SFX_MARK); break;
		case 3: SoundManager.getInstance().playSound(SoundManager.SFX_DESTROY);
				process(); break;
		case 4: SoundManager.getInstance().playSound(SoundManager.SFX_ERROR);
				GameManager.getInstance().setErrorsCur(GameManager.getInstance().getErrorsCur()+1);
				process(); break;
		}
	}
	
	private void reset() {
		
		zooming = false;
    	hidding = false;
    	spinning = false;
    	hidden = false;
    	
    	oldX = 0;
    	oldY = 0;
    	
    	//distX = 0;
    	distY = 0;
	}
	
	private void render() {
		
        mRenderer.setRendered(false);
        if (!GameManager.getInstance().isCleared()) requestRender();
	}
	
	private void process() {
		
		//If user has no more lives
		if(!GameManager.getInstance().isCleared() && noMoreLifes()) {
        	
        	GameManager.getInstance().setGameState(GameManager.STATE_NOLIFES);
        	((Activity) context).finish();
        }

		//If user finished the level
        if(!GameManager.getInstance().isCleared() && isLevelCleared()) {
        	
        	setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        	
        	GameManager.getInstance().setGameState(GameManager.STATE_CLEARED);
        	GameManager.getInstance().setCleared(true);
        }
	}
	
	private boolean noMoreLifes() {
		
		if(GameManager.getInstance().getErrorsCur() >= GameManager.getInstance().getErrorsMax()) return true;
		return false;
	}
	
	private boolean isLevelCleared() {

		return mRenderer.allCubesDestroyed();
	}
}
