package dungeoncrawler.main;


import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Sprite3D {
	public ArrayList<Texture> images = new ArrayList<Texture>();
	public int IMAGEINDEX = 0;

	public void tick(){}
	public void draw(float width, float height){
		GL11.glPushMatrix();
		if(images.size() > 0){

			Texture texture = getCurrentImage();

			if(texture != null){
			
				texture.bind();
				
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glBegin(GL11.GL_QUADS);

				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0,0,0);

				GL11.glTexCoord2f(0, texture.getHeight());
				GL11.glVertex3f(0,height,0);

				GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
				GL11.glVertex3f(width,height,0);

				GL11.glTexCoord2f(texture.getWidth(), 0);
				GL11.glVertex3f(width,0,0);

				GL11.glEnd();

			}

		}
	
		GL11.glPopMatrix();
	}

	public Texture getCurrentImage(){
		return images.get(IMAGEINDEX);
	}
}
