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
        float accel = 0.001f;
        Car c = Car.getPlayerCar(1);
        
        switch (key) {
        case KeyEvent.VK_UP:
        	c.setXAccel(c.getXAccel() + accel * (c.getXVelocity()/c.getVectorLength()));
        	c.setYAccel(c.getYAccel() + accel * (c.getYVelocity()/c.getVectorLength()));
        	c.setZAccel(c.getZAccel() + accel * (c.getZVelocity()/c.getVectorLength()));
            break;
        case KeyEvent.VK_DOWN:
        	c.setXAccel(c.getXAccel() - accel * (c.getXPos()/c.getVectorLength()));
        	c.setYAccel(c.getYAccel() - accel * (c.getYPos()/c.getVectorLength()));
        	c.setZAccel(c.getZAccel() - accel * (c.getZPos()/c.getVectorLength()));
        	break;
        case KeyEvent.VK_LEFT:
        	//cam_angle = -3.6f;
        	break;
        case KeyEvent.VK_RIGHT:
        	//cam_angle = 3.6f;
        	break;
        }
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
