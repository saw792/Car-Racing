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

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class LapTimer extends UIObject {
    
	//THREAD: GRAPHICS/UI
    private static int width = 100;
    private static int height = 50;
    private static int xpos = 0;
    private static int ypos = 0;
    private static int buffer = 30;
    
       
    private long startTime = 0;
    private long stopTime = 0;
   
   
    private boolean running = false;
     
    public void show(boolean toShow) {
    	show = toShow;
    }
    
    public LapTimer() {
    	xpos = maxwidth - (width + buffer);
    	ypos = buffer;
    }

	public String getTime() {
        if (running){
            long currentTime = Calendar.getInstance().getTimeInMillis();
            return formatTime(currentTime - startTime);
        }else {
            return formatTime(stopTime - startTime);
        }
    }
	
    public void start() {
        running = true;
        startTime = Calendar.getInstance().getTimeInMillis();
    }
    
    public void stop() {
        running = false;
        stopTime = Calendar.getInstance().getTimeInMillis();
                
        String time1 = null;
        String time2 = null;
        String time3 = null;
        String time4 = null;
        String time5 = null;
        String gameTime = formatTime(stopTime-startTime);
        
        try {
        	BufferedReader read = new BufferedReader(new FileReader("scores.txt"));
            time1 = read.readLine();
            time2 = read.readLine();
            time3 = read.readLine();
            time4 = read.readLine();
            time5 = read.readLine();
        }catch (IOException e) {}
        
        BufferedWriter hightime = null;
        try {
        	hightime = new BufferedWriter(new FileWriter("scores.txt"));
        	if ((stopTime-startTime) > Long.parseLong(time1)){
        		hightime.write(formatTime(stopTime - startTime));
        		hightime.newLine();
        		hightime.write(time1);
        		hightime.newLine();
        		hightime.write(time2);
        		hightime.newLine();
        		hightime.write(time3);
        		hightime.newLine();
        		hightime.write(time4);
        		hightime.newLine();
        		hightime.write(gameTime);
        	}
        	if ((stopTime-startTime) > Long.parseLong(time2)){
        		hightime.write(time1);
        		hightime.newLine();
        		hightime.write(formatTime(stopTime - startTime));
        		hightime.newLine();
        		hightime.write(time2);
        		hightime.newLine();
        		hightime.write(time3);
        		hightime.newLine();
        		hightime.write(time4);
        		hightime.newLine();
        		hightime.write(gameTime);
        	}
        	if ((stopTime-startTime) > Long.parseLong(time3)){
        		hightime.write(time1);
        		hightime.newLine();
        		hightime.write(time2);
        		hightime.newLine();
        		hightime.write(formatTime(stopTime - startTime));
        		hightime.newLine();
        		hightime.write(time3);
        		hightime.newLine();
        		hightime.write(time4);
        		hightime.newLine();
        		hightime.write(gameTime);
        	}
        	if ((stopTime-startTime) > Long.parseLong(time4)){
        		hightime.write(time1);
        		hightime.newLine();
        		hightime.write(time2);
        		hightime.newLine();
        		hightime.write(time3);
        		hightime.newLine();
        		hightime.write(formatTime(stopTime - startTime));
        		hightime.newLine();
        		hightime.write(time4);
        		hightime.newLine();
        		hightime.write(gameTime);
        	}
        	if ((stopTime-startTime) > Long.parseLong(time5)){
        		hightime.write(time1);
        		hightime.newLine();
        		hightime.write(time2);
        		hightime.newLine();
        		hightime.write(time3);
        		hightime.newLine();
        		hightime.write(time4);
        		hightime.newLine();
        		hightime.write(formatTime(stopTime - startTime));
        		hightime.newLine();
        		hightime.write(gameTime);
        	}
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (hightime != null) {
                    hightime.flush();
                    hightime.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	public void update() {
		if (show) {
			graphics.setColor(Color.BLACK);
			graphics.fillRoundRect(xpos, ypos, width, height, 5, 5);
			graphics.setColor(Color.WHITE);
			graphics.drawRoundRect(xpos, ypos, width, height, 5, 5);
			graphics.drawString("Lap Timer", ((xpos + width/2) - ("Lap Timer".length()*	6)/2), ypos + height/3);
			graphics.drawString(getTime(), ((xpos + width/2) - (getTime().length()*6)/2) , ypos + height - 10 );
		}
    }
    
    private String formatTime(long time){
        long min = time/60000;
        long remainder = time%60000;
        long sec = remainder/1000;
        long tenths = (remainder%1000)/10;
        return (min + ":" + sec + ":" + tenths);
    }

	boolean isShowing() {
		return false;
	}
   
}