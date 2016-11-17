package window.game_engine.view;

import java.awt.Graphics2D;

import window.game_engine.model.Camera;

public abstract class Renderer {


	protected Camera camera;
	protected int width, height;
	
	public Renderer(int width, int height, Camera camera){
		setCamera(camera);
		setWidth(width);
		setHeight(height);
	}
	
	public abstract void render(Graphics2D rendIn);
	
	public void setCamera(Camera camera){this.camera = camera;}
	public Camera getCamera(){return camera;}
	
	public void setWidth(int width){this.width = width;}
	public void setHeight(int height){this.height = height;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
}
