package dungeoncrawler.main;


import java.util.ArrayList;









public abstract class Scene {

	public ArrayList<Instance> instances = new ArrayList<Instance>();
	public Camera camera = new Camera(0,0,32f);

	public void update(){
		camera.update();
		for(int i = 0; i < instances.size(); i++){
			Instance instance = instances.get(i);
			if(instance != null)
			instance.update();
		}
	}
	
	public void draw(){
		
	}
	public void tick(){
		
	}
	
	public void instantiateInstance(Instance instance){
		instances.add(instance);
	}
	
	public void destroyInstance(Instance instance){
		instances.remove(instance);
	}
	

}
