package edu.upc.fib.freepicrosspfc.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import edu.upc.fib.freepicrosspfc.managers.GameManager;
import edu.upc.fib.freepicrosspfc.managers.LevelManager;
import edu.upc.fib.freepicrosspfc.managers.TextureManager;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class GLRenderer implements GLSurfaceView.Renderer{
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private GLCube  cube[][][];		//The cube matrix
	private GLFrame toolboxFrame;	//Brush and hammer frame
	private GLFrame staticsFrame;	//Time and errors frame
	private GLFrame hideXFrame;		//Right black border
	private GLFrame hideZFrame;		//Left black border
	
	private int nCubesX;			//Cubes length in X axis
	private int nCubesY;			//Cubes length in Y axis
	private int nCubesZ;			//Cubes length in Z axis
	
	private float xRot;				//Rotation around X axis
	private float yRot;				//Rotation around Y axis
	private float z;				//Depth in the screen
	
	private int touchX;				//X coordinate of user touch
	private int touchY;				//Y coordinate of user touch
	private int screenW;			//Screen width
	private int screenH;			//Screen height
	
	private int hideX;				//Rows hidden horizontally
	private int hideZ;				//Rows hidden deeper
	
	private int mode;				//Touch screen mode
	
	private int touchedCubeID;		//ID of the touched cube

	private boolean rendered;		//Render flag. (true when render finishes)

	//**************************************************//
    //**********     RENDERER METHODS      *************//
	
	public void onDrawFrame(GL10 gl) {
	
		//Reset The Current Modelview Matrix
		gl.glLoadIdentity();
		
		//If cube has been finished, spin it a little
		if(GameManager.getInstance().isCleared()) {
			
			this.yRot += 0.7;
			this.xRot += 0.3;
			this.z     = -10 * this.getLargestSide();
			this.hideX = 0;
			this.hideZ = 0;
		}
		//Hide hidden rows before they are drawn
		hideRows();
		
		//Switch to perspective view
		perspective(gl);
		
		//Draw the color scaled scene and get the cube picked to further treatment
		if(!GameManager.getInstance().isCleared() && this.mode != 0) touchedCubeID = pickCube(gl);
		
		//Draw the actual scene
		drawScene(gl);
		
		//Draw the user interface
		if(!GameManager.getInstance().isCleared()) {
		
			//Switch to orthographic view
			ortho(gl);
			
			//Draw the UI
			drawUI(gl);
		}
		
		setRendered(true);
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		if(height == 0) { 						//Prevent A Divide By Zero By
			height = 1; 						//Making Height Equal One
		}
		
		this.screenW = width;					//Set screen width global
		this.screenH = height;					//Set screen height global

		gl.glViewport(0, 0, width, height); 	//Reset The Current Viewport
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		//One time OpenGL initialization
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		gl.glDisable(GL10.GL_DITHER);			//Disable dithering
		gl.glDisable(GL10.GL_LIGHTING);			//Disable lighting
		
		gl.glShadeModel(GL10.GL_SMOOTH); 		//Enable Smooth Shading
		gl.glClearDepthf(1.0f); 				//Depth Buffer Setup
		gl.glDepthFunc(GL10.GL_LEQUAL); 		//The Type Of Depth Testing To Do
		
		//Initializations
		initTextures(gl);
		initCubes();
		initFrames();
		initVariables();
	}
	
	//**************************************************//
	//**********     PUBLIC METHODS     ****************//
	
	public int touchCube(){
		
		for (int i=0 ; i<nCubesX ; i++)
			for (int j=0 ; j<nCubesY ; j++)
				for (int k=0 ; k<nCubesZ ; k++) {
				
					if(cube[i][j][k].getID() == touchedCubeID) {
			
						switch(mode) {
						
						case 0: break;
						case 1: return cube[i][j][k].tryToMark();
						case 2: return cube[i][j][k].tryToDestroy();
						}
					}
				}
		return 0;
	}
	
	public boolean allCubesDestroyed() {
		
		for (int i=0 ; i<nCubesX ; i++)
			for (int j=0 ; j<nCubesY ; j++)
				for (int k=0 ; k<nCubesZ ; k++){
					if(!cube[i][j][k].getSolid() && !cube[i][j][k].getDestroyed()) return false;
				}

		return true;
	}
	
	//**************************************************//
	//**********     PRIVATE METHODS     ***************//
	
	private void initTextures(GL10 gl){
		
		TextureManager.getInstance().setUp(gl);
	}
	
	private void initCubes() {
		
		LevelManager.getInstance().setUp(GameManager.getInstance().getLevelID());
		
		nCubesX = LevelManager.getInstance().getNCubesX();
		nCubesY = LevelManager.getInstance().getNCubesY();
		nCubesZ = LevelManager.getInstance().getNCubesZ();
		cube =    LevelManager.getInstance().getCubes();
	}
	
	private void initFrames() {
		
		float upperFrameVertices[] = { 0.0f,  9.0f,  0.0f, 10.0f,  9.0f,  0.0f,  0.0f, 10.0f,  0.0f, 10.0f, 10.0f,  0.0f};
		float lowerFrameVertices[] = { 0.0f,  0.0f,  0.0f, 10.0f,  0.0f,  0.0f,  0.0f,  1.0f,  0.0f, 10.0f,  1.0f,  0.0f};
		float rightFrameVertices[] = { 9.0f,  1.0f,  0.0f, 10.0f,  1.0f,  0.0f,  9.0f,  9.0f,  0.0f, 10.0f,  9.0f,  0.0f};
		float leftFrameVertices[]  = { 0.0f,  1.0f,  0.0f,  1.0f,  1.0f,  0.0f,  0.0f,  9.0f,  0.0f,  1.0f,  9.0f,  0.0f};
		
		staticsFrame = new GLFrame(upperFrameVertices);
		toolboxFrame = new GLFrame(lowerFrameVertices);
		hideXFrame = new GLFrame(rightFrameVertices);
		hideZFrame = new GLFrame(leftFrameVertices);
	}
	
	private void initVariables() {
		
		this.xRot =  20.0f;
		this.yRot = -30.0f;
		this.z    = -10 * this.getLargestSide();
		
		this.hideX = 0;
		this.hideZ = 0;
		
		this.mode = 0;
	}
	
	private void perspective (GL10 gl) {
		
		gl.glEnable(GL10.GL_DEPTH_TEST); 		//Enables Depth Testing
		
		gl.glMatrixMode(GL10.GL_PROJECTION); 	//Select The Projection Matrix
		gl.glLoadIdentity(); 					//Reset The Projection Matrix

		//Calculate aspect ratio
		GLU.gluPerspective(gl, 45.0f, (float)screenW / (float)screenH, 0.1f, 100.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW); 	//Select The Modelview Matrix
		gl.glLoadIdentity(); 					//Reset The Modelview Matrix
	}
	
	private void ortho (GL10 gl) {
		
	    gl.glDisable(GL10.GL_DEPTH_TEST );	
	    
	    gl.glMatrixMode(GL10.GL_PROJECTION );
	    gl.glLoadIdentity();
	    
	    //Make a 10 x 10 orthographic matrix
	    GLU.gluOrtho2D(gl, 0, 10, 0, 10);
	    
	    gl.glMatrixMode(GL10.GL_MODELVIEW );
	    gl.glLoadIdentity();
	}
	
	private int pickCube(GL10 gl) {
		
		//Set clear color complete white (to avoid bad color picking)
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		//Disable Texture Mapping
		gl.glDisable(GL10.GL_TEXTURE_2D);
		
		//Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

		//Drawing
		for (int i=0, id=0 ; i<nCubesX ; i++)
			for (int j=0 ; j<nCubesY ; j++)
				for (int k=0 ; k<nCubesZ ; k++, id++)
				{
					
					gl.glTranslatef(0.0f, 0.0f, z);				//Move z units into the screen
					
					//Rotate around the axis based on the rotation matrix
					gl.glRotatef(xRot, 1.0f, 0.0f, 0.0f);		//X
					gl.glRotatef(yRot, 0.0f, 1.0f, 0.0f);		//Y
					
					//Translate the cube to its position relative to the cube matrix
					gl.glTranslatef((-1.0f * (nCubesX-1-hideX))+(2*i), (-1.0f * (nCubesY-1))+(2*j), (1.0f * (nCubesZ-1+hideZ))-(2*k));
					
					//Draw the cube in red-scale
					if(isNotSurrounded(i, j, k)) cube[i][j][k].drawColor(gl, id);
					
					//Move back
					gl.glTranslatef((1.0f * (nCubesX-1-hideX))-(2*i), (1.0f * (nCubesY-1))-(2*j), (-1.0f * (nCubesZ-1+hideZ))+(2*k));
					gl.glRotatef(yRot, 0.0f, -1.0f, 0.0f);
					gl.glRotatef(xRot, -1.0f, 0.0f, 0.0f);
					gl.glTranslatef(0.0f, 0.0f, -z);
				}
		
		//Get the picked Cube
		return this.readPixel(gl);
	}
	
	private void drawScene(GL10 gl) {
		
		//Set ClearColor by the mode selected
		if(!GameManager.getInstance().isCleared()) {
			
			switch(mode) {
			
			case 0: gl.glClearColor(0.25f, 0.75f, 0.25f, 1.0f); break;
			case 1: gl.glClearColor(0.25f, 0.50f, 0.75f, 1.0f); break;
			case 2: gl.glClearColor(0.75f, 0.50f, 0.55f, 1.0f); break;
			}
		} else gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		//Enable Texture Mapping
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		//Clear Screen And Depth Buffer
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		
		//Drawing
		for (int i=0 ; i<nCubesX ; i++)
			for (int j=0 ; j<nCubesY ; j++)
				for (int k=0 ; k<nCubesZ ; k++)
				{
					//gl.glLoadIdentity();						//Reset The Current Modelview Matrix
					gl.glTranslatef(0.0f, 0.0f, z);				//Move z units into the screen
					
					//Rotate around the axis based on the rotation matrix
					gl.glRotatef(xRot, 1.0f, 0.0f, 0.0f);		//X
					gl.glRotatef(yRot, 0.0f, 1.0f, 0.0f);		//Y
					
					//Translate the cube to its position relative to the cube matrix
					gl.glTranslatef((-1.0f * (nCubesX-1-hideX))+(2*i), (-1.0f * (nCubesY-1))+(2*j), (1.0f * (nCubesZ-1+hideZ))-(2*k));
					
					//Draw the cube
					if(isNotSurrounded(i, j, k)) cube[i][j][k].draw(gl);	//Draw the Cube
					
					//Move back
					gl.glTranslatef((1.0f * (nCubesX-1-hideX))-(2*i), (1.0f * (nCubesY-1))-(2*j), (-1.0f * (nCubesZ-1+hideZ))+(2*k));
					gl.glRotatef(yRot, 0.0f, -1.0f, 0.0f);
					gl.glRotatef(xRot, -1.0f, 0.0f, 0.0f);
					gl.glTranslatef(0.0f, 0.0f, -z);
				}
	}
	
	private boolean isNotSurrounded(int x, int y, int z) {
		
		if(x-1 < 0 || x+1 >= nCubesX) return true;
		else if(!cube[x-1][y][z].getShown() || !cube[x+1][y][z].getShown()) return true;
		else if(cube[x-1][y][z].getDestroyed() || cube[x+1][y][z].getDestroyed()) return true;
		if(y-1 < 0 || y+1 >= nCubesY) return true;
		else if(!cube[x][y-1][z].getShown() || !cube[x][y+1][z].getShown()) return true;
		else if(cube[x][y-1][z].getDestroyed() || cube[x][y+1][z].getDestroyed()) return true;
		if(z-1 < 0 || z+1 >= nCubesZ) return true;
		else if(!cube[x][y][z-1].getShown() || !cube[x][y][z+1].getShown()) return true;
		else if(cube[x][y][z-1].getDestroyed() || cube[x][y][z+1].getDestroyed()) return true;
		
		return false;
	}
	
	private void drawUI(GL10 gl) {
		
		hideXFrame.draw(gl, TextureManager.TEX_BLACK);
		hideZFrame.draw(gl, TextureManager.TEX_BLACK);
		staticsFrame.draw(gl, TextureManager.TEX_ZOOM);
		toolboxFrame.draw(gl, TextureManager.TEX_TOOLBOX);
	}
	
	private int getLargestSide() {
		
		return Math.max(nCubesX, Math.max(nCubesY,nCubesZ));
	}
	
	private int readPixel(GL10 gl) {
		
		ByteBuffer PixelBuffer = ByteBuffer.allocateDirect(4);
		PixelBuffer.order(ByteOrder.nativeOrder());
		gl.glReadPixels(touchX, screenH - touchY, 1, 1, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, PixelBuffer);
		byte b[] = new byte[4];
		PixelBuffer.get(b);
		return b[0] & 0xff;
    }
	
	private void hideRows() {
		
		for (int i=nCubesX-1 ; i>=0 ; i--)
			for (int j=0 ; j<nCubesY ; j++)
				for (int k=0 ; k<nCubesZ ; k++) {
					cube[i][j][k].setShown(true);
					if(i>=nCubesX - hideX) cube[i][j][k].setShown(false);
					if(k<hideZ) cube[i][j][k].setShown(false);
				}
	}
	
	//**************************************************//
    //**********     SETTERS AND GETTERS     ***********//
	
	public void  		setXRot(float xRot) 			{this.xRot=xRot;}
	public void  		setYRot(float yRot) 			{this.yRot=yRot;}
	public void  		setZ(float z)					{this.z=z;}
	public void  		setTouchX(int touchX)			{this.touchX = touchX;}
	public void  		setTouchY(int touchY)			{this.touchY = touchY;}
	public void  		setHideX(int hideX)				{this.hideX = hideX;}
	public void  		setHideZ(int hideZ)				{this.hideZ = hideZ;}
	public void  		setMode(int mode) 				{this.mode = mode;}
	public void 		setRendered(boolean rendered)	{this.rendered = rendered;}
	
	public int	 		getNCubesX()					{return this.nCubesX;}
	public int	 		getNCubesY()					{return this.nCubesY;}
	public int	 		getNCubesZ()					{return this.nCubesZ;}
	public float 		getXRot()						{return this.xRot;}
	public float 		getYRot()						{return this.yRot;}
	public float 		getZ()							{return this.z;}
	public int   		getHideX()						{return this.hideX;}
	public int   		getHideZ()						{return this.hideZ;}
	public int   		getMode() 						{return this.mode;}
	public boolean 		isRendered() 					{return rendered;}
}
