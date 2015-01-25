package dungeoncrawler.main.instances;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import dungeoncrawler.main.BlockType;
import dungeoncrawler.main.Camera;
import dungeoncrawler.main.Chunk;
import dungeoncrawler.main.Instance;
import dungeoncrawler.main.Main;

public class Block extends Instance {

	public BlockType type;
	public Chunk chunk;
	public Color color = Color.WHITE;


	public Block(float x, float y, float z, BlockType type, Chunk chunk) {
		super(x, y, z);
		this.type = type;
		this.chunk = chunk;
	}

	public void draw(){


		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, z);


		GL11.glEnable(GL11.GL_TEXTURE_2D);


		if(type.top != null && type.bottom != null && type.front != null && type.left != null && type.right != null){
			GL11.glColor3f(color.getRed()/255f, color.getGreen()/255f, color.getBlue()/255f);
			type.top.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glNormal3f(0.0f,  1.0f, 0.0f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(0,0f,0);

			GL11.glTexCoord2f(0, 1f);
			GL11.glVertex3f(0,0f,1f);

			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex3f(1f,0f,1f);

			GL11.glTexCoord2f(1f, 0);
			GL11.glVertex3f(1f,0f,0);

			GL11.glEnd();

			type.bottom.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glNormal3f(0.0f, -1.0f, 0.0f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(0,-1f,0);

			GL11.glTexCoord2f(0, 1f);
			GL11.glVertex3f(0,-1f,1f);

			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex3f(1f,-1f,1f);

			GL11.glTexCoord2f(1f, 0);
			GL11.glVertex3f(1f,-1f,0);

			GL11.glEnd();


			type.left.bind();
			GL11.glBegin(GL11.GL_QUADS);
			
			GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(0,0f,0);

			GL11.glTexCoord2f(1f, 0);
			GL11.glVertex3f(1f,0f,0);

			GL11.glTexCoord2f(1f, -1f);
			GL11.glVertex3f(1f,-1f,0);

			GL11.glTexCoord2f(0, -1f);
			GL11.glVertex3f(0,-1f,0);



			GL11.glEnd();


			type.right.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glNormal3f(1.0f, 0.0f, 0.0f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(0,0f,1f);

			GL11.glTexCoord2f(1f, 0);
			GL11.glVertex3f(1f,0f,1f);

			GL11.glTexCoord2f(1f, -1f);
			GL11.glVertex3f(1f,-1f,1f);

			GL11.glTexCoord2f(0, -1f);
			GL11.glVertex3f(0,-1f,1f);



			GL11.glEnd();


			type.back.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glNormal3f(0.0f, 0.0f, -1.0f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(0,0f,0);

			GL11.glTexCoord2f(0, 1f);
			GL11.glVertex3f(0,0f,1f);

			GL11.glTexCoord2f(-1f, 1f);
			GL11.glVertex3f(0,-1f,1f);

			GL11.glTexCoord2f(-1f, 0);
			GL11.glVertex3f(0,-1f,0f);



			GL11.glEnd();


			type.back.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(1f,0f,0);
			GL11.glNormal3f(1f,0f,0f);

			GL11.glTexCoord2f(0, 1f);
			GL11.glVertex3f(1f,0f,1f);
			GL11.glNormal3f(1f,0f,1f);

			GL11.glTexCoord2f(-1f, 1f);
			GL11.glVertex3f(1f,-1f,1f);
			GL11.glNormal3f(1f,-1f,1f);

			GL11.glTexCoord2f(-1f, 0);
			GL11.glVertex3f(1f,-1f,0f);
			GL11.glNormal3f(1f,-1f,0f);



			GL11.glEnd();
		}

		GL11.glTranslatef(-x, -y, -z);

		GL11.glPopMatrix();
		


	}
	
	public float getCameraDistance(){
		Camera camera = Main.getCurrentScene().camera;
		float distance = (float) Math.sqrt((x-camera.x)*(x-camera.x) + (z-camera.z)*(z-camera.z));
		return distance;
	}


	@Override
	public void tick() {
		float brightness = Math.max(0, 255-(getCameraDistance()*25));
		color = new Color(brightness/255f,brightness/255f,brightness/255f);

	}

}
