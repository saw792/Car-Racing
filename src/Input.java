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
        double accel = 1.0;
        double turnrate = 60.0;
        Car c = Car.getPlayerCar(1);
        
        switch (key) {
        case KeyEvent.VK_UP:
        	c.setXAccel(accel * Math.cos(Math.toRadians(c.getFacingAngle())));
        	c.setZAccel(-accel * Math.sin(Math.toRadians(c.getFacingAngle())));
            break;
        case KeyEvent.VK_DOWN:
        	c.setXAccel(-accel * Math.cos(Math.toRadians(c.getFacingAngle())));
        	c.setZAccel(accel * Math.sin(Math.toRadians(c.getFacingAngle())));
        	break;
        case KeyEvent.VK_LEFT:
        	c.setFacingAcc(turnrate);
        	break;
        case KeyEvent.VK_RIGHT:
        	c.setFacingAcc(-turnrate);
        	break;
        }
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		Car c = Car.getPlayerCar(1);
		
		switch (key) {
        case KeyEvent.VK_UP:
        	c.setXAccel(0);
        	c.setZAccel(0);
            break;
        case KeyEvent.VK_DOWN:
        	c.setXAccel(0);
        	c.setZAccel(0);
        	break;
        case KeyEvent.VK_LEFT:
        	c.setFacingAcc(0);
        	break;
        case KeyEvent.VK_RIGHT:
        	c.setFacingAcc(0);
        	break;
        }
	}

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
