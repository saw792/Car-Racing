
public class Car {
	//THREAD: GAME

  //*******************************
  //Fields	
  //*******************************	
	
  private static final float DEFAULT_COLLISION_RADIUS = 50;	
	
  //Position
  float xpos, ypos, zpos;
  
  //Velocity
  float xvel, yvel, zvel;
  
  //Acceleration
  float xacc, yacc, zacc;
  
  /* Owner/controller of the car
   * 0 = Computer
   * Any other value is a player
   */
  int owner;
 
  /* Distance from the centre of the car
   * that the car will collide with objects
   */
  float collision_radius;
  
  //********************************
  //Constructors
  //********************************
  public Car(int controller, float x, float y, float z) {
	  owner = controller;
	  xpos = x;
	  ypos = y;
	  zpos = z;
	  //etc, zero out remaining values
	  collision_radius = DEFAULT_COLLISION_RADIUS;
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
