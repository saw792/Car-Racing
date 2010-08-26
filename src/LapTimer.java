/*  LapTimer
 * 
 *  25/8/2010
 * 
 *  Victoria Hone
 * 
 *  v1.0.0
 * 
 *  This class generates a lap timer to display on the screen
 *  while the game is running. It provides functions to get the
 *  current lap time, the lap time of the previous race and
 *  format the time for display on the screen.
 * 
 */


import java.util.Calendar;

public class LapTimer extends UIObject {
    //THREAD: GRAPHICS/UI
    private static float width = 200;
    private static float height = 100;
    private static float xpos = 300;
    private static float ypos = 300;
   
    private static long startTime = 0;
    private static long stopTime = 0;
   
   
    private boolean running = false;
       
    public LapTimer() {
        super(width, height, xpos, ypos);
    }
       
    public long getTime() {
        if (running){
            long currentTime = Calendar.getInstance().getTimeInMillis();
            return currentTime - startTime;
        }else {
            return stopTime - startTime;
        }
    }
   
    public void start() {
        running = true;
        startTime = Calendar.getInstance().getTimeInMillis();
    }
       
    public void stop() {
        running = false;
        stopTime = Calendar.getInstance().getTimeInMillis();
    }
   
    public String formatTime(long time){
        long min = time/60000;
        long remainder = time%60000;
        long sec = remainder/1000;
        long tenths = (remainder%1000)/10;
        return (min + ":" + sec + ":" + tenths);
    }
   
    public static void main(String[] args){
        LapTimer timer = new LapTimer();
        System.out.println(timer.formatTime(timer.getTime()));
    }
   
}