import java.awt.Color;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class MainMenu extends UIObject {
	
  static Image image;
  
  static {
	  try {
		  File f = new File("textures/track/straightedge.jpg");
		  image = ImageIO.read(f);
	  } catch (Exception e) {
	      e.printStackTrace();
	  } 
  }
  
  
  public MainMenu() {}
  
  public void show(boolean toShow) {
	  show = toShow;
  }
  
  public void update() {
	  if (show) {
		 graphics.drawImage(image, 0, 0, maxwidth, maxheight, Color.BLACK, null);
	  }
  }
}
