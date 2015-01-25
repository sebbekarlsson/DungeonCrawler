package dungeoncrawler.main;

import java.util.Random;

import dungeoncrawler.main.instances.Block;
import dungeoncrawler.main.instances.TestModel;

public class Chunk {
	public int x,z = 0;
	public Block[][][] blocks = new Block[16][16][16];
	private boolean init = true;
	Random random = new Random();

	public Chunk(int x, int z){
		this.x = x;
		this.z = z;
	}


	public void update(){
		if(init){
			init();
			init = false;
		}
		for(int x = 0; x < blocks.length; x++){
			for(int y = 0; y < blocks[x].length; y++){
				for(int z = 0; z < blocks[x][y].length; z++){
					Block block = blocks[x][y][z];
					if(block != null){
						block.update();
					}
				}
			}
		}
	}

	public void init(){
		generateAir();
		
	

		for(int x = 0; x < blocks.length; x++){

			for(int z = 0; z < blocks[x].length; z++){
				blocks[x][0][z] = new Block(this.x+x,0,this.z+z,BlockType.STONE,this);
				if(random.nextInt(100) == 0){
					Main.getCurrentScene().instantiateInstance(new TestModel(this.x+x,3,this.z+z));
				}
			}

		}
		
		for(int x = 0; x < blocks.length; x++){

			for(int z = 0; z < blocks[x].length; z++){
				blocks[x][5][z] = new Block(this.x+x,5,this.z+z,BlockType.STONE,this);
			}

		}


	}

	public boolean isVisible(){
		int margin = 8;
		Camera camera = Main.getCurrentScene().camera;
		if(camera.x >= x-margin && camera.x <= x+16+margin && camera.z >= z-margin && camera.z <= z+16+margin){
			return true;
		}
		return false;
	}

	public void generateAir(){
		for(int x = 0; x < blocks.length; x++){
			for(int y = 0; y < blocks[x].length; y++){
				for(int z = 0; z < blocks[x][y].length; z++){
					blocks[x][y][z] = new Block(this.x+x,y,this.z+z,BlockType.AIR,this);

				}
			}
		}

		for(int x = 0; x < blocks.length; x++){
			for(int z = 0; z < blocks[x].length; z++){

				int w = Math.max(4, random.nextInt(16));
				int h = Math.max(4, random.nextInt(16));
				Slice slice = new Slice(x*w,z*h,w,h);
				slice.build();
			
			}
		}
	}

	public class Slice{
		int width;
		int height;
		int x;
		int y;

		public Slice(int x,int y,int width, int height){
			this.x = x;
			this.y = y;

			this.width = width;
			this.height = height;
		}

		public void build(){
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					BlockType type = BlockType.BRICK;
					if(x >= 1 && x < width-1 && y >= 1 && y < height-1){
						type = BlockType.AIR;
					}else{
						for(int h = 1; h < 5; h++)
							blocks[Math.min(15, this.x+x)][h][Math.min(15, this.y+y)].type = type;
					
						if(random.nextInt(3) == 0){
							for(int h = 1; h < 5; h++){
							blocks[Math.min(15, this.x+x)][h][Math.min(15, this.y+y)].type = BlockType.AIR;
							blocks[Math.min(15, this.x+x+1)][h][Math.min(15, this.y+y+1)].type = BlockType.AIR;
							}
						}
					}
					
				}
			}
			
			
			
			
		}
	}

}
