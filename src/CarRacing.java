import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CarRacing {
	
	static {
		String OS = System.getProperty("os.name");
		boolean win = OS.startsWith("Windows");
		
		String[] winnames = {"gluegen-rt.dll", "jogl_desktop.dll", "jogl_es1.dll", "jogl_es2.dll", 
				             "jogl_gl2es12.dll", "nativewindow_awt.dll", "newt.dll"};
		String[] unixnames = {"libgluegen-rt.so", "libjogl_desktop.so", "libnativewindow_awt.so",
				              "libnativewindow_x11.so", "libnewt.so"};
		
		InputStream libstream;
		FileOutputStream output;
		File f;
		
		try {
			if (win) {
				if (!(f = new File(winnames[0])).exists()) {
					for (String path : winnames) {
						libstream = CarRacing.class.getResourceAsStream("/nativelibs/" + path);
						output = new FileOutputStream(path);
						int i;
						while ((i = libstream.read()) != -1) {
							output.write(i);
						}
					}
				}
			} else {
				if (!(f = new File(unixnames[0])).exists()) {
					for (String path : unixnames) {
						libstream = CarRacing.class.getClass().getResourceAsStream("/nativelibs/" + path);
						output = new FileOutputStream(path);
						int i;
						while ((i = libstream.read()) != -1) {
							output.write(i);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
	  Game game = new Game();
	  GLGraphics context = new GLGraphics();
	  Input parser = new Input(context);
	}

}
