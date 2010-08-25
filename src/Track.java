
public interface Track {
  //Each track will have different implementations of each method
	//Loads the heightmap of a track from file
	void loadHeightMap(String filepath);
	
	//Returns the start/finish line information
	float getFinishX();
	float getFinishY();
	float getFinishFacing(); //In radians
	
	//Returns the height at 2D coordinates x, y
	float getTerrainHeight(float x, float y);
}
