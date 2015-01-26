package dungeoncrawler.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

import dungeoncrawler.main.instances.Block;
import dungeoncrawler.main.instances.Flower;


public class Chunk {
	public int x,z = 0;
	public Block[][][] blocks = new Block[16][256][16];
	private boolean init = true;
	public BufferedImage img;
	Random random = new Random();

	public Chunk(int x, int z, BufferedImage img){
		this.x = x;
		this.z = z;
		this.img = img;
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

		for(int x = 0; x < img.getWidth(); x++){

			for(int z = 0; z < img.getHeight(); z++){
				Color color = new Color(img.getRGB(x, z));
				int r = color.getRed();
				int g = color.getGreen();
				int b = color.getBlue();
				
				blocks[x][g][z].type = BlockType.GRASS;
			
				for(int depth = g-1; depth > 50; depth -=1){
					blocks[x][depth][z].type = BlockType.GRASS;
				}
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

	}

	

}
