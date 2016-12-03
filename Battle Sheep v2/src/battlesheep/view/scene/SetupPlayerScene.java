package battlesheep.view.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import battlesheep.GameState;
import battlesheep.actor.ComputerPlayer;
import battlesheep.actor.HexSpace;
import battlesheep.actor.HumanPlayer;
import battlesheep.actor.Player;
import battlesheep.actor.SheepStack;
import battlesheep.actor.SingleCamera;
import jGame.model.game.GameObject;
import jGame.model.game.GameScene;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.model.timer.SimpleTimer;
import jGame.view.Renderer;

public class SetupPlayerScene extends GameScene{

	public static final int MAX_SHEEP_PER_PLAYER = 16;
	
	private SingleCamera camera = null;
	private SimpleTimer leftMouseTimer;
	private SimpleTimer rightMouseTimer;
	
	public SetupPlayerScene(String name) {
		super(name);
		setDoneLoading(true);
	}

	@Override
	public void init() {
		camera = SingleCamera.getInstance();
		leftMouseTimer = new SimpleTimer(0.25f);
		rightMouseTimer = new SimpleTimer(0.25f);
		leftMouseTimer.setResult(true);
		rightMouseTimer.setResult(true);
		GameState.getInstance().reset();
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
			for(HexSpace h : GameState.getInstance().getHexList())
				if(h.generateSprite(camera).contains(input.getMouseX(), input.getMouseY()))
					GameState.getInstance().setPlayer("HUMAN", h.getId());
			leftMouseTimer.restart();
		} else 
		if(input.getMouseButton(MouseEvent.BUTTON3) && rightMouseTimer.checkTime()){
			for(HexSpace h : GameState.getInstance().getHexList())
				if(h.generateSprite(camera).contains(input.getMouseX(), input.getMouseY()))
					GameState.getInstance().setPlayer("COMPUTER", h.getId());
			rightMouseTimer.restart();
		}
		
		if(input.getKeyboardKey(KeyEvent.VK_ENTER))if(GameState.getInstance().getStateChangeTimer().checkTime()){
			if(GameState.getInstance().getSheepStackPlayer().size() + GameState.getInstance().getSheepStackEnemy().size() == GameState.getInstance().getPlayerCount()){
				GameState.getInstance().init();
				getGameSceneManager().changeScene("PLAY_GAME");
			} else {
				System.out.println("All players must have sheeps on the board");
			}
			GameState.getInstance().getStateChangeTimer().restart();
			}
		if(input.getKeyboardKey(KeyEvent.VK_BACK_SPACE))if(GameState.getInstance().getStateChangeTimer().checkTime()){
			getGameSceneManager().changeScene("SETUP_BOARD");
			GameState.getInstance().getStateChangeTimer().restart();
		}
	}

	@Override
	public void logic(long deltaTime) {
		camera.logic(deltaTime);
	}

	@Override
	public void render(Renderer renderer) {
		for(GameObject h: GameState.getInstance().getHexList())
			h.render(renderer, camera);
		for(Player p: GameState.getInstance().getPlayers())
		for(SheepStack s:GameState.getInstance().getSheepStack(p)){
			renderer.getRendIn().setColor(s.getOwner().getColor());
			renderer.getRendIn().drawPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setColor(s.getOwner().getColor2());
			renderer.getRendIn().fillPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setColor(s.getOwner().getColor());
			renderer.getRendIn().drawString("ID: "+s.getHexSpace().getId(),
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 14,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() - 14);
			renderer.getRendIn().drawString("S: "+s.getNumberOfSheep(), 
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 24,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() + 3);
			}

		renderer.getRendIn().setColor(Color.BLACK);
		renderer.getRendIn().drawString("Instructions:", 15, 15);
		renderer.getRendIn().drawString("Hold click a hexagon to add a player. ", 30, 30);
		renderer.getRendIn().drawString("Left-click for human player. ", 45, 45);
		renderer.getRendIn().drawString("Right-click for computer player. ", 45, 60);
		renderer.getRendIn().drawString("Player added last goes first. ", 30, 75);
		renderer.getRendIn().drawString("Clicking a hexagon will remove the player. ", 30, 90);
		
		renderer.getRendIn().drawString("First Turn: ",15,120);
		if(GameState.getInstance().getCurrentPlayer() != null){
			renderer.getRendIn().setColor(GameState.getInstance().getCurrentPlayer().getColor());
			renderer.getRendIn().drawString(GameState.getInstance().getCurrentPlayer().getName(),15 + 80,120);
		}
		renderer.getRendIn().setColor(Color.BLACK);
		renderer.getRendIn().drawString("Players: ",15,150);
		for(int i = 0; i < GameState.getInstance().getPlayers().size(); i++){
			renderer.getRendIn().setColor(GameState.getInstance().getPlayers().get(i).getColor());
			renderer.getRendIn().drawString(GameState.getInstance().getPlayers().get(i).getName(),15 + 3,(150 + ((i+2) * 14)) + 3);
		}
		renderer.getRendIn().setColor(Color.BLACK);
		renderer.getRendIn().drawString("Use [ENTER] to start game.", 15, 540 - 44);
		renderer.getRendIn().drawString("Use [BACKSPACE] to return to map setup.", 15, 540 - 29);
		renderer.getRendIn().drawString("Use [ARROW KEYS] to move map.", 15, 540 - 14);
		
		renderer.getRendIn().setStroke(new BasicStroke(1));
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	public SimpleTimer getLeftMouseTimer() {
		return leftMouseTimer;
	}

	public void setLeftMouseTimer(SimpleTimer leftMouseTimer) {
		this.leftMouseTimer = leftMouseTimer;
	}

	public SimpleTimer getRightMouseTimer() {
		return rightMouseTimer;
	}

	public void setRightMouseTimer(SimpleTimer rightMouseTimer) {
		this.rightMouseTimer = rightMouseTimer;
	}

}
