package net.sgonzalez.freepicross.presentation.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import edu.upc.fib.freepicrosspfc.R;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class TextureManager {
  //**************************************************//
  //**********     CONSTANTS     *********************//

  public static final int MAX_TEXTURES = 36;
  public static final int TEX_0 = 0;
  public static final int TEX_1 = 1;
  public static final int TEX_2 = 2;
  public static final int TEX_3 = 3;
  public static final int TEX_4 = 4;
  public static final int TEX_5 = 5;
  public static final int TEX_6 = 6;
  public static final int TEX_7 = 7;
  public static final int TEX_8 = 8;
  public static final int TEX_9 = 9;
  public static final int TEX_12 = 12;
  public static final int TEX_13 = 13;
  public static final int TEX_14 = 14;
  public static final int TEX_15 = 15;
  public static final int TEX_16 = 16;
  public static final int TEX_17 = 17;
  public static final int TEX_18 = 18;
  public static final int TEX_19 = 19;
  public static final int TEX_23 = 23;
  public static final int TEX_24 = 24;
  public static final int TEX_25 = 25;
  public static final int TEX_26 = 26;
  public static final int TEX_27 = 27;
  public static final int TEX_28 = 28;
  public static final int TEX_29 = 29;
  public static final int TEX_EMPTY = 30;
  public static final int TEX_ZOOM = 31;
  public static final int TEX_TOOLBOX = 32;
  public static final int TEX_BLACK = 33;
  //**************************************************//
  //**********     GLOBAL VARIABLES     **************//

  private static TextureManager instance = null;

  private Context context;

  private int[] textures = new int[MAX_TEXTURES];
  //**************************************************//
  //**********     CONSTRUCTORS     ******************//

  protected TextureManager() {
  }
  //**************************************************//
  //**********     PUBLIC METHODS     ****************//

  public static synchronized TextureManager getInstance() {
    if (instance == null) {
      instance = new TextureManager();
    }
    return instance;
  }

  public void init(Context context) {
    this.context = context;
  }

  public void setUp(GL10 gl) {
    gl.glGenTextures(MAX_TEXTURES, textures, 0);
    loadGLTextures(gl, context, TEX_0, R.drawable.tex_0);
    loadGLTextures(gl, context, TEX_1, R.drawable.tex_1);
    loadGLTextures(gl, context, TEX_2, R.drawable.tex_2);
    loadGLTextures(gl, context, TEX_3, R.drawable.tex_3);
    loadGLTextures(gl, context, TEX_4, R.drawable.tex_4);
    loadGLTextures(gl, context, TEX_5, R.drawable.tex_5);
    loadGLTextures(gl, context, TEX_6, R.drawable.tex_6);
    loadGLTextures(gl, context, TEX_7, R.drawable.tex_7);
    loadGLTextures(gl, context, TEX_8, R.drawable.tex_8);
    loadGLTextures(gl, context, TEX_9, R.drawable.tex_9);
    loadGLTextures(gl, context, TEX_12, R.drawable.tex_2c);
    loadGLTextures(gl, context, TEX_13, R.drawable.tex_3c);
    loadGLTextures(gl, context, TEX_14, R.drawable.tex_4c);
    loadGLTextures(gl, context, TEX_15, R.drawable.tex_5c);
    loadGLTextures(gl, context, TEX_16, R.drawable.tex_6c);
    loadGLTextures(gl, context, TEX_17, R.drawable.tex_7c);
    loadGLTextures(gl, context, TEX_18, R.drawable.tex_8c);
    loadGLTextures(gl, context, TEX_19, R.drawable.tex_9c);
    loadGLTextures(gl, context, TEX_23, R.drawable.tex_3s);
    loadGLTextures(gl, context, TEX_24, R.drawable.tex_4s);
    loadGLTextures(gl, context, TEX_25, R.drawable.tex_5s);
    loadGLTextures(gl, context, TEX_26, R.drawable.tex_6s);
    loadGLTextures(gl, context, TEX_27, R.drawable.tex_7s);
    loadGLTextures(gl, context, TEX_28, R.drawable.tex_8s);
    loadGLTextures(gl, context, TEX_29, R.drawable.tex_9s);
    loadGLTextures(gl, context, TEX_EMPTY, R.drawable.tex_empty);
    loadGLTextures(gl, context, TEX_ZOOM, R.drawable.tex_zoom);
    loadGLTextures(gl, context, TEX_TOOLBOX, R.drawable.tex_toolbox);
    loadGLTextures(gl, context, TEX_BLACK, R.drawable.tex_black);
  }

  public int[] getTexture() {
    return this.textures;
  }
  //**************************************************//
  //**********     PRIVATE METHODS     ***************//

  private void loadGLTextures(GL10 gl, Context context, int tex_index, int tex_name) {
    //Get the texture from the Android resource directory
    InputStream is = context.getResources().openRawResource(tex_name);
    Bitmap bitmap = null;
    try {
      //BitmapFactory is an Android graphics utility for images
      bitmap = BitmapFactory.decodeStream(is);
    } finally {
      //Always clear and close
      try {
        is.close();
        is = null;
      } catch(IOException e) {
      }
    }
    //Create Nearest Filtered Texture and bind it to texture array
    gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[tex_index]);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    if (gl instanceof GL11) {
      gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
      //
    } else {
      buildMipmap(gl, bitmap);
    }
    //Clean up
    bitmap.recycle();
  }

  private void buildMipmap(GL10 gl, Bitmap bitmap) {
    //
    int level = 0;
    //
    int height = bitmap.getHeight();
    int width = bitmap.getWidth();
    //
    while (height >= 1 || width >= 1) {
      //First of all, generate the texture from our bitmap and set it to the according level
      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
      //
      if (height == 1 || width == 1) {
        break;
      }
      //Increase the mipmap level
      level++;
      //
      height /= 2;
      width /= 2;
      Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, width, height, true);
      //Clean up
      bitmap.recycle();
      bitmap = bitmap2;
    }
  }
}
