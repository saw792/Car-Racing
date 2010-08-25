import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
 
import javax.swing.JFrame;
 
 
public class UIObject {
    //THREAD: GRAPHICS/UI
     
    //2D user interface component
    //Holds a certain xy position on the screen (opengl coords not track coords)
    //Has a certain width and height
         
    private float x, y;
    private float w, h; 
    private static Graphics graphics;
    private Canvas canvas;
         
    public UIObject(/*LGraphics gl,*/ float width, float height, float xpos, float ypos) {
        JFrame frame = new JFrame();
        canvas = new Canvas();
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.add(canvas);
        frame.setVisible(true);
        graphics = frame.getGraphics();//gl.getGraphics2D();
        w = width;
        h = height;
        x = xpos;
        y = ypos;
        //RoundRectangle2D ugly = new RoundRectangle2D.Float(5, 5, x, y, w, h);
        canvas.paint(graphics);
        graphics.drawRoundRect((int)x,(int) y,(int) width, (int)height, 5, 5);
    }
         
    public void addRectangle() {
         
        RoundRectangle2D ugly = new RoundRectangle2D.Float(5, 5, x, y, w, h);
    }
         
    public void addCircle() {
             
    }
         
    public void update() {
        canvas.update(graphics);
    }
}