package window.game_engine.controller;

import game.GameState;
import game.HexSpace;
import window.game_engine.model.camera.Camera;

public class Controller {


	private Camera camera;
	private int width, height;
	
	public Controller(int width, int height, Camera camera){
		setWidth(width);
		setHeight(height);
		setCamera(camera);
	}
	
	 
	public void update(float deltaTime){
		camera.update(deltaTime);
		for(HexSpace h: getState().getHexList())
			h.update(deltaTime);
	}

	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public GameState getState() {
		return GameState.getInstance();
	}


	public Camera getCamera() {
		return camera;
	}


	public void setCamera(Camera camera) {
		this.camera = camera;
	}


}
