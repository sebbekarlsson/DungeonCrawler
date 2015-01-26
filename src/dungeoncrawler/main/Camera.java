package dungeoncrawler.main;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

import dungeoncrawler.main.instances.Block;
import dungeoncrawler.main.instances.TestModel;

public class Camera extends Entity {

	public float xrot, yrot, zrot = 0f;
	float speed = 0.1f;
	Random random = new Random();
	
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight; 
	private FloatBuffer lModelAmbient;

	public Camera(float x, float y, float z) {
		super(x, y, z);
		friction = 0.05f;
		collisionSize = new Vector3f(1,1f,1);
	}
	
	public void update(){
		
		initGraphics();
		
		updatePhysics();
		tick();
		draw();
	}

	@Override
	public void tick() {


		if(Keyboard.isKeyDown(Keyboard.KEY_W)){

			Block blockMiddle;
			Block blockHead;

			blockMiddle = getChunk().blocks[(int) (x+ speed * Math.sin(Math.toRadians(yrot)))%16][(int) (y)%256][(int) (z- speed * Math.cos(Math.toRadians(yrot)))%16];
			blockHead = getChunk().blocks[(int) (x+ speed * Math.sin(Math.toRadians(yrot)))%16][(int) (y+1)%256][(int) (z- speed * Math.cos(Math.toRadians(yrot)))%16];


			if(blockMiddle.type.equals(BlockType.AIR) && blockHead.type.equals(BlockType.AIR) ){

				x += speed * Math.sin(Math.toRadians(yrot));
				z -= speed * Math.cos(Math.toRadians(yrot));
			}


			System.out.println(getChunk());

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
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
			
			addForce(new Vector3f(0,0.2f,0));
			
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_C)){
			getChunk().init();
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_V)){
			Main.getCurrentScene().instantiateInstance(new TestModel(x,y+1,z));
		}


		xrot -= Mouse.getDY();
		yrot += Mouse.getDX();


	}
	
	private void initGraphics(){
		
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		
		GL11.glLoadIdentity();
		
		
		
		GLU.gluPerspective(100f, (float)(Display.getWidth()/8) / (Display.getHeight()/8), 0.001f, 1000f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST); 
		
		initLightArrays();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_SPECULAR, matSpecular);				// sets specular material color
		GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 50.0f);					// sets shininess
		
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPosition);				// sets light position
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_SPECULAR, whiteLight);				// sets specular light to white
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_DIFFUSE, whiteLight);					// sets diffuse light to white
		GL11.glLightModel(GL11.GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light 
		
		GL11.glEnable(GL11.GL_LIGHTING);										// enables lighting
		GL11.glEnable(GL11.GL_LIGHT0);										// enables light0
		
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
		GL11.glColorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
	}
	
	private void initLightArrays() {
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(0f).put(2f).put(3f).put(0f).flip();
		
		
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(0.0f).put(2f).put(3.0f).put(0f).flip();
		
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(2f).put(2f).put(2f).put(2f).flip();
		
		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
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
