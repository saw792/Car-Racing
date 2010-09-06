import java.awt.Graphics2D;
import com.jogamp.opengl.util.awt.TextureRenderer;
 
public class UIObject {
    //THREAD: GRAPHICS/UI
     
    //2D user interface component
    //Holds a certain xy position on the screen (opengl coords not track coords)
    //Has a certain width and height
         
    private static TextureRenderer tr;
    protected Graphics2D graphics;
    
    protected static int maxwidth;
    protected static int maxheight;
    
    public UIObject() {
    	graphics = tr.createGraphics();
    }
    
    //Designed to be overwritten
    public void update() {}
    
    public static void updateAll(int screenWidth, int screenHeight) {
    	tr.beginOrthoRendering(screenWidth, screenHeight);
    	tr.drawOrthoRect(0, 0);
    	tr.endOrthoRendering();
    }
    
    public static void initUIObjects(int screenWidth, int screenHeight) {
    	maxwidth = screenWidth;
    	maxheight = screenHeight;
    	tr = new TextureRenderer(screenWidth, screenHeight, true);
    }
}