package net.sgonzalez.freepicross.presentation.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Santiago Gonzàlez i Bertran
 */
public class GLFrame {
  //**************************************************//
  //**********     GLOBAL VARIABLES     **************//

  private FloatBuffer vertexBuffer;
  private FloatBuffer textureBuffer;

  private float vertices[];

  private float texture[] = {
  //Mapping coordinates for the vertices
  0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, };
  //**************************************************//
  //**********     CONSTRUCTORS     ******************//

  public GLFrame(float vertices[]) {
    this.vertices = vertices;
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
  }
  //**************************************************//
  //**********     PUBLIC METHODS     ****************//

  public void draw(GL10 gl, int tex_id) {
    //Set the color to white, by default
    gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    //Set the face rotation
    gl.glFrontFace(GL10.GL_CW);
    //Point to our vertex buffer
    gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
    //Enable vertex buffer
    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    //Bind the texture and draw the vertices as triangle strips
    gl.glBindTexture(GL10.GL_TEXTURE_2D, TextureManager.getInstance().getTexture()[tex_id]);
    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
    //Disable the client state before leaving
    gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
  }
}
