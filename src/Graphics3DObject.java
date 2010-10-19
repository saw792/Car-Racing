import javax.media.opengl.GL2;

import com.jogamp.opengl.util.texture.Texture;


public interface Graphics3DObject {

	public void buildList(GL2 gl, Texture[] textures);
	
	public void draw(GL2 gl);
	
	public void clearList(GL2 gl);
}
