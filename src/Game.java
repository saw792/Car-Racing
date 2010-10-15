import java.util.ArrayList;


public class Game {
	
	private ArrayList<UIObject> ui;
	private MainMenu menu;
	private LapTimer timer1;
	private LapTimer timer2;
	private HighScores scores;
	private Track editor;
	
	private Car car1;
	private Car car2;
	
	public Game() {
		ArrayList<UIObject> ui = GLGraphics.overlays;
		menu = new MainMenu();
		ui.add(menu);
		//menu.show(true);
		editor = new Track();
		ui.add(editor);
		editor.show(true);
		
		car1 = new Car(0, -2.0, 0.0, 0.0, 90.0);
		car2 = new Car(1, 2.0, 0.0, 0.0, 90.0);
	}
	
	public void play() {
		timer1 = new LapTimer();
		timer2 = new LapTimer();
		
		ui.add(timer1);
		ui.add(timer2);
		
		car1 = new Car(0, -2.0, 0.0, 0.0, 90.0);
		car2 = new Car(1, 2.0, 0.0, 0.0, 90.0);
	}
	
}
