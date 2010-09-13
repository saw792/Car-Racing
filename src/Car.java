import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GL2;


public class Car extends TimerTask {
	//THREAD: GAME

  //*******************************
  //Fields	
  //*******************************	
	
  private static final float DEFAULT_COLLISION_RADIUS = 50;	
  
  private static final String modelPath = "cart-tri.obj";
  
  //Timer timeout, in milliseconds
  private static final int updateRate = 40;
  
  private static Model model = new Model(modelPath);
  
  private static Timer timer = new Timer(true);

  private static Car[] cars = new Car[8];
  //Position
  private float xpos = 0, ypos = 0, zpos = 0;
  
  //Velocity
  private float xvel = 0, yvel = 0, zvel = 0;
  
  //Acceleration
  private float xacc = 0, yacc = 0, zacc = 0;
  
  /* Owner/controller of the car
   * 0 = Computer
   * Any other value is a player
   */
  private int owner = 0;
 
  /* Distance from the centre of the car
   * that the car will collide with objects
   */
  private float collision_radius = DEFAULT_COLLISION_RADIUS;
  
  //********************************
  //Constructors
  //********************************
  public Car(int controller, float x, float y, float z) {
	  owner = controller;
	  xpos = x;
	  ypos = y;
	  zpos = z;
	  timer.scheduleAtFixedRate(this , 0, updateRate);
	  
	  if (controller > 0 && controller <= 8)
		  cars[controller] = this;
  }
  
  public static Car getPlayerCar(int controller) {
	  return cars[controller];
  }
  
  public void run() {
	  xvel += xacc * (updateRate/1000);
	  xpos += xvel * (updateRate/1000);
	  yvel += yacc * (updateRate/1000);
	  ypos += yacc * (updateRate/1000);
	  zvel += zacc * (updateRate/1000);
	  zpos += zvel * (updateRate/1000);
  }
  
  public void draw(GL2 gl) {
	  gl.glPushMatrix();
	  gl.glTranslatef(xpos, ypos, zpos);
	  model.draw(gl);
	  gl.glPopMatrix();
  }
  
  public static void modelInit(GL2 gl) {
	  model.buildList(gl);
  }
  
  //********************************
  //Methods
  //********************************
  
  //Get/Set methods for owner/controller
  int getController() {
	  return owner;
  }
  
  void setController(int controller) {
	  owner = controller;
  }
  
  //Get/Set methods for collision radius
  float getCollision() {
	  return collision_radius;
  }
  
  void setCollision(float collision) {
	  collision_radius = collision;
  }
  
  float getVectorLength() {
	 double length =  Math.sqrt(xvel*xvel + yvel*yvel + zvel*zvel); 
	 return (float) length;  
  }
  
  //Get methods for pos, vel, acc
  float getXPos() {
	  return xpos;
  }
  
  float getYPos() {
	  return ypos;
  }
  
  float getZPos() {
	  return zpos;
  }
  
  float getXVelocity() {
	  return xvel;
  }
  
  float getYVelocity() {
	  return yvel;
  }
  
  float getZVelocity() {
	  return zvel;
  }
  
  float getXAccel() {
	  return xacc;
  }
  
  float getYAccel() {
	  return yacc;
  }
  
  float getZAccel() {
	  return zacc;
  }
  
  //Set methods for pos, vel, acc
  void setXPos(float newxpos) {
	  xpos = newxpos;
  }
  
  void setYPos(float newypos) {
	  ypos = newypos;
  }
  
  void setZPos(float newzpos) {
	  zpos = newzpos;
  }
  
  void setXVelocity(float newxvel) {
	  xvel = newxvel;
  }
  
  void setYVelocity(float newyvel) {
	  yvel = newyvel;
  }
  
  void setZVelocity(float newzvel) {
	  zvel = newzvel;
  }
  
  void setXAccel(float newxacc) {
	  xacc = newxacc;
  }
  
  void setYAccel(float newyacc) {
	  yacc = newyacc;
  }
  
  void setZAccel(float newzacc) {
	  zacc = newzacc;
  }

}
