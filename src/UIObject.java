import java.awt.Graphics2D;

import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.awt.TextureRenderer;
import com.jogamp.opengl.util.awt.Overlay;
 
abstract class UIObject {
    //THREAD: GRAPHICS/UI
     
    //2D user interface component
    //Holds a certain xy position on the screen (opengl coords not track coords)
    //Has a certain width and height
    
    private static Overlay ov;
    protected static Graphics2D graphics;
    
    protected static int maxwidth;
    protected static int maxheight;
    protected boolean show = false;
    
    //Designed to be overridden
    abstract void update();
    
    abstract void show(boolean toShow);
    
    public static void updateAll(int screenWidth, int screenHeight) {
    	ov.drawAll();
    	ov.markDirty(0, 0, maxwidth, maxheight);
    	graphics.dispose();
    	graphics = ov.createGraphics();
    }
    
    public static void initUIObjects(GLAutoDrawable drawable, int screenWidth, int screenHeight) {
    	maxwidth = screenWidth;
    	maxheight = screenHeight;
    	ov = new Overlay(drawable);
    	graphics = ov.createGraphics();
    }
}