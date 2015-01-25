package dungeoncrawler.main;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public abstract class Instance {

	public float x,y,z = 0f;
	public Sprite3D sprite = new Sprite3D();
	
	public Vector3f collisionSize = new Vector3f(1,1,1);
	
	public Instance(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void update(){
		tick();
		draw();
	}
	
	public void draw(){


		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		if(sprite.images.size() > 0)
		sprite.draw(sprite.getCurrentImage().getImageWidth(),sprite.getCurrentImage().getImageHeight());
		
		GL11.glTranslatef(-x, -y, -z);
		
		GL11.glPopMatrix();
	}
	
	public abstract void tick();

}
