package dungeoncrawler.main;

import java.awt.Dimension;
import java.util.ArrayList;



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
			GL11.glClearColor(46/255f, 100/255f, 170/255f, 1.0f);
			
			
			Camera camera = getCurrentScene().camera;
			
			GL11.glPushMatrix();
			
			GL11.glRotatef(camera.xrot, 1, 0, 0);
			GL11.glRotatef(camera.yrot, 0, 1, 0);
			GL11.glRotatef(camera.zrot, 0, 0, 1);
			
			 
			GL11.glTranslatef(-camera.x, -camera.y, -camera.z);
			
			getCurrentScene().update();
			
			GL11.glTranslatef(camera.x, camera.y, camera.z);
			
			GL11.glPopMatrix();
			
			Display.sync(60);
			Display.update();
		}
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
	
	
	
	public static Scene getCurrentScene(){
		return scenes.get(SCENEINDEX);
	}
}
