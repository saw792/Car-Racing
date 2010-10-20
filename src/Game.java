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
	
	private static Game instance;
	
	public static Game getGame() {
		return instance;
	}
	
	public Game(GLGraphics context) {
		instance = this;
		ArrayList<UIObject> ui = context.overlays;
		
		menu = new MainMenu();
		timer1 = new LapTimer();
		timer2 = new LapTimer();
		scores = new HighScores();
		editor = new Track();
		ui.add(menu);
		ui.add(timer1);
		ui.add(timer2);
		ui.add(scores);
		ui.add(editor);
		menu.show(true);
		
		context.loadTrack("/tracks/track1.trk");
		
		context.addCars(car1, car2);
	}
	
	public void play() {
		menu.show(false);
		car1 = new Car(0, 0.0, 0.0, 0.0, 90.0);
		timer1.start();
	}
	
	public void trackedit() {
		menu.show(false);
		editor.show(true);
	}
}
