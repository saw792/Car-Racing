import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import javax.swing.*;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.*;
import com.jogamp.opengl.util.texture.*;


public class GLGraphics implements GLEventListener{
  TextRenderer renderer;
  GLU glu;
  GLCapabilities capabilities;
  JFrame frame;
  GLCanvas canvas;
  FPSAnimator animator;
  boolean hardwareaccel = true;
  boolean fullscreen = false;
  Texture[] textures = new Texture[1];
  TextureCoords tc;
  Car testcar = new Car(1, 0.0, 0.0, 0.0, 90.0);;
  
  int framewidth = 0;
  int frameheight = 0;
  
  Texture[] tracktiles = new Texture[10];
  
  Texture[] skytex = new Texture[5];
  Texture[] hi_res_sky = new Texture[1];
  int sky;
  
  ArrayList<UIObject> overlays = new ArrayList<UIObject>();
  
  int listsToDraw;
  
  float camera_zoom = 10;
  float camera_rotate = 0;
  
  public GLGraphics(boolean windowed) {
	  Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
	  frame = new JFrame("Car Racing");
	  //Specify initial canvas options
	  capabilities = new GLCapabilities(GLProfile.getDefault());
	  capabilities.setHardwareAccelerated(hardwareaccel);
	  capabilities.setDoubleBuffered(true);
	  capabilities.setRedBits(8);
	  capabilities.setBlueBits(8);
	  capabilities.setGreenBits(8);
	  capabilities.setAlphaBits(8);
	  
	  canvas = new GLCanvas(capabilities);
	  canvas.addGLEventListener(this);
	  
	  frame.add(canvas);
	  frame.setUndecorated(!windowed);
	  frame.setSize(d);
	  
	  framewidth = d.width;
	  frameheight = d.height;
	  
	  frame.setVisible(true);
	  canvas.requestFocus();
  }

  public GLCanvas getCanvas() {
	  return canvas;
  }
  
  public void cameraZoom (float distance) {
	  camera_zoom += distance;
  }
  
  public void cameraRotate (float angle) {
	  camera_rotate += angle;
  }
  
  public void printText(String text) {
	  renderer.begin3DRendering();
	  renderer.draw3D(text,(float) -Math.sin(Math.toRadians(camera_rotate)), 1.0f,(float) -Math.cos(Math.toRadians(camera_rotate)), 0.01f);
	  renderer.end3DRendering();
  }
  
  private void loadTextures(GL2 gl) {
	  try {
	  textures[0] = TextureIO.newTexture(new File("Sunset.jpg"), false);
	  skytex[0] = TextureIO.newTexture(new File("sky/skyfront.jpg"), false);
	  skytex[1] = TextureIO.newTexture(new File("sky/skyleft.jpg"), false);
	  skytex[2] = TextureIO.newTexture(new File("sky/skyback.jpg"), false);
	  skytex[3] = TextureIO.newTexture(new File("sky/skyright.jpg"), false);
	  skytex[4] = TextureIO.newTexture(new File("sky/skytop.jpg"), false);
	  hi_res_sky[0] = TextureIO.newTexture(new File("sky/hirestest1.jpg"), false);
	  
	  tracktiles[0] = TextureIO.newTexture(new File("Asphalt.tga"), false);
	  } catch (Exception e) {
		  System.out.println(e);
	  }
  }
  
  private void loadModels(GL2 gl) {
	  Car.modelInit(gl);
  }
  
  private void loadSky(GL2 gl) {
	  sky = gl.glGenLists(1);
	  
	  gl.glNewList(sky, GL2.GL_COMPILE);
	  gl.glEnable(GL2.GL_TEXTURE_2D);
	  gl.glColor3f(1.0f, 1.0f, 1.0f);
	  
	    //Front
	    skytex[0].bind();
	    gl.glBegin(GL2.GL_QUADS);
	    
	      gl.glNormal3f(0.0f, 0.0f, -1.0f);
	      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, 1.0f);
	      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f, -1.0f, 1.0f);
	    
	    gl.glEnd();
	    
	    //Left
	    skytex[1].bind();
	    gl.glBegin(GL2.GL_QUADS);
	    
	      gl.glNormal3f(1.0f, 0.0f, 0.0f);
	      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, 1.0f);
	    
	    gl.glEnd();
	    
	    //Back
	    skytex[2].bind();
	    //hi_res_sky[0].bind();
	    gl.glBegin(GL2.GL_QUADS);
	    
	      gl.glNormal3f(0.0f, 0.0f, 1.0f);
	      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f, -1.0f, -1.0f);
	      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
	    
	    gl.glEnd();
	    
	    //Right
	    skytex[3].bind();
	    gl.glBegin(GL2.GL_QUADS);
	    
	      gl.glNormal3f(-1.0f, 0.0f, 0.0f);
	      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(1.0f, -1.0f, 1.0f);
	      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f, -1.0f, -1.0f);
	    
	    gl.glEnd();
	    
	    //Top
	    skytex[4].bind();
	    gl.glBegin(GL2.GL_QUADS);
	    
	      gl.glNormal3f(0.0f, -1.0f, 0.0f);
	      gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, 1.0f, 1.0f);
	      gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(1.0f, 1.0f, -1.0f);
	      gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(1.0f, 1.0f, 1.0f);
	    
	    gl.glEnd();
	    
	  gl.glDisable(GL2.GL_TEXTURE_2D);  
	  gl.glEndList();
  }
  
  private void loadDisplayLists(GL2 gl) {
	  listsToDraw = gl.glGenLists(1);
	  
	  gl.glNewList(listsToDraw, GL2.GL_COMPILE);
	  
		gl.glBegin(GL.GL_TRIANGLES);
		  gl.glColor3f(1.0f, 0.0f, 0.0f);
		  gl.glVertex3f(-1.0f, 0.0f, 0.0f);
		  gl.glColor3f(0.0f, 1.0f, 0.0f);
		  gl.glVertex3f(0.0f, 2.0f, 0.0f);
		  gl.glColor3f(.0f, 0.0f, 1.0f);
		  gl.glVertex3f(1.0f, 0.0f, 0.0f);
		gl.glEnd();
		
	  gl.glEndList();
  }
  
  public void loadTrack(GL2 gl) {
	  
  }
  
  public void drawSky(GL2 gl) {
		gl.glPushMatrix();
		gl.glLoadIdentity();
		
		gl.glPushAttrib(GL2.GL_ENABLE_BIT);
		gl.glEnable(GL2.GL_TEXTURE_2D);
	    gl.glDisable(GL2.GL_DEPTH_TEST);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_BLEND);
		
		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		gl.glCallList(sky);
		
		gl.glPopAttrib();
		gl.glPopMatrix();
  }
  
  private void setCamera(GLAutoDrawable drawable, GL2 gl, GLU glu, float distance) {
	  gl.glMatrixMode(GL2.GL_PROJECTION);
	  gl.glLoadIdentity();
	  
	  glu.gluPerspective(30, (float)drawable.getWidth() / (float)drawable.getHeight(), 1, 1000);
	  
	  gl.glMatrixMode(GL2.GL_MODELVIEW);
	  gl.glLoadIdentity();
	  glu.gluLookAt(distance * Math.sin(Math.toRadians(camera_rotate)), distance * 0.577f, distance * Math.cos(Math.toRadians(camera_rotate)), 0, 0, 0, 0, 1, 0);
  }
  
  public void display(GLAutoDrawable drawable) {
	GL2 gl = drawable.getGL().getGL2();
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	gl.glLoadIdentity();
	setCamera(drawable, gl, glu, camera_zoom);
	
	drawSky(gl);
	
	gl.glEnable(GL2.GL_TEXTURE_2D);
	textures[0].bind();
	testcar.draw(gl);
	
	tracktiles[0].bind();
	tc = tracktiles[0].getImageTexCoords();
	
	for (float x = 5; x > -5; x-=1) {
		
		for (float z = 5; z > -5; z-=1) {
			
			gl.glBegin(GL2.GL_QUADS);
			
			  gl.glTexCoord2f(tc.bottom(), tc.right()); gl.glVertex3f(x, 0.0f, z);
			  gl.glTexCoord2f(tc.bottom(), tc.left()); gl.glVertex3f(x-1, 0.0f, z);
			  gl.glTexCoord2f(tc.top(), tc.left()); gl.glVertex3f(x-1, 0.0f, z-1);
			  gl.glTexCoord2f(tc.top(), tc.right()); gl.glVertex3f(x, 0.0f, z-1);
			
			gl.glEnd();
		}
	}
	
	gl.glDisable(GL2.GL_TEXTURE_2D);
	
	gl.glColor3f(1.0f, 0.0f, 0.0f);
	printText("Zomg text!");
	
	for (UIObject ui : overlays) {
		ui.update();
	}
	UIObject.updateAll(framewidth, frameheight);
  }

  public void dispose(GLAutoDrawable drawable) {
	System.exit(0);
  }

  public void init(GLAutoDrawable drawable) {
	GL2 gl = drawable.getGL().getGL2();
	animator = new FPSAnimator(drawable, 60);
	glu = new GLU();
    
	loadDisplayLists(gl);
	loadTextures(gl);
	loadSky(gl);
	loadModels(gl);
	
	renderer = new TextRenderer(new Font("Times New Roman", Font.TRUETYPE_FONT, 60), true, true);
	
	gl.glEnable(GL2.GL_DEPTH_TEST);
	gl.glDepthFunc(GL2.GL_LEQUAL);
	gl.glShadeModel(GL2.GL_SMOOTH);
	gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
	gl.glClearColor(0f, 0f, 0f, 0f);
	
	gl.glEnable(GL2.GL_LIGHTING);
	gl.glEnable(GL2.GL_LIGHT0);
	gl.glEnable(GL2.GL_COLOR_MATERIAL);
	gl.glColorMaterial(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
	
	gl.glMatrixMode(GL2.GL_PROJECTION);
	gl.glLoadIdentity();
	gl.glMatrixMode(GL2.GL_MODELVIEW);
	gl.glLoadIdentity();
	
	animator.start();
	
	UIObject.initUIObjects(framewidth, frameheight);
	//overlays.add(new LapTimer());
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	GL gl = drawable.getGL();
	gl.glViewport(0, 0, width, height);
  }
}