package window.game_engine.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout.SequentialGroup;

import game.GameState;
import window.game_engine.Input.Input;
import window.game_engine.Input.InputType;
import window.game_engine.model.camera.Camera;
import window.game_engine.view.GameRenderer;
import window.game_engine.view.Renderer;

public class MainEngine {
	
	private GameState state;
	
	
	// Controllers
	private Controller controller;
	private Renderer renderer;
	
	private Camera camera;
	
	private Input input;
	
	private int width, height;
	
	public MainEngine(int width, int height){
		setWidth(width);
		setHeight(height);
		setInput(new Input());
		setGameState(GameState.getInstance());
		setCamera(new Camera());
		//getCamera().setLeft_limit(15);
		//getCamera().setTop_limit(75);
		//getCamera().setBottom_limit(15);
		setRenderer(new GameRenderer( width, height, getCamera()));
		setController(new Controller(width, height, getCamera()));
	}
	
	// keyboard
	public void input(InputType type, int code) {
		input.inputKeyboard(type, code);
	}
	
	// mouse
	public void input(InputType type, int code, int x, int y) {
		input.inputMouse(type, code, x, y);
	}

	public void update(float deltaTime) {
		getController().update(deltaTime);
	}
	
	public void render(Graphics2D rendIn) {
		
		renderer.render(rendIn);
		
	}
	
	// getters and setters

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public GameState getGameState() {
		return state;
	}

	public void setGameState(GameState state) {
		this.state = state;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public void setRenderer(Renderer renderer) {
		this.renderer = renderer;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
