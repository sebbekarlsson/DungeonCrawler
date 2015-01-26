package dungeoncrawler.main.instances;

import org.lwjgl.util.vector.Vector3f;

import dungeoncrawler.main.Camera;
import dungeoncrawler.main.Entity;
import dungeoncrawler.main.Main;
import dungeoncrawler.main.TextureBank;

public class Flower extends Entity {

	public Flower(float x, float y, float z) {
		super(x, y, z);
		sprite.images.add(TextureBank.FLOWER);
		collisionSize = new Vector3f(1,0.1f,1);
	}

	@Override
	public void tick() {
		Camera camera = Main.getCurrentScene().camera;
		float angle = (float) Math.toDegrees(Math.atan2(camera.x - x, camera.z - z));

		yrot = angle;
		
	}

}
