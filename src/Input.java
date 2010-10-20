/*
 * Author: Sean Wild
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Input implements KeyListener, MouseListener, MouseMotionListener {
    /*
     * Classes requiring key events:
     *   Car
     *   *Track
     *   HighScores
     *   
     * Classes requiring mouse click events:
     *   *Track
     *   MainMenu
     *   HighScores
     * 
     * Classes requiring mouse movement events:
     *   *Track
     */
	private GLGraphics context;
	
	public Input(GLGraphics window) {
		context = window;
		context.getCanvas().addKeyListener(this);
		context.getCanvas().addMouseListener(this);
		context.getCanvas().addMouseMotionListener(this);
	}
	
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int direction = 0;
        Car c = null;
        
        switch (key) {
        case KeyEvent.VK_UP:
        	c = context.car1;
        	direction = 1;
        	break;
        case KeyEvent.VK_DOWN:
        	c = context.car1;
        	direction = 2;
        	break;
        case KeyEvent.VK_LEFT:
        	c = context.car1;
        	direction = 3;
        	break;
        case KeyEvent.VK_RIGHT:
        	c = context.car1;
        	direction = 4;
        	break;
        case KeyEvent.VK_W:
        	c = context.car2;
        	direction = 1;
        	break;
        case KeyEvent.VK_S:
        	c = context.car2;
        	direction = 2;
        	break;
        case KeyEvent.VK_A:
        	c = context.car2;
        	direction = 3;
        	break;
        case KeyEvent.VK_D:
        	c = context.car2;
        	direction = 4;
        	break;
        }
        if (c != null) 
          c.keyPress(direction);
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		int direction = 0;
		Car c = null;
		
		switch (key) {
        case KeyEvent.VK_UP:
        	c = context.car1;
        	direction = 1;
        	break;
        case KeyEvent.VK_DOWN:
        	c = context.car1;
        	direction = 2;
        	break;
        case KeyEvent.VK_LEFT:
        	c = context.car1;
        	direction = 3;
        	break;
        case KeyEvent.VK_RIGHT:
        	c = context.car1;
        	direction = 4;
        	break;
        case KeyEvent.VK_W:
        	c = context.car2;
        	direction = 1;
        	break;
        case KeyEvent.VK_S:
        	c = context.car2;
        	direction = 2;
        	break;
        case KeyEvent.VK_A:
        	c = context.car2;
        	direction = 3;
        	break;
        case KeyEvent.VK_D:
        	c = context.car2;
        	direction = 4;
        	break;
        }
		if (c != null)
			c.keyRelease(direction);
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		if (Track.getActive().isShowing())
			Track.getActive().mouseClick(x, y);
		if (MainMenu.getMenu().isShowing())
			MainMenu.getMenu().mouseClick(x, y);
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Track.getActive().mouseMove(x, y);
	}

}
