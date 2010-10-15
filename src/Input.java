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
     *   Track
     *   MainMenu
     *   HighScores
     *   (PauseMenu)
     *   
     * Classes requiring mouse click events:
     *   Track
     *   MainMenu
     *   HighScores
     *   (PauseMenu)
     * 
     * Classes requiring mouse movement events:
     *   Track
     */
	
	public Input(GLGraphics window) {
		window.getCanvas().addKeyListener(this);
		window.getCanvas().addMouseListener(this);
		window.getCanvas().addMouseMotionListener(this);
	}
	
	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int direction = 0;
        Car c = null;
        
        switch (key) {
        case KeyEvent.VK_UP:
        	c = Car.getPlayerCar(1);
        	direction = 1;
        case KeyEvent.VK_DOWN:
        	c = Car.getPlayerCar(1);
        	direction = 2;
        case KeyEvent.VK_LEFT:
        	c = Car.getPlayerCar(1);
        	direction = 3;
        case KeyEvent.VK_RIGHT:
        	c = Car.getPlayerCar(1);
        	direction = 4;
        case KeyEvent.VK_W:
        	c = Car.getPlayerCar(2);
        	direction = 1;
        case KeyEvent.VK_S:
        	c = Car.getPlayerCar(2);
        	direction = 2;
        case KeyEvent.VK_A:
        	c = Car.getPlayerCar(2);
        	direction = 3;
        case KeyEvent.VK_D:
        	c = Car.getPlayerCar(2);
        	direction = 4;
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
        	c = Car.getPlayerCar(1);
        	direction = 1;
        case KeyEvent.VK_DOWN:
        	c = Car.getPlayerCar(1);
        	direction = 2;
        case KeyEvent.VK_LEFT:
        	c = Car.getPlayerCar(1);
        	direction = 3;
        case KeyEvent.VK_RIGHT:
        	c = Car.getPlayerCar(1);
        	direction = 4;
        case KeyEvent.VK_W:
        	c = Car.getPlayerCar(2);
        	direction = 1;
        case KeyEvent.VK_S:
        	c = Car.getPlayerCar(2);
        	direction = 2;
        case KeyEvent.VK_A:
        	c = Car.getPlayerCar(2);
        	direction = 3;
        case KeyEvent.VK_D:
        	c = Car.getPlayerCar(2);
        	direction = 4;
        }
		if (c != null)
			c.keyRelease(direction);
	}

	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		Track.getActive().mouseClick(x, y);
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
