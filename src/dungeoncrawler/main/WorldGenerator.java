package dungeoncrawler.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import dungeoncrawler.main.scenes.World;

public class WorldGenerator {
	public static int worldSize = 64;
	BufferedImage map = new BufferedImage(worldSize, worldSize, BufferedImage.TYPE_INT_RGB);
	Random random = new Random();
	
	public void generateWorld(World world){
		
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				map.setRGB(x, y, new Color(0,100,0).getRGB());
			}
		}
		
		for(int x = 0; x < map.getWidth(); x++){
			for(int y = 0; y < map.getHeight(); y++){
				Color c = new Color(map.getRGB(x, y));
				int r = c.getRed();
				int g = c.getGreen();
				int b = c.getBlue();
				
				if(random.nextInt(100) == 0){
					int size = random.nextInt(8);
					for(int xx = -size; xx < size; xx++){
						for(int yy = -size; yy < size; yy++){
							if(x+xx >= 0 && x+xx < map.getWidth() && y+yy >= 0 && y+yy < map.getHeight()){
								
								float distance = (float) Math.sqrt((x-x+xx)*(x-x+xx) + (y-y+yy)*(y-y+yy));
								System.out.println(distance);
								if(distance < size){
									System.out.println(distance);
									map.setRGB(x+xx, y+yy, new Color(0,(int)100+size-(int)distance,0).getRGB());
								}
							}
						}
					}
				}
				
			}
		}
		
		for(int x = 0; x < world.chunks.length/16; x++){
			for(int y = 0; y < world.chunks[x].length/16; y++){
				BufferedImage mapPart = map.getSubimage(x*16, y*16, 16, 16);
				world.chunks[x][y] = new Chunk(x*16,y*16,mapPart);
				world.chunks[x][y].generateAir();
			}
		}
		
		
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(map, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
