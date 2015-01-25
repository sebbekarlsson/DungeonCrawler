package dungeoncrawler.main.instances;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import dungeoncrawler.main.Camera;
import dungeoncrawler.main.Entity;
import dungeoncrawler.main.Main;
import dungeoncrawler.main.TextureBank;

public class TestModel extends Entity {
	float rot = 0;
	float rotspeed = 0;
	Random random = new Random();
	
	float spawnx,spawny,spawnz = 0;
	
	public TestModel(float x, float y, float z) {
		super(x, y, z);
		friction = 0.005f;
		spawnx = x;
		spawny = y;
		spawnz = z;
		sprite.images.add(TextureBank.BURGER);
		collisionSize = new Vector3f(1,1,1);
	}


		
		
	

	@Override
	public void tick() {
		Camera camera = Main.getCurrentScene().camera;
		float angle = (float) Math.toDegrees(Math.atan2(camera.x - x, camera.z - z));
		float distance = (float) Math.sqrt((x-camera.x)*(x-camera.x) + (z-camera.z)*(z-camera.z));
		rot = angle;
		if(onFloor && distance < 10){
			if(random.nextInt(40) == 0){
				
				addForce(new Vector3f((float)Math.cos(Math.toRadians(rot-90))*0.1f,random.nextFloat()*0.4f,(float)-Math.sin(Math.toRadians(rot)-90)*0.1f));
			}
		}
		
		if(y < -5f){
			x = spawnx;
			y = spawny;
			z = spawnz;
		}
	}
	
	public void draw(){
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rot, 0, 1, 0);
		GL11.glRotatef(180, 0, 0, 1);
		sprite.draw(1,1);
		
		GL11.glTranslatef(-x, -y, -z);
		
		GL11.glPopMatrix();
		
		rot += rotspeed;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_2)){
			rotspeed += 0.5f;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_1)){
			rotspeed -= 0.5f;
		}
	}

}
