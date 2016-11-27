package window.game_engine.controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout.SequentialGroup;

import game.GameState;
import game.HexSpace;
import window.game_engine.Input.Input;
import window.game_engine.Input.InputType;
import window.game_engine.model.Camera;
import window.game_engine.view.GameRenderer;
import window.game_engine.view.Renderer;

public class MainEngine {
	
	private GameState state;
	
	private Renderer renderer;
	
	private Controller controller;
	
	private Camera camera;
	
	private Input input;
	
	private int width, height;
	
	public MainEngine(int width, int height){
		setWidth(width);
		setHeight(height);
		setInput(new Input());
		setGameState(GameState.getInstance());
		setCamera(new Camera(width / 2, height / 2, 5));
		//getCamera().setLeft_limit(15);
		//getCamera().setTop_limit(75);
		//getCamera().setBottom_limit(15);
		setRenderer(new GameRenderer( width, height, getCamera(), state));
		setController(new Controller(width, height, state));
	}
	
	// keyboard
	public void input(InputType type, int code) {
		switch(type){
		case KEY_PRESS:
			input.setKey(code, true);
			break;
		case KEY_RELEASE:
			input.setKey(code, false);
			break;
		default:
		}
	}
	
	// mouse
	public void input(InputType type, int code, int x, int y) {
		switch(type){
		case MOUSE_CLICKED:
			//input.setMouseButton(code, true);
			//input.setMouseCoords(x, y);
			break;
		case MOUSE_ENTERED:
			//input.setMouseCoords(x, y);
			break;
		case MOUSE_EXITED:
			//input.setMouseCoords(x, y);
			break;
		case MOUSE_PRESSED:
			input.setMouseButton(code, true);
			//System.out.println("Mouse Button ID: "+code);
			input.setMouseCoords(x, y);
			break;
		case MOUSE_RELEASED:
			input.setMouseButton(code, false);
			input.setMouseCoords(x, y);
			break;
		}
	}

	public void update(float deltaTime) {
		camera.process(input.getKey(KeyEvent.VK_UP), input.getKey(KeyEvent.VK_DOWN), 
				input.getKey(KeyEvent.VK_LEFT), input.getKey(KeyEvent.VK_RIGHT));
		
		getController().update(deltaTime);
		
		boolean isSpaceFree;
		int nextHexSpace = GameState.getInstance().getHexSpaceCount();
		if(input.getMouseButton(1)){
			isSpaceFree = true;
			for(HexSpace h : GameState.getInstance().getHexList())
				if(h.generateSprite(camera).contains(input.getMouseX(), input.getMouseY())){
					System.out.println("ID: " + h.getId());
					for(HexSpace n: h.getNeighbors())
						if(n != null)
						System.out.println("Neighbor ID: " + n.getId());
					System.out.println();
					isSpaceFree = false;
					break;
					}

			if(isSpaceFree)
			for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++){
				for(HexSpace h : GameState.getInstance().getHexList()){
					int x = (int)(HexSpace.RADIUS * (2 - HexSpace.OFFSET) * Math.cos((1 + direction *2)*Math.PI/6) + input.getMouseX());
					int y = (int)(HexSpace.RADIUS * (2 - HexSpace.OFFSET) * Math.sin((1 + direction *2)*Math.PI/6) + input.getMouseY());
					if(h.generateSprite(camera).contains(x, y)){
						GameState.getInstance().addHexSpace(nextHexSpace);
						GameState.getInstance().setHexNeighbor(nextHexSpace, h.getId(), (direction + 3) % HexSpace.MAX_NEIGHBORS);
						break;
						}
					}
				
			}
		}
		
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
