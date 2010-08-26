import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input implements KeyListener, MouseListener {

	GLGraphics graphics;
	
	public Input(GLGraphics window) {
		window.getCanvas().addKeyListener(this);
		graphics = window;
	}
	
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        float cam_distance = 0.0f;
        float cam_angle = 0.0f;
        
        switch (key) {
        case KeyEvent.VK_UP:
        	cam_distance = -0.1f;
            break;
        case KeyEvent.VK_DOWN:
        	cam_distance = 0.1f;
        	break;
        case KeyEvent.VK_LEFT:
        	cam_angle = -3.6f;
        	break;
        case KeyEvent.VK_RIGHT:
        	cam_angle = 3.6f;
        	break;
        }
        
        graphics.cameraZoom(cam_distance);
        graphics.cameraRotate(cam_angle);
	}

	public void keyReleased(KeyEvent e) {}

	public void mouseClicked(MouseEvent e) {

	}


	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

}
