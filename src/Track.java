import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;


public class Track extends UIObject implements Graphics3DObject{
  
	//Multiple of 8
	private static final int CELL_SIZE = 32;
	
	private static Font font = new Font("sansserif", Font.PLAIN, 30);
	
	private static Track editor;
	
	private static Image[] tiles;
	private static Image background;
	
	private int[][] tiledata = new int[21][21];
	private int[][] rotdata = new int[21][21];
	private float startx = 0;
	private float starty = 0;
	private float startrot = 0;
	private int selected = -1;
	private int rotation = 0;
	private boolean erase = false;
	            
	private int mousex = 0;
	private int mousey = 0;
	
	private int displayList;
	
	static {
		try {
			InputStream bg = Track.class.getClass().getResourceAsStream("/textures/editor.jpg");
			background = ImageIO.read(bg);
			
			InputStream tile0 = Track.class.getClass().getResourceAsStream("/textures/track/grass.jpg");
			InputStream tile1 = Track.class.getClass().getResourceAsStream("/textures/track/track.jpg");
			InputStream tile2 = Track.class.getClass().getResourceAsStream("/textures/track/straightedge.jpg");
			InputStream tile3 = Track.class.getClass().getResourceAsStream("/textures/track/bigcurvededge.jpg");
			InputStream tile4 = Track.class.getClass().getResourceAsStream("/textures/track/smallcurvededge.jpg");
			InputStream tile5 = Track.class.getClass().getResourceAsStream("/textures/track/finish.jpg");
			InputStream tile6 = Track.class.getClass().getResourceAsStream("/textures/track/finishedge.jpg");
			InputStream tile7 = Track.class.getClass().getResourceAsStream("/textures/track/finishedge.jpg");
			InputStream tile8 = Track.class.getClass().getResourceAsStream("/textures/track/finishedge.jpg");
			
			tiles = new Image[9];
			tiles[0] = ImageIO.read(tile0);
			tiles[1] = ImageIO.read(tile1);
			tiles[2] = ImageIO.read(tile2);
			tiles[3] = ImageIO.read(tile3);
			tiles[4] = ImageIO.read(tile4);
			tiles[5] = ImageIO.read(tile5);
			tiles[6] = ImageIO.read(tile6);
			tiles[7] = ImageIO.read(tile7);
			tiles[8] = ImageIO.read(tile8);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Track(String filepath) {
		BufferedReader reader;
		InputStream input;
		String line;
		String[] cells;
		String[] data;
		
		int x = 0;
		int y = 0;
		int tile = 0;
		int rot = 0;
		
		int startcount = 0;
		
		try {
			 input = Model.class.getClass().getResourceAsStream(filepath);
			 if (input == null) {
				 input = new FileInputStream(filepath);
			 }
			 reader = new BufferedReader(new InputStreamReader(input));
			 while (reader.ready()) {
				 line = reader.readLine();
				 cells = line.split("\\s+", -1);
				 
				 for (x = 0; x <= 20; x++) {
					 data = cells[x].split(":", -1);
					 tile = Integer.parseInt(data[0]);
					 rot = Integer.parseInt(data[1]);
					 
					 if (tile == 5 || tile == 6) {
						 startcount++;
						 startx += x - 10.5;
						 starty += y - 10.5;
						 startrot = rot;
					 }
					 
					 tiledata[x][y] = tile;
					 rotdata[x][y] = rot;
				 }
				 y++;
			 }
			 startx /= (float) startcount;
			 starty /= (float) startcount;
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Track() {
		editor = this;
		int x = 0;
		int y = 0;
		
		for (; y < 21; y++) {
			for (x = 0;x < 21; x++) {
				tiledata[x][y] = 0;
				rotdata[x][y] = 0;
			}
		}
	}
	
	public static Track getActive() {
		return editor;
	}
	
	public void update() {
		if (show) {
			AffineTransform transform = graphics.getTransform();
			graphics.drawImage(background, 0, 0, maxwidth, maxheight, Color.BLACK, null);
			
			//Draw the tiles
			int x = CELL_SIZE;
			int y = CELL_SIZE;
			int end = 768 - CELL_SIZE - CELL_SIZE;
			
			for (; y < end; y += CELL_SIZE) {
				for (x = CELL_SIZE; x < end; x += CELL_SIZE) {
					graphics.translate(x + CELL_SIZE/2, y + CELL_SIZE/2);
					graphics.rotate(rotdata[(x/CELL_SIZE) - 1][(y/CELL_SIZE) - 1] * Math.PI/2.0);
					graphics.drawImage(tiles[tiledata[(x/CELL_SIZE) - 1][(y/CELL_SIZE) - 1]], -CELL_SIZE/2, -CELL_SIZE/2, CELL_SIZE, CELL_SIZE, Color.BLACK, null);
					graphics.setTransform(transform);
				}
			}
			
			//Draw the grid lines
			graphics.setColor(Color.BLACK);
			for (x = CELL_SIZE, y = CELL_SIZE; x <= end; x += CELL_SIZE, y += CELL_SIZE) {
				graphics.drawLine(CELL_SIZE, y, end, y);
				graphics.drawLine(x, CELL_SIZE, x, end);
			}
		
			//Add the button text
			graphics.setColor(Color.RED);
			graphics.setFont(font);
			graphics.drawString("Tiles", 820, 160);
			graphics.drawString("Rotate", 812, 480);
			graphics.drawString("Erase", 816, 544);
			graphics.drawString("Save", 820, 608);
			graphics.drawString("Main Menu", 784, 672);
			
			//Add the Tile Selection area
			int i = 0;
			for (x = 0, y = 0; y <= 128; y += 64) {
				for (x = 0; x <= 128; x += 64, i++) {
					graphics.drawImage(tiles[i], x + 896 - (int) (4 * CELL_SIZE), y + 240, CELL_SIZE, CELL_SIZE, Color.BLACK, null);
				}
			}
			
			//Add the current tile to cursor position
			if (selected != -1) {
			  graphics.translate(mousex, mousey);
			  graphics.rotate(rotation * Math.PI/2.0);
			  graphics.drawImage(tiles[selected], -CELL_SIZE/2, -CELL_SIZE/2, CELL_SIZE, CELL_SIZE, Color.BLACK, null);
			  graphics.setTransform(transform);
			}
		}
	}

	public void mouseClick(int x, int y) {
		if (x >= 808 && x <= 900 && y >= 456 && y <= 480) {
			//Rotate
			if (rotation++ >= 3)
				rotation = 0;
		} else if (x >= 815 && x <= 893 && y >= 520 && y <= 546) {
			//Erase
			selected = -1;
			erase = !erase;
		} else if (x >= 820 && x <= 886 && y >= 582 && y <= 608) {
			//Save
			try {
				BufferedWriter bf = new BufferedWriter(new FileWriter("user.trk"));
				int datax = 0;
				int datay = 0;
				for (; datay < 21; datay++) {
					for (datax = 0; datax < 21; datax++) {
						System.out.println("loop");
						bf.write(tiledata[datax][datay] + ":" + rotdata[datax][datay] + " ");
					}
					bf.newLine();
				}
				bf.flush();
				bf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (x >= 784 && x <= 934 && y >= 650 && y <= 672) {
			//Main Menu
			show = false;
			MainMenu.getMenu().show(true);
		} else if (x >= 768 && x <= 800 && y >= 240 && y <= 272) {
			//Tile Selection
			if (selected != 0)
			  selected = 0;
			else
			  selected = -1;
		} else if (x >= 832 && x <= 864 && y >= 240 && y <= 272) {
			if (selected != 1)
			 selected = 1;
			else
		     selected = -1;
		} else if (x >= 896 && x <= 928 && y >= 240 && y <= 272) {
			if (selected != 2)
			  selected = 2;
			else
			  selected = -1;
		} else if (x >= 768 && x <= 800 && y >= 304 && y <= 336) {
			if (selected != 3)
			  selected = 3;
			else
			  selected = -1;
		} else if (x >= 832 && x <= 864 && y >= 304 && y <= 336) {
			if (selected != 4)
			  selected = 4;
			else
			  selected = -1;
		} else if (x >= 896 && x <= 928 && y >= 304 && y <= 336) {
			if (selected != 5)
			  selected = 5;
			else
			  selected = -1;
		} else if (x >= 768 && x <= 800 && y >= 368 && y <= 400) {
			if (selected != 6)
			  selected = 6;
			else
			  selected = -1;
		} else if (x >= 832 && x <= 864 && y >= 368 && y <= 400) {
			if (selected != 7)
			  selected = 7;
			else
			  selected = -1;
		} else if (x >= 896 && x <= 928 && y >= 368 && y <= 400) {
			if (selected != 8)
			  selected = 8;
			else
			  selected = -1;
		} else if (x >= CELL_SIZE && x <= (768 - CELL_SIZE) && y >= CELL_SIZE && y <= (768 - CELL_SIZE)) {
			//Clicked on grid
			int tilex = (x / CELL_SIZE) - 1;
			int tiley = (y / CELL_SIZE) - 1;
			if (selected != -1) {
				tiledata[tilex][tiley] = selected;
				rotdata[tilex][tiley] = rotation;
			} else if (erase) {
				tiledata[tilex][tiley] = 0;
				rotdata[tilex][tiley] = 0;
			}
		}
	}
	
	public void mouseMove(int x, int y) {
		mousex = x;
		mousey = y;
	}
	
	public void show(boolean toShow) {
		show = toShow;
	}

	public boolean isShowing() {
		return show;
	}
	public void buildList(GL2 gl, Texture[] textures) {
		displayList = gl.glGenLists(1);
		
		gl.glNewList(displayList, GL2.GL_COMPILE);
		
			
			int x = 0;
			int y = 0;
			
			for (; y <= 20; y++) {
				for (x = 0; x <= 20; x++) {
					textures[tiledata[x][y]].bind();
					
					gl.glPushMatrix();
					gl.glTranslatef(x - 10.5f, 0.0f, y - 10.5f);
					gl.glRotatef(rotdata[x][y] * -90, 0.0f, 1.0f, 0.0f);
					gl.glBegin(GL2.GL_QUADS);
					gl.glNormal3f(0.0f, 1.0f, 0.0f);
					gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-0.5f, 0.0f, 0.5f);
					gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-0.5f, 0.0f, -0.5f);
					gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(0.5f, 0.0f, -0.5f);
					gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(0.5f, 0.0f, 0.5f);
					
					
					gl.glEnd();
					gl.glPopMatrix();
				}
			}
			
			
		gl.glEndList();
		System.out.println("Startx: " + startx + " Starty: " + starty + " StartRot: " + startrot);
	}
	public void draw(GL2 gl) {
		gl.glTranslatef(-3 * startx, 0.0f, -3 * starty);
		gl.glRotatef(startrot * 0, 0.0f, 1.0f, 0.0f);
		gl.glPushMatrix();
		gl.glScalef(3.0f, 1.0f, 3.0f);
		gl.glCallList(displayList);
		gl.glPopMatrix();
	}

	public void clearList(GL2 gl) {
		gl.glDeleteLists(displayList, 1);
	}
}
