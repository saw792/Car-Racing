import java.util.ArrayList;


public class Game {
	
	public Game() {
		ArrayList<UIObject> ui = GLGraphics.overlays;
		MainMenu menu1 = new MainMenu();
		ui.add(menu1);
		
		menu1.show(true);
		
		Car player1 = new Car(1, 0.0, 0.0, 0.0, 90.0);
	}
	
	public void start(){}
	
	public void destroy(){}
}
