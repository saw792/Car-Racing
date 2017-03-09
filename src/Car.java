/*
 * Author: Sean Wild
 */

import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;

public class Car extends TimerTask {    

  //*******************************
  //Fields and variable declarations/initializations  
  //******************************* 
    
  public enum TurnStatus { NOT_TURNING, TURNING_LEFT, TURNING_RIGHT };
  public enum Acceleration { NOT_ACCELERATING, FORWARD, BACKWARDS };
    
  private static final double DEFAULT_COLLISION_RADIUS = 50;
  
  private static final String modelPath = "/models/ferrari.obj";
  
  private static final double drag = 0.9;
  
  private static final double maxangle = 45.0;
  
  private static final int updateRate = 40;
  
  private static final double facingacc = 60.0;
  
  private static final double baseacc = 1.0;
  
  
  
  private static Model model = new Model(modelPath);    //create model using predefined model path
  
  private static Timer timer = new Timer(true);         

  /*
   * Array that holds all the cars in the game. 
   * In this version, there are only 2 cars.
   */
  private static Car[] cars = new Car[2];       
  
  //The following are variables that describe and control the car's behaviour
  private TurnStatus turning = TurnStatus.NOT_TURNING;  //Car begins the simulation heading straight
  
  //Position
  private double xpos = 0, ypos = 0, zpos = 0;
  
  //Velocity
  private double xvel = 0, yvel = 0, zvel = 0;
  
  //Acceleration
  private double xacc = 0, yacc = 0, zacc = 0;
  
  //Direction that the car is facing - represented as an 
  //angle between the car's direction and the positive z-axis
  private double facing = 0, facingvel = 0;
  
  /* Owner/controller of the car
   * 0 = Computer
   * Any other value is a player
   */
  private int owner = 0;
 
  /* Distance from the centre of the car.
   * Any objects that are at or within this distance from the centre 
   * of the car will result in a collision.
   */
  private double collision_radius = DEFAULT_COLLISION_RADIUS;
  
  //********************************
  //Constructors
  //********************************
  
  /* ------------------------------------------
   * Constructs the car at specified location and places the car facing the 
   * correct direction as dictated by parameters passed into the function
     ------------------------------------------*/
  public Car(int controller, double x, double y, double z, double faceAngle) {
      owner = controller;
      xpos = x;
      ypos = y;
      zpos = z;
      facing = faceAngle;
      timer.scheduleAtFixedRate(this , 0, updateRate);
      
      /*
       * We need to be able to identify which car belongs to which owner. 
       * Each car's index number within the 'cars' array is equal to its owner/controller value.
       */
      if (controller == 0 || controller == 1)   
          cars[controller] = this;
  }
  
  //Retrieves the correct car from the 'cars' array for the an owner based on the owner's controller value.
  public static Car getPlayerCar(int controller) {
      return cars[controller];  
  }

  /* ------------------------------------------
   * The following function controls the car's behaviour
     ------------------------------------------*/  
  public void run() {
      double turnacc = 0;       //angular acceleration of the car turning
      double frequency = updateRate/1000.0;     //Frequency at which car behaviour is updated
      //calculate car's direction of travel using trigonometry and car's current velocities
      double veldirection = Math.toDegrees(Math.atan2(-zvel,xvel)); 
      
      if (veldirection < 0)     //if we have a negative angle for direction,
          veldirection += 360;  //then we add 360 degrees until we have a the equivalent angle in the positive domain
      
      /*
       * We need to make sure the car faces the direction it is traveling in.
       * The idea of the following conditionals is that we will eventually have the 
       * car facing the right way when facing=veldirection, at which point neither 
       * of the 'if' statements will be satisfied and no further adjustment is necessary.
       */
      
      if (turning == TurnStatus.NOT_TURNING) {  //In the case that the car is not turning:
          if (facing > veldirection)      //If the angle that the car is facing is greater than the angle of the direction of travel,
              turnacc = 1/-facingacc;         //then we incrementally (at a predefined rate) reduce the angle the car is facing
          if (facing < veldirection)      //If the angle the car is facing is less than the angle of the direction of travel,
              turnacc = 1/facingacc;          //then we incrementally (at a predefined rate) increase the angle the car is facing
          
      } else if (turning == TurnStatus.TURNING_LEFT) {  //In the case that the car is turning left:
        //If there is a big enough discrepancy between the direction the car is facing and the direction the car is going,
          if (facing - veldirection > maxangle) {       
            //then we go back to the previous 'NOT_TURNING' conditional to adjust the directions
              turning = TurnStatus.NOT_TURNING;         
              turnacc = 0;
              facingvel = 0;
          } else               //Otherwise, we turn the car according to the input
              turnacc = facingacc;
      } 
      
      /*
       * The right turn case below is the same thing as the left turn case, 
       * except we need to reverse the 'maxangle' variable because the 
       * discrepancy between the two directions, if there is one, will 
       * be negative due to the direction of the turn
       */
      else if (turning == TurnStatus.TURNING_RIGHT){  
          if (facing - veldirection < -maxangle) {      
              turning = TurnStatus.NOT_TURNING;
              turnacc = 0;
              facingvel = 0;
          } else
              turnacc = -facingacc;
      }
      
      //These two conditionals just make sure that 'facing' is always between 0-360 degrees
      if (facing > 360)
          facing = 0;
      if (facing < 0)
          facing = 360;
      
      //calculates intended velocity in the x direction due to acceleration, 
      //taking into account the effect of drag causing negative acceleration (for realism)
      xvel += (xacc - Math.signum(xvel) * (0.5 * drag * xvel * xvel)) * frequency; 
      xpos += xvel * frequency;         //updates current x position using current x velocity
      //calculates intended velocity in y direction due to acceleration. There is no drag in the y (vertical) direction 
      yvel += yacc * frequency;         
      ypos += yacc * frequency;             //updates current y position using current y velocity
      //calculates intended velocity in z direction, taking into account drag
      zvel += -(zacc - Math.signum(-zvel) *(0.5 * drag * zvel * zvel)) * frequency; 
      zpos += zvel * frequency;             //updates z direction using current z velocity
      facingvel += turnacc * frequency; //updates angular velocity of the direction the car is facing using the angular acceleration
      facing += facingvel * frequency;  //updates the direction the car is facing using angular velocity
      //System.out.println(zvel);
  }
  
  /* ------------------------------------------
   * This function dictates what happens when the keys to the controller are 
   * pressed (and held down). 4 cases for each of the 4 buttons used to control the car
     ------------------------------------------*/
  public void keyPress(int direction) {
      switch (direction) {
        case 1:     //Case for moving in the forward direction
            setXAccel(baseacc * Math.cos(Math.toRadians(facing)));  //Positive acceleration in the x direction
            setZAccel(baseacc * Math.sin(Math.toRadians(facing)));  //Positive acceleration in the z direction
            break;
        case 2:     //Case for moving in the backwards direction 
            setXAccel(-baseacc * Math.cos(Math.toRadians(facing))); //Negative acceleration in the x direction
            setZAccel(-baseacc * Math.sin(Math.toRadians(facing))); //Negative acceleration in the z direction
            break;
        case 3:         //Case for turning left
            turn(1);    //Pass 1 into the 'turn' function
            break;
        case 4:         //Case for turning right
            turn(-1);   //Pass -1 into the 'turn' function
            break;
      }
  }
  
  
  /* ------------------------------------------
   * This function dictates what happens when the keys to the controller are released. 
   * 4 cases for each of the 4 buttons used to control the car
     ------------------------------------------*/
  public void keyRelease(int direction) {
      switch (direction) {
        case 1:             //When the forward key is released, acceleration in the forward direction is set to 0
            setXAccel(0);
            setZAccel(0);
            break;
        case 2:             //When the back key is released, acceleration in the backward direction is set to 0
            setXAccel(0);
            setZAccel(0);
            break;
        case 3:             //When the turn keys are released, 0 is passed into 'turn' which results in no turning acceleration
            turn(0);
            break;
        case 4:
            turn(0);
            break;
      }
  }
  
  /* ------------------------------------------
   * This function dictates which direction the car should turn based on the int passed into the function
     ------------------------------------------*/ 
  private void turn(int direction) {
     switch (direction) {
       case 1:                                  //if direction = 1
           turning = TurnStatus.TURNING_LEFT;       //then turn left
           break;
       case -1:                                 //if direction = -1
           turning = TurnStatus.TURNING_RIGHT;      //then turn right
           break;
       default:                                 //if direction = 0
           turning = TurnStatus.NOT_TURNING;        //then don't turn
     }
  }
  
  /* ------------------------------------------
   * This function draws the actual car onto the track using GL2 (custom class)
     ------------------------------------------*/ 
  public void draw(GL2 gl) {
      gl.glRotatef(90 - (float) facing, 0.0f, 1.0f, 0.0f);      //rotate CCW
      gl.glTranslatef(-(float)xpos, -(float)ypos, -(float)zpos);    //move forward
      gl.glPushMatrix();
      gl.glScalef(0.2f, 0.2f, 0.2f);        //scaling the car down to 0.2f in every direction
      gl.glTranslatef((float)xpos / 0.2f, (float)ypos / 0.2f, (float)zpos / 0.2f);  //move backward
      gl.glRotatef(90 + (float) facing, 0.0f, 1.0f, 0.0f);      //rotate CW
      model.draw(gl);
      gl.glPopMatrix();
  }
  
  //Initialize car models
  public static void modelInit(GL2 gl, Texture[] textures) {
      model.buildList(gl, textures);
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
  double getCollision() {
      return collision_radius;
  }
  
  void setCollision(double collision) {
      collision_radius = collision;
  }
  
  //Get/Set methods for direction the car is facing
  double getFacingAngle() {
      return facing;
  }
  
  void setFacingAngle(double degrees) {
      facing = degrees;
  }
  
  //Get methods for pos, vel, acc in all three axis
  double getXPos() {
      return xpos;
  }
  
  double getYPos() {
      return ypos;
  }
  
  double getZPos() {
      return zpos;
  }
  
  double getXVelocity() {
      return xvel;
  }
  
  double getYVelocity() {
      return yvel;
  }
  
  double getZVelocity() {
      return zvel;
  }
  
  double getXAccel() {
      return xacc;
  }
  
  double getYAccel() {
      return yacc;
  }
  
  double getZAccel() {
      return zacc;
  }
  
  //Set methods for pos, vel, acc
  void setXPos(double newxpos) {
      xpos = newxpos;
  }
  
  void setYPos(double newypos) {
      ypos = newypos;
  }
  
  void setZPos(double newzpos) {
      zpos = newzpos;
  }
  
  void setXVelocity(double newxvel) {
      xvel = newxvel;
  }
  
  void setYVelocity(double newyvel) {
      yvel = newyvel;
  }
  
  void setZVelocity(double newzvel) {
      zvel = newzvel;
  }
  
  void setXAccel(double newxacc) {
      xacc = newxacc;
  }
  
  void setYAccel(double newyacc) {
      yacc = newyacc;
  }
  
  void setZAccel(double newzacc) {
      zacc = newzacc;
  }

}
