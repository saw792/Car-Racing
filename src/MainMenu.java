/* Victoria Hone */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class MainMenu extends UIObject {
	
	static Image menu;
	static Image controls;
	static MainMenu instance;
	
	Font title = new Font("sansserif", Font.PLAIN, 30);
	
	private boolean controlshow = false;
	
	static {
		try {
			InputStream menu1 = MainMenu.class.getClass().getResourceAsStream("/textures/menu.jpg");
			InputStream control = MainMenu.class.getClass().getResourceAsStream("/textures/controls.jpg");
			menu = ImageIO.read(menu1);
			controls = ImageIO.read(control);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public MainMenu() {
		instance = this;
	}
  
	public static MainMenu getMenu() {
		return instance;
	}
	
	public void show(boolean toShow) {
		show = toShow;
	}
  
	public void update() {
		if (show){
			if (!controlshow) {
				graphics.drawImage(menu, 0, 0, maxwidth, maxheight, Color.BLACK, null);
				graphics.setFont(title);
				graphics.drawString("Start Game", 500, 200);
				graphics.drawString("Controls", 550, 275);
				graphics.drawString("Highscores", 600, 350);
				graphics.drawString("Track Editor", 650, 425);
			} else {
				graphics.drawImage(controls, 0, 0, maxwidth, maxheight, Color.BLACK, null);
				graphics.drawString("Main Menu", 650, 425);
			}
		}
	}

	boolean isShowing() {
		return false;
	}
	
	public void mouseClick(int x, int y) {
		if (!controlshow && x >= 500 && x <= 700 && y >= 168 && y <= 200) {
			//Start game
			Game.getGame().play();
		} else if (!controlshow && x >= 500 && x <= 700 && y >= 168 && y <= 200) {
			//Controls
			controlshow = true;
		} else if (x >= 650 && x <= 820 && y >= 168 && y <= 200) {
			if (!controlshow) {
				//Track editor
				Game.getGame().trackedit();
			} else {
				controlshow = false;
			}
		}
	}
}