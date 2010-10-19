import java.awt.Dimension;
import java.util.ArrayList;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import javax.swing.*;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.*;


public class GLGraphics implements GLEventListener{
  private GLU glu;
  private GLCapabilities capabilities;
  private JFrame frame;
  private GLCanvas canvas;
  private FPSAnimator animator;
  private Texture[] textures = new Texture[1];
  
  private int framewidth = 0;
  private int frameheight = 0;
  
  private Texture[] tracktiles = new Texture[9];
  
  private Texture[] skytex = new Texture[5];
  private Texture[] hi_res_sky = new Texture[1];
  private int sky;
  
  private Track currentTrack = null;
  private boolean trackLoaded = false;

  public Car car1 = null;
  public Car car2 = null;
  
  ArrayList<UIObject> overlays = new ArrayList<UIObject>();
  
  
  public GLGraphics() {
	  Dimension d = new Dimension(1024, 768);
	  frame = new JFrame("Car Racing");
	  //Specify initial canvas options
	  capabilities = new GLCapabilities(GLProfile.getDefault());
	  capabilities.setHardwareAccelerated(true);
	  capabilities.setDoubleBuffered(true);
	  capabilities.setRedBits(8);
	  capabilities.setBlueBits(8);
	  capabilities.setGreenBits(8);
	  capabilities.setAlphaBits(8);
	  
	  canvas = new GLCanvas(capabilities);
	  canvas.addGLEventListener(this);
	  
	  frame.add(canvas);
	  frame.setSize(d);
	  
	  framewidth = d.width;
	  frameheight = d.height;
	  
	  frame.setVisible(true);
	  canvas.requestFocus();
  }

  public GLCanvas getCanvas() {
	  return canvas;
  }
  
  private void loadTextures(GL2 gl) {
	  try {
	  textures[0] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/ferrari.png"), false, "png");
	  skytex[0] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/sky/skyfront.jpg"), false, "jpg");
	  skytex[1] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/sky/skyleft.jpg"), false, "jpg");
	  skytex[2] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/sky/skyback.jpg"), false, "jpg");
	  skytex[3] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/sky/skyright.jpg"), false, "jpg");
	  skytex[4] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/sky/skytop.jpg"), false, "jpg");
	  hi_res_sky[0] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/sky/hirestest1.jpg"), false, "jpg");
	  
	  tracktiles[0] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/grass.jpg"), false, "jpg");
	  tracktiles[1] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/track.jpg"), false, "jpg");
	  tracktiles[2] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/straightedge.jpg"), false, "jpg");
	  tracktiles[3] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/bigcurvededge.jpg"), false, "jpg");
	  tracktiles[4] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/smallcurvededge.jpg"), false, "jpg");
	  tracktiles[5] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/finish.jpg"), false, "jpg");
	  tracktiles[6] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/finishedge.jpg"), false, "jpg");
	  tracktiles[7] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/finishedge.jpg"), false, "jpg");
	  tracktiles[8] = TextureIO.newTexture(GLGraphics.class.getClass().getResourceAsStream("/textures/track/finishedge.jpg"), false, "jpg");
	  } catch (Exception e) {
		  System.out.println(e);
	  }
  }
  
  private void loadModels(GL2 gl) {
	  Car.modelInit(gl, textures);
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
  
  public void loadTrack(String filepath) {
	  currentTrack = new Track(filepath);
	  trackLoaded = false;
  }
  
  public void addCars(Car p1, Car p2) {
	car1 = p1;
	car2 = p2;
  }
  
  private void drawSky(GL2 gl) {
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
	  glu.gluLookAt(0.0f, distance * 0.577f, distance, 0, 0, 0, 0, 1, 0);
  }
  
  public void display(GLAutoDrawable drawable) {
	GL2 gl = drawable.getGL().getGL2();
	
	gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
	gl.glLoadIdentity();
	
	setCamera(drawable, gl, glu, 10);
	
	gl.glEnable(GL2.GL_TEXTURE_2D);
	
	if (!trackLoaded && currentTrack != null) {
		currentTrack.buildList(gl, tracktiles);
		trackLoaded = true;
	}
	
	drawSky(gl);
	
	if (car1 != null)
		car1.draw(gl);
	if (car2 != null)
		car2.draw(gl);
	
	if (currentTrack != null) {
		currentTrack.draw(gl);
	}
	
	gl.glDisable(GL2.GL_TEXTURE_2D);
	
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
    
	loadTextures(gl);
	loadSky(gl);
	loadModels(gl);
	
	//renderer = new TextRenderer(new Font("Times New Roman", Font.TRUETYPE_FONT, 60), true, true);
	
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
	
	UIObject.initUIObjects(drawable, framewidth, frameheight);
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	GL gl = drawable.getGL();
	gl.glViewport(0, 0, width, height);
  }
}