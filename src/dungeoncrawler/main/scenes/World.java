package dungeoncrawler.main.scenes;




import dungeoncrawler.main.Chunk;
import dungeoncrawler.main.Instance;
import dungeoncrawler.main.Scene;
import dungeoncrawler.main.WorldGenerator;


public class World extends Scene {

	WorldGenerator generator = new WorldGenerator();
	public Chunk[][] chunks = new Chunk[WorldGenerator.worldSize][WorldGenerator.worldSize];

	public World(){
		camera.x = 5;
		camera.z = 9;
		camera.y = 120f;
		
		generator.generateWorld(this);
	}

	public void update(){
		camera.update();
		for(int i = 0; i < instances.size(); i++){
			Instance instance = instances.get(i);
			if(instance != null)
				instance.update();
		}

		for(int x = 0; x < chunks.length; x++){
			for(int z = 0; z < chunks[x].length; z++){
				Chunk chunk = chunks[x][z];
				if(chunk != null){
					if(chunk.isVisible()){
						chunk.update();
					}
				}
			}
		}
		
	}


}
