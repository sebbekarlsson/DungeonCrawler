package dungeoncrawler.main;

import org.lwjgl.util.vector.Vector3f;

import dungeoncrawler.main.instances.Block;
import dungeoncrawler.main.instances.TestModel;
import dungeoncrawler.main.scenes.World;



public abstract class Entity extends Instance {

	public float dx,dy,dz = 0;
	public float friction = 0.01f;
	public float frictionY = 0f;
	public boolean onFloor = false;
	public float weight = 0.025f;


	public Entity(float x, float y, float z) {
		super(x, y, z);
		// TODO Auto-generated constructor stub
	}


	public void update(){
		if(isVisible()){
			updatePhysics();
			tick();
			draw();
		}
	}

	public void updatePhysics(){
		x += dx;
		y += dy;
		z += dz;

		onFloor = false;

		Chunk chunk = getChunk();
		if(chunk != null){


			for(int x = 0; x < chunk.blocks.length; x++){
				for(int y = 0; y < chunk.blocks[x].length; y++){
					for(int z = 0; z < chunk.blocks[x][y].length; z++){
						Block block = chunk.blocks[x][y][z];
						if(block != null && !(block.type.equals(BlockType.AIR))){
							if(this.y >= block.y && this.y-collisionSize.y <= block.y+1){
								if(this.x >= block.x && this.x <= block.x+1){
									if(this.z >= block.z && this.z <= block.z+1){
										if(this instanceof TestModel){
											block.type = BlockType.GRASS;
										}
										onFloor = true;
										addForce(new Vector3f(0,-dy,0));
									}
								}
							}
						}
					}
				}
			}

		}




		if(dx < 0){
			if(dx + friction > 0){
				dx = 0;
			}else{
				dx += friction;
			}
		}

		if(dx > 0){
			if(dx - friction < 0){
				dx = 0;
			}else{
				dx -= friction;
			}
		}


		if(dz < 0){
			if(dz + friction > 0){
				dz = 0;
			}else{
				dz += friction;
			}
		}

		if(dz > 0){
			if(dz - friction < 0){
				dz = 0;
			}else{
				dz -= friction;
			}
		}


		if(dy < 0){
			if(dy + frictionY > 0){
				dy = 0;
			}else{
				dy += frictionY;
			}
		}

		if(dy > 0){
			if(dy - frictionY < 0){
				dy = 0;
			}else{
				dy -= frictionY;
			}
		}

		if(onFloor == false){
			addForce(new Vector3f(0,-weight,0));
		}
	}

	public Chunk getChunk(){
		if(Main.getCurrentScene() instanceof World){
			World world = (World) Main.getCurrentScene();


			return world.chunks[(int) ((x/16)%WorldGenerator.worldSize)][(int) ((z/16)%WorldGenerator.worldSize)];
		}
		return null;
	}


	public void addForce(Vector3f v){
		dx += v.x;
		dy += v.y;
		dz += v.z;
	}

	public boolean isVisible(){
		Camera camera = Main.getCurrentScene().camera;
		float distance = (float) Math.sqrt((x-camera.x)*(x-camera.x) + (z-camera.z)*(z-camera.z));
		return (distance < 16+8);
	}

}
