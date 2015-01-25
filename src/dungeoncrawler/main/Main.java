package dungeoncrawler.main;

import java.awt.Dimension;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import dungeoncrawler.main.scenes.World;

public class Main {

	public static int WIDTH = 640;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 3;
	public static Dimension FRAMESIZE = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	static float depth = 0f;
	
	public static ArrayList<Scene> scenes = new ArrayList<Scene>();
	public static int SCENEINDEX = 0;
	
	private FloatBuffer matSpecular;
	private FloatBuffer lightPosition;
	private FloatBuffer whiteLight; 
	private FloatBuffer lModelAmbient;
	
	float test = 0f;
	
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		try {
			setDisplayMode(FRAMESIZE.width,FRAMESIZE.height,false);
			Display.setTitle("Dungeon Crawler");
			Display.create();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		scenes.add(new World());
		
		while(!Display.isCloseRequested()){
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glColor3f(1, 1, 1);
			GL11.glClearColor(0f, 0f, 0f, 0.0f);
			
			
			Camera camera = getCurrentScene().camera;
			
			GL11.glPushMatrix();
			
			GL11.glRotatef(camera.xrot, 1, 0, 0);
			GL11.glRotatef(camera.yrot, 0, 1, 0);
			GL11.glRotatef(camera.zrot, 0, 0, 1);
			
			
			GL11.glTranslatef(-camera.x, -camera.y, -camera.z);
			
			initGraphics();
			getCurrentScene().update();
			GL11.glTranslatef(camera.x, camera.y, camera.z);
			
			GL11.glPopMatrix();
			
			Display.sync(60);
			Display.update();
		}
	}
	
	private void initGraphics(){
		
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		
		
		
		GLU.gluPerspective(100f, (float)(Display.getWidth()/8) / (Display.getHeight()/8), 0.001f, 1000f);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		
			
		
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
	
	public void setDisplayMode(int width, int height, boolean fullscreen) {
		 
	    // return if requested DisplayMode is already set
	    if ((Display.getDisplayMode().getWidth() == width) && 
	        (Display.getDisplayMode().getHeight() == height) && 
	    (Display.isFullscreen() == fullscreen)) {
	        return;
	    }
	 
	    try {
	        DisplayMode targetDisplayMode = null;
	         
	    if (fullscreen) {
	        DisplayMode[] modes = Display.getAvailableDisplayModes();
	        int freq = 0;
	                 
	        for (int i=0;i<modes.length;i++) {
	            DisplayMode current = modes[i];
	                     
	        if ((current.getWidth() == width) && (current.getHeight() == height)) {
	            if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
	                if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
	                targetDisplayMode = current;
	                freq = targetDisplayMode.getFrequency();
	                        }
	                    }
	 
	            // if we've found a match for bpp and frequence against the 
	            // original display mode then it's probably best to go for this one
	            // since it's most likely compatible with the monitor
	            if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
	                        (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
	                            targetDisplayMode = current;
	                            break;
	                    }
	                }
	            }
	        } else {
	            targetDisplayMode = new DisplayMode(width,height);
	        }
	 
	        if (targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }
	 
	        Display.setDisplayMode(targetDisplayMode);
	        Display.setFullscreen(fullscreen);
	             
	    } catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
	
	private void initLightArrays() {
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1f).put(5f).put(3f).put(1f).flip();
		
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(4f).put(1.0f).put(0f).flip();
		
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(2f).put(2f).put(2f).put(2f).flip();
		
		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
	}
	
	public static Scene getCurrentScene(){
		return scenes.get(SCENEINDEX);
	}
}
