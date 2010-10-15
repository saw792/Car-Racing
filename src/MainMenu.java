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
			graphics.drawImage(menu, 0, 0, maxwidth, maxheight, Color.BLACK, null);
			graphics.setFont(title);
			graphics.drawString("Start Game", 500, 250);
			graphics.drawString("Controls", 550, 325);
			graphics.drawString("Highscores", 600, 400);
		}

	}

	boolean isShowing() {
		return false;
	}
}