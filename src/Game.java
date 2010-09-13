
public class Game {
 //Contains the main game loop
 //Passes state changes through to physics to manipulate cars
 //Separate thread to Graphics/UI loop
	
	public Game() {
		Car player1 = new Car(1, 0.0f, 0.0f, 0.0f);
	}
	
	//Game loop
	public void start(){}
	
	public void destroy(){}
}
