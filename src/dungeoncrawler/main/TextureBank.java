package dungeoncrawler.main;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureBank {

	public static Texture BRICKS = loadTexture("assets/images/bricks.png");
	public static Texture STONE = loadTexture("assets/images/stane.png");
	public static Texture GRASS = loadTexture("assets/images/grass.png");
	public static Texture DIRT = loadTexture("assets/images/dirt.png");
	public static Texture SAND = loadTexture("assets/images/sand.png");
	
	public static Texture BURGER = loadTexture("assets/images/bak.png");
	
	public static Texture FLOWER = loadTexture("assets/images/flower.png");
	
	public static Texture loadTexture(String path){
		Texture t = null;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			
			t = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(path));
			
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return t;
	}
}
