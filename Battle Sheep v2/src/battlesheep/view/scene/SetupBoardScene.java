package battlesheep.view.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import battlesheep.GameState;
import battlesheep.actor.HexSpace;
import battlesheep.actor.SingleCamera;
import jGame.model.game.GameObject;
import jGame.model.game.GameScene;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.model.timer.SimpleTimer;
import jGame.view.Renderer;

public class SetupBoardScene extends GameScene{

	private SingleCamera camera = null;
	private SimpleTimer leftMouseTimer;
	private SimpleTimer rightMouseTimer;
	
	private boolean isSpaceFree;
	
	
	public SetupBoardScene(String name) {
		super(name);
		setDoneLoading(true);
	}

	@Override
	public void init() {
		camera = SingleCamera.getInstance();
		camera.setPosition(740/2, 540/2, 0);
		leftMouseTimer = new SimpleTimer(0.25f);
		rightMouseTimer = new SimpleTimer(0.25f);
		leftMouseTimer.setResult(true);
		rightMouseTimer.setResult(true);
		
	}

	@Override
	public void input(Input input, long deltaTime) {
		// camera
		if(!(input.getKeyboardKey(KeyEvent.VK_UP) || input.getKeyboardKey(KeyEvent.VK_DOWN)))
			camera.accelDecayY(Camera.DEF_ACCEL * 2, true);
		else {
			if(input.getKeyboardKey(KeyEvent.VK_UP))
				camera.accelerateY(-Camera.DEF_ACCEL);
			if(input.getKeyboardKey(KeyEvent.VK_DOWN))
				camera.accelerateY(Camera.DEF_ACCEL);
		}
		if(!(input.getKeyboardKey(KeyEvent.VK_RIGHT) || input.getKeyboardKey(KeyEvent.VK_LEFT)))
			camera.accelDecayX(Camera.DEF_ACCEL * 2, true);
		else {
			if(input.getKeyboardKey(KeyEvent.VK_RIGHT))
				camera.accelerateX(Camera.DEF_ACCEL);
			if(input.getKeyboardKey(KeyEvent.VK_LEFT))
				camera.accelerateX(-Camera.DEF_ACCEL);
		}
		
		if(input.getMouseButton(MouseEvent.BUTTON1) && leftMouseTimer.checkTime()){
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
			int nextHexSpace = GameState.getInstance().getHexSpaceCount();
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
			leftMouseTimer.restart();
		} else
		if(input.getMouseButton(MouseEvent.BUTTON3)  && rightMouseTimer.checkTime()){
			int id = -1;
			System.out.println(input.getMouseX()+" "+input.getMouseY());
			for(HexSpace h : GameState.getInstance().getHexList())
				if(h.generateSprite(camera).contains(input.getMouseX(), input.getMouseY())){
					id = h.getId();
					break;
					}
			if(id != -1){
				if(GameState.getInstance().removeHex(id)){
				System.out.println("Removed ID: " + id);
				System.out.println();
				} else {
					System.out.println("Cannot remove hexagon with ID: "+id);
					System.out.println("Either only one hexagon exists or it has sheeps.");
				}
			}
			rightMouseTimer.restart();
		}
		if(input.getKeyboardKey(KeyEvent.VK_ENTER))if(GameState.getInstance().getStateChangeTimer().checkTime()){
			getGameSceneManager().changeScene("SETUP_PLAYER");
			GameState.getInstance().getStateChangeTimer().restart();
			}
	}

	@Override
	public void logic(long deltaTime) {
		camera.logic(deltaTime);
			
	}

	@Override
	public void render(Renderer renderer) {
		//for(GameObject o: getActors())
		for(GameObject h: GameState.getInstance().getHexList())
			h.render(renderer, camera);
		

		renderer.getRendIn().setColor(Color.BLACK);
		renderer.getRendIn().drawString("Instructions:", 15, 15);
		renderer.getRendIn().drawString("Click left mouse button near hexagons to add more. ", 30, 30);
		renderer.getRendIn().drawString("Click right mouse button near hexagons to remove selected hexagon. ", 30, 45);

		renderer.getRendIn().drawString("Use [ENTER] to goto player setup.", 15, 540 - 29);
		renderer.getRendIn().drawString("Use [ARROW KEYS] to move map.", 15, 540 - 14);
		

		renderer.getRendIn().setStroke(new BasicStroke(1));
	}

	@Override
	public void close() {
		
	}

}
