package window.game_engine.controller;

import game.GameState;

public class Controller {


	private GameState state;

	private int width, height;
	
	public Controller(int width, int height, GameState state){
		setWidth(width);
		setHeight(height);
		this.state = state;
	}
	
	 
	public void update(float deltaTime){
		
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

}
