package dungeoncrawler.main;

import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import dungeoncrawler.main.instances.Block;
import dungeoncrawler.main.instances.TestModel;

public class Camera extends Entity {

	public float xrot, yrot, zrot = 0f;
	float speed = 0.1f;
	Random random = new Random();

	public Camera(float x, float y, float z) {
		super(x, y, z);
		friction = 0.05f;
		collisionSize = new Vector3f(1,1f,1);
	}

	@Override
	public void tick() {


		if(Keyboard.isKeyDown(Keyboard.KEY_W)){

			Block blockMiddle;
			Block blockHead;

			blockMiddle = getChunk().blocks[(int) (x+ speed * Math.sin(Math.toRadians(yrot)))%16][(int) (y)%16][(int) (z- speed * Math.cos(Math.toRadians(yrot)))%16];
			blockHead = getChunk().blocks[(int) (x+ speed * Math.sin(Math.toRadians(yrot)))%16][(int) (y+1)%16][(int) (z- speed * Math.cos(Math.toRadians(yrot)))%16];


			if(blockMiddle.type.equals(BlockType.AIR) && blockHead.type.equals(BlockType.AIR) ||(blockMiddle.type.equals(null) || blockHead.type.equals(null))){
				x += speed * Math.sin(Math.toRadians(yrot));
				z -= speed * Math.cos(Math.toRadians(yrot));
			}




			//y += (float)Math.tan(Math.toRadians(xrot));

		}

		if(Keyboard.isKeyDown(Keyboard.KEY_R)){
			xrot = 0;
			yrot = 0;
			zrot = 0;

		}

		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			if(onFloor){
				addForce(new Vector3f(0,0.2f,0));
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_C)){
			getChunk().init();
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_V)){
			Main.getCurrentScene().instantiateInstance(new TestModel(x,y+1,z));
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_X)){
			BlockType t = BlockType.AIR;
			while(t.equals(BlockType.AIR)){
				t = BlockType.values()[random.nextInt(BlockType.values().length)];
			}


			if(getFacingBlock() != null){
				Block block = getFacingBlock();
				block.type = BlockType.AIR;
			}

		}


		xrot -= Mouse.getDY();
		yrot += Mouse.getDX();


	}

	public boolean isMoving(){
		if(dx < 0 || dx > 0 || dz > 0 || dz < 0){
			return true;
		}
		return false;
	}

	public Block getFacingBlock(){
		for(int i = 0; i < 16; i++){
			float xx = (float) ((x)+Math.sin(Math.toRadians(yrot))*i)%16;
			float yy = (float) ((y)-Math.tan(Math.toRadians(xrot))*i)%16;
			float zz = (float) ((z)-Math.cos(Math.toRadians(yrot))*i)%16;

			if(xx > 0 && xx < 16 && zz > 0 && zz < 16 && yy > 0 && yy < 16){

				Block b = getChunk().blocks

						[(int) xx]
								[(int) yy]
										[(int) zz];

				if(b != null && !b.type.equals(BlockType.AIR)){return b;}
			}
		}
		return null;
	}

}
