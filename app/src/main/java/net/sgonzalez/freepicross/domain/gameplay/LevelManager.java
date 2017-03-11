package net.sgonzalez.freepicross.domain.gameplay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sgonzalez.freepicross.presentation.opengl.GLCube;

import android.content.Context;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class LevelManager {
	
	//**************************************************//
    //**********     GLOBAL VARIABLES     **************//
	
	private static LevelManager instance = null;
	
	private Context context;
	
	private int nCubesX;
	private int nCubesY;
	private int nCubesZ;
	private GLCube[][][] cube;
	
	//**************************************************//
    //**********     CONSTRUCTORS     ******************//
	
	protected LevelManager() {
		
	}
	
	//**************************************************//
    //**********     PUBLIC METHODS     ****************//
	
	public static synchronized LevelManager getInstance() {
		
		if(instance == null) instance = new LevelManager();
		return instance;
	}
	
	public void init(Context context) {
		
		this.context = context;
	}
	
	public void setUp(int levelID) {
		
		int i, j, k, index, cursor;
		String ch;
		
		//Read the String that represents the level
		String strLevel = readRawTextFile(context, levelID);
		
		//Get the width, height and depth
		this.nCubesX = Integer.parseInt(strLevel.substring(0, 1));
		this.nCubesY = Integer.parseInt(strLevel.substring(2, 3));
		this.nCubesZ = Integer.parseInt(strLevel.substring(4, 5));
		
		//Initialize variables;
		Boolean skeleton[][][] = new Boolean[nCubesX][nCubesY][nCubesZ];
		int		xHints[][][] = new int[nCubesX][nCubesY][nCubesZ];
		int		yHints[][][] = new int[nCubesX][nCubesY][nCubesZ];
		int		zHints[][][] = new int[nCubesX][nCubesY][nCubesZ];
		
		//Put the cursor at the beginning of the second line
		cursor = 6;

		//Read the solid/false map
		for(k=0, index=0 ; k<nCubesZ ; k++)
			for(j=0 ; j<nCubesY ; j++)
				for(i=0 ; i<nCubesX ; i++, index+=2) {
					ch = strLevel.substring(cursor + index, cursor+1 + index);
					if(Integer.parseInt(ch) == 0) skeleton[i][j][k] = false;
					else						  skeleton[i][j][k] = true;
				}
		
		cursor = cursor + index;
		//Read the X axis hints
		for(j=0, index=0 ; j<nCubesY ; j++)
			for(k=0 ; k<nCubesZ ; k++, index+=3) { 
				ch = strLevel.substring(cursor + index, cursor+2 + index);
				for(i=0 ; i<nCubesX ; i++) {
					
					xHints[i][j][k] = Integer.parseInt(ch);
				}
			}
		
		cursor = cursor + index;
		//Read the Y axis hints
		for(k=0, index=0 ; k<nCubesZ ; k++)
			for(i=0 ; i<nCubesX ; i++, index+=3) {
				ch = strLevel.substring(cursor + index, cursor+2 + index);
				for(j=0 ; j<nCubesY ; j++) {
					yHints[i][j][k] = Integer.parseInt(ch);
				}
			}
		
		cursor = cursor + index;
		//Read the Z axis hints
		for(j=0, index=0 ; j<nCubesY ; j++)
			for(i=0 ; i<nCubesX ; i++, index+=3) {
				ch = strLevel.substring(cursor + index, cursor+2 + index);
				for(k=0 ; k<nCubesZ ; k++) {
					zHints[i][j][k] = Integer.parseInt(ch);
				}
			}
		
		//Set up the cube
		cube = new GLCube[nCubesX][nCubesY][nCubesZ];
		for(i=0 ; i<nCubesX ; i++)
			for(j=0 ; j<nCubesY ; j++)
				for(k=0 ; k<nCubesZ ; k++)
					cube[i][j][k] = new GLCube(skeleton[i][j][k], xHints[i][j][k], yHints[i][j][k], zHints[i][j][k]);
	}
	
	public int getNCubesX() {
		return this.nCubesX;
	}
	
	public int getNCubesY()	{
		return this.nCubesY;
	}
	
	public int getNCubesZ() {
		return this.nCubesZ;
	}	
	
	public GLCube[][][] getCubes() {
		return this.cube;
	}
	
	//**************************************************//
    //**********     PRIVATE METHODS     ***************//
	
	private static String readRawTextFile(Context ctx, int resId)
    {
		InputStream inputStream = ctx.getResources().openRawResource(resId);

		InputStreamReader inputreader = new InputStreamReader(inputStream);
		BufferedReader buffreader = new BufferedReader(inputreader);
		String line;
		StringBuilder text = new StringBuilder();
		
		try {
			
			while (( line = buffreader.readLine()) != null) {
				text.append(line);
			    text.append('\n');
			}
		} catch (IOException e) {
			
			return null;
		}
		return text.toString();
	}
}