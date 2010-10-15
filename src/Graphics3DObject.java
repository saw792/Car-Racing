import javax.media.opengl.GL2;


public interface Graphics3DObject {

	public void buildList(GL2 gl);
	
	public void draw(GL2 gl);
	
	public void clearList(GL2 gl);
}
