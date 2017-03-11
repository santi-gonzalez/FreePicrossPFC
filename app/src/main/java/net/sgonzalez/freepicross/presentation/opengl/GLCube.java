package net.sgonzalez.freepicross.presentation.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import net.sgonzalez.freepicross.domain.cube.CubeProperties;
import net.sgonzalez.freepicross.domain.gameplay.GameManager;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class GLCube {

	//**************************************************//
    //**********     CONSTANTS     *********************//
	
	public static final float RED_COLOR_CONSTANT = 0.003921569f;
	public static final int   DO_NOTHING   		 = 0;
	public static final int   MARK_CUBE    		 = 1;
	public static final int   UNMARK_CUBE  		 = 2;
	public static final int   DESTROY_CUBE 		 = 3;
	public static final int   ERROR_CUBE   		 = 4;
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private CubeProperties cubeProperties;
	
	private FloatBuffer vertexBuffer;
	private FloatBuffer textureBuffer;
	private ByteBuffer indexBuffer_x;
	private ByteBuffer indexBuffer_y;
	private ByteBuffer indexBuffer_z;

	private float vertices[] = {
						-1.0f, -1.0f,  1.0f,
						 1.0f, -1.0f,  1.0f,
						-1.0f,  1.0f,  1.0f,
						 1.0f,  1.0f,  1.0f,
			
						 1.0f, -1.0f,  1.0f,
						 1.0f, -1.0f, -1.0f, 
						 1.0f,  1.0f,  1.0f, 
						 1.0f,  1.0f, -1.0f,
			
						 1.0f, -1.0f, -1.0f, 
						-1.0f, -1.0f, -1.0f, 
						 1.0f,  1.0f, -1.0f, 
						-1.0f,  1.0f, -1.0f,
			
						-1.0f, -1.0f, -1.0f, 
						-1.0f, -1.0f,  1.0f, 
						-1.0f,  1.0f, -1.0f, 
						-1.0f,  1.0f,  1.0f,
			
						-1.0f, -1.0f, -1.0f, 
						 1.0f, -1.0f, -1.0f, 
						-1.0f, -1.0f,  1.0f, 
						 1.0f, -1.0f,  1.0f,
			
						-1.0f,  1.0f,  1.0f, 
						 1.0f,  1.0f,  1.0f, 
						-1.0f,  1.0f, -1.0f, 
						 1.0f,  1.0f, -1.0f, 
	};
	
	private float texture[] = {
						0.0f, 1.0f, 
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
						
						0.0f, 1.0f, 
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
						
						0.0f, 1.0f, 
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
						
						0.0f, 1.0f, 
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
						
						0.0f, 1.0f, 
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
						
						0.0f, 1.0f, 
						1.0f, 1.0f,
						0.0f, 0.0f,
						1.0f, 0.0f,
	};

	private byte indices_x[] = {
			4, 5, 7, 4, 7, 6,
			12, 13, 15, 12, 15, 14, 
	};

	private byte indices_y[] = {
			16, 17, 19, 16, 19, 18, 
			20, 21, 23, 20, 23, 22, 
	};
	
	private byte indices_z[] = {
			0, 1, 3, 0, 3, 2, 
			8, 9, 11, 8, 11, 10,
	};
	
	//**************************************************//
    //**********     CONSTRUCTORS     ******************//
	
	public GLCube(boolean solid, int xHint, int yHint, int zHint) {
		
		//Create new CubeProperties for this cube
		cubeProperties = new CubeProperties(solid, xHint, yHint, zHint);
		
		//Initialize buffers
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuf.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);

		indexBuffer_x = ByteBuffer.allocateDirect(indices_x.length);
		indexBuffer_x.put(indices_x);
		indexBuffer_x.position(0);
		indexBuffer_y = ByteBuffer.allocateDirect(indices_y.length);
		indexBuffer_y.put(indices_y);
		indexBuffer_y.position(0);
		indexBuffer_z = ByteBuffer.allocateDirect(indices_z.length);
		indexBuffer_z.put(indices_z);
		indexBuffer_z.position(0);
	}

	//**************************************************//
    //**********     PUBLIC METHODS     ****************//
	
	public void draw(GL10 gl) {

		//Only draw the cube if it can be seen
		if(this.cubeProperties.getShown() && !this.cubeProperties.getDestroyed()) {
			
			//Set the color to white, by default
			gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

			//Set the face rotation
			gl.glFrontFace(GL10.GL_CCW);
			
			//Point to our buffers
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
			//Paint the cube in a different color if needed
			if(cubeProperties.getMarked()) gl.glColor4f(0.0f, 0.0f, 25.0f, 1.0f);
			else if(cubeProperties.getErrored()) gl.glColor4f(50.0f, 0.0f, 0.0f, 1.0f);
			//If level is finished, not paint any color at all
			if(GameManager.getInstance().isCleared()) gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
			
			//Enable the vertex and texture arrays
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			
			//If level is finished, not paint any number at all
			if(GameManager.getInstance().isCleared())  gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureManager.getInstance().getTexture()[TextureManager.TEX_EMPTY]);
			
			//Bind the texture and draw the vertices as triangle strips
			if(!GameManager.getInstance().isCleared()) gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureManager.getInstance().getTexture()[cubeProperties.getXHint()]);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices_x.length, GL10.GL_UNSIGNED_BYTE, indexBuffer_x);
			if(!GameManager.getInstance().isCleared()) gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureManager.getInstance().getTexture()[cubeProperties.getYHint()]);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices_y.length, GL10.GL_UNSIGNED_BYTE, indexBuffer_y);
			if(!GameManager.getInstance().isCleared()) gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureManager.getInstance().getTexture()[cubeProperties.getZHint()]);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices_z.length, GL10.GL_UNSIGNED_BYTE, indexBuffer_z);
			
			//Disable the client state before leaving
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		}
	}
	
	public void drawColor(GL10 gl, int ID) {

		//Only draw the cube if it can be seen
		if(this.cubeProperties.getShown() && !this.cubeProperties.getDestroyed()) {
			
			//Set the ID for this cube
			cubeProperties.setID(ID);
			
			//Set the face rotation
			gl.glFrontFace(GL10.GL_CCW);
			
			//Point to our buffers
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			
			//Enable the vertex array
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			
			//Set unique color (from integer red 0 to 255 max)
			gl.glColor4f(RED_COLOR_CONSTANT * ID, 0.0f, 0.0f, 1.0f);
			
			//Draw the vertices as triangles, based on the Index Buffer information
			gl.glDrawElements(GL10.GL_TRIANGLES, indices_x.length, GL10.GL_UNSIGNED_BYTE, indexBuffer_x);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices_y.length, GL10.GL_UNSIGNED_BYTE, indexBuffer_y);
			gl.glDrawElements(GL10.GL_TRIANGLES, indices_z.length, GL10.GL_UNSIGNED_BYTE, indexBuffer_z);
			
			//Disable the client state before leaving
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}
	}
	
	public int tryToMark() {
		
		if(!cubeProperties.getErrored()) {
		
			if(cubeProperties.getMarked()) 	{cubeProperties.setMarked(false); return UNMARK_CUBE;}
			else							{cubeProperties.setMarked(true);  return MARK_CUBE;}
		}
		return DO_NOTHING;
	}
	
	public int tryToDestroy() {
		
		if(!cubeProperties.getMarked() && !cubeProperties.getErrored()) {
			
			if(cubeProperties.getSolid())  	{cubeProperties.setErrored(true);   return ERROR_CUBE;}
			else							{cubeProperties.setDestroyed(true); return DESTROY_CUBE;}
		}
		return DO_NOTHING;
	}
	
	//**************************************************//
    //**********     SETTERS AND GETTERS     ***********//
	
	public void setShown(boolean shown) 		{cubeProperties.setShown(shown);}
	public void setDestroyed(boolean destroyed) {cubeProperties.setDestroyed(destroyed);}
	public void setMarked(boolean marked) 		{cubeProperties.setMarked(marked);}
	public void setErrored(boolean errored) 	{cubeProperties.setErrored(errored);}
	public void setID(int ID)					{cubeProperties.setID(ID);}
	
	public boolean getSolid()					{return cubeProperties.getSolid();}
	public boolean getShown()					{return cubeProperties.getShown();}
	public boolean getDestroyed()				{return cubeProperties.getDestroyed();}
	public boolean getMarked()					{return cubeProperties.getMarked();}
	public boolean getErrored()					{return cubeProperties.getErrored();}
	public int 	   getID()						{return cubeProperties.getID();}
}
