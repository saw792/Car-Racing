/* Victoria Hone */

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;


public class HighScores extends UIObject {
	
	static Image scores;
	static String time1;
    static String time2;
    static String time3;
    static String time4;
    static String time5;
    static String gameTime;
	
	Font high = new Font("sansserif", Font.PLAIN, 20);
	Font time = new Font("sansserif", Font.PLAIN, 18);
	
	static {
		try {
			InputStream high = HighScores.class.getClass().getResourceAsStream("textures/highscore.jpg");
			scores = ImageIO.read(high);
		} catch (IOException e) {}
		
        try {
        	BufferedReader read = new BufferedReader(new FileReader("scores.txt"));
            time1 = read.readLine();
            time2 = read.readLine();
            time3 = read.readLine();
            time4 = read.readLine();
            time5 = read.readLine();
            gameTime = read.readLine();
        }catch (IOException e) {}
	}
	
	public HighScores() {}
	
	public void show(boolean toShow) {
		show = toShow;
	}
  
	public void update() {
		if (show){
			graphics.drawImage(scores, maxwidth/2-300, maxheight/2-200, 600, 400, Color.BLACK, null);
			graphics.setFont(high);
			graphics.setColor(Color.BLACK);
			graphics.drawString(time1, maxwidth/2-250, maxheight/2-113);
			graphics.drawString(time2, maxwidth/2-250, maxheight/2-35);
			graphics.drawString(time3, maxwidth/2-250, maxheight/2-74);
			graphics.drawString(time4, maxwidth/2-60, maxheight/2-107);
			graphics.drawString(time5, maxwidth/2-60, maxheight/2-66);
			graphics.drawString("Menu", maxwidth/2+60, maxheight/2-130);
			graphics.drawString("Play Again", maxwidth/2+160, maxheight/2-130);
			graphics.setFont(time);
			graphics.drawString(gameTime, maxwidth/2+110, maxheight/2+50);
		}
	}

	boolean isShowing() {
		return false;
	}
}
