package battlesheep.view.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import battlesheep.GameState;
import battlesheep.AI.state.BattleSheepState;
import battlesheep.AI.state.State;
import battlesheep.actor.HexSpace;
import battlesheep.actor.SheepStack;
import battlesheep.actor.SingleCamera;
import jGame.model.game.GameObject;
import jGame.model.game.GameScene;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.model.timer.SimpleTimer;
import jGame.view.Renderer;

public class PlayGameScene extends GameScene{

	private SingleCamera camera = null;
	private SimpleTimer leftMouseTimer;
	private SimpleTimer rightMouseTimer;
	
	private HexSpace sourceHex;
	private HexSpace destinationHex;
	private ArrayList<HexSpace> validSpaces;
	private int maxSheep;
	private int numToMove;
	
	public PlayGameScene(String name) {
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
		resetSelection();
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
		// game state change
		if(input.getKeyboardKey(KeyEvent.VK_ENTER))if(GameState.getInstance().getStateChangeTimer().checkTime()){
			if(sourceHex != null && destinationHex != null){
			
			int sheepIndex = 0;
			for(int i = 0; i < GameState.getInstance().getSheepStacks().size(); i++)
				if(GameState.getInstance().getSheepStacks().get(i).getHexSpaceID() == sourceHex.getId())
					sheepIndex = i;
			((BattleSheepState)GameState.getInstance().getCurrentTurn()).move(GameState.getInstance().getSheepStacks(), sheepIndex, destinationHex, numToMove, GameState.getInstance().getCurrentPlayer());
			State result = new BattleSheepState(GameState.getInstance().getNextPlayer(), GameState.getInstance().getSheepStacks());
			
			GameState.getInstance().setCurrentTurn(result);
			GameState.getInstance().nextTurn();
			GameState.getInstance().setTurnOver(true);
			} else {
				System.out.println("MOVE IS NOT VALID!!!");
			}
			GameState.getInstance().getStateChangeTimer().restart();
			}
		if(input.getKeyboardKey(KeyEvent.VK_BACK_SPACE))if(GameState.getInstance().getStateChangeTimer().checkTime()){
			getGameSceneManager().changeScene("SETUP_PLAYER");
			GameState.getInstance().getStateChangeTimer().restart();
		}
		// human player input TODO
		if(!GameState.getInstance().isGameOver() && !GameState.getInstance().isTurnOver())
			if(GameState.getInstance().getCurrentPlayer().getName().equalsIgnoreCase(GameState.HUM_PLAYER)){
				if(input.getMouseButton(MouseEvent.BUTTON1) && leftMouseTimer.checkTime()){
					for(HexSpace h : GameState.getInstance().getHexList())
						if(h.generateSprite(camera).contains(input.getMouseX(), input.getMouseY())){
							// left click
							for(SheepStack s: ((BattleSheepState)GameState.getInstance().getCurrentTurn()).getSheepStacks())
								if(s.getHexSpaceID() == h.getId() && s.getNumberOfSheep() > 1){
									if(sourceHex == null){
										sourceHex = h;
										maxSheep = s.getNumberOfSheep();
										validSpaces = ((BattleSheepState)GameState.getInstance().getCurrentTurn()).validNextMoves(h);
									}
									break;
								}
							
							if(sourceHex != null && validSpaces != null){
								for(HexSpace dest: validSpaces){
									// create new destination
									if(dest.equals(h)){
										destinationHex = h;
										if(destinationHex == null)
											numToMove = 1;
										else {
											numToMove++;
											numToMove = numToMove % maxSheep;
											if(numToMove == 0)
												numToMove = 1;
										}
									}
									}
							}
							
							
						}
					leftMouseTimer.restart();
				} 
				if(input.getMouseButton(MouseEvent.BUTTON3) && rightMouseTimer.checkTime()){
					
					// right click
					resetSelection();
					
					rightMouseTimer.restart();
				}
			}
			
	}

	@Override
	public void logic(long deltaTime) {
		camera.logic(deltaTime);
		if(!GameState.getInstance().isGameOver())
		if(GameState.getInstance().isTurnOver()){
			resetSelection();
			GameState.getInstance().setTurnOver(false);
			GameState.getInstance().getCurrentPlayer().doTurn();
		}
	}

	@Override
	public void render(Renderer renderer) {
		for(GameObject h: GameState.getInstance().getHexList())
			h.render(renderer, camera);
		
		for(SheepStack s:GameState.getInstance().getSheepStacks()){
			if(sourceHex != null)
				if(s.getHexSpaceID() == sourceHex.getId())
					renderer.getRendIn().setStroke(new BasicStroke(5));
			renderer.getRendIn().setColor(s.getOwner().getColor());
			renderer.getRendIn().drawPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setStroke(new BasicStroke(1));
			renderer.getRendIn().setColor(s.getOwner().getColor2());
			renderer.getRendIn().fillPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setColor(s.getOwner().getColor());
			
			renderer.getRendIn().drawString("ID: "+s.getHexSpace().getId(),
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 14,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() - 14);
			if(sourceHex == null)
			renderer.getRendIn().drawString("S: "+s.getNumberOfSheep(), 
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 24,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() + 3);
			else if(sourceHex.getId() == s.getHexSpaceID())
			renderer.getRendIn().drawString("S: "+(s.getNumberOfSheep() - numToMove), 
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 24,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() + 3);
			else 
			renderer.getRendIn().drawString("S: "+s.getNumberOfSheep(), 
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 24,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() + 3);
			}
		if(destinationHex != null){
			renderer.getRendIn().setStroke(new BasicStroke(5));
			renderer.getRendIn().setColor(GameState.getInstance().getCurrentPlayer().getColor());
			renderer.getRendIn().drawPolygon(destinationHex.generateSprite(camera));
			renderer.getRendIn().setStroke(new BasicStroke(1));
			renderer.getRendIn().setColor(GameState.getInstance().getCurrentPlayer().getColor2());
			renderer.getRendIn().fillPolygon(destinationHex.generateSprite(camera));
			renderer.getRendIn().setColor(GameState.getInstance().getCurrentPlayer().getColor());
			
			renderer.getRendIn().drawString("ID: "+destinationHex.getId(),
				camera.getPosition().getX() + destinationHex.getPosition().getX() - 14,
				camera.getPosition().getY() + destinationHex.getPosition().getY() - 14);
			renderer.getRendIn().drawString("S: "+numToMove, 
				camera.getPosition().getX() + destinationHex.getPosition().getX() - 24,
				camera.getPosition().getY() + destinationHex.getPosition().getY() + 3);
		}
		renderer.getRendIn().setColor(Color.BLACK);

		renderer.getRendIn().drawString("Instructions:", 15, 15);
		renderer.getRendIn().drawString("1. Left click mouse to select a souce hexagon. ", 30, 30);
		renderer.getRendIn().drawString("2. Left click mouse to select a destination hexagon (This adds one sheep to hexagon). ", 30, 45);
		renderer.getRendIn().drawString("3. Continue left clicking to add more sheep. ", 30, 60);
		renderer.getRendIn().drawString("4. Press [ENTER] to finalize move. ", 30, 75);
		renderer.getRendIn().drawString("Note: Right click to reset selections. ", 30, 90);
		
		renderer.getRendIn().drawString("Current Turn: ",15,120);
		renderer.getRendIn().setColor(GameState.getInstance().getCurrentPlayer().getColor());
		renderer.getRendIn().drawString(GameState.getInstance().getCurrentPlayer().getName(),15 + 80,120);
		renderer.getRendIn().setColor(Color.BLACK);
		renderer.getRendIn().drawString("Players: ",15,150);
		for(int i = 0; i < GameState.getInstance().getPlayers().size(); i++){
			renderer.getRendIn().setColor(GameState.getInstance().getPlayers().get(i).getColor());
			renderer.getRendIn().drawString(GameState.getInstance().getPlayers().get(i).getName(),15 + 3,(135 + ((i+2) * 14)) + 3);
		}
		renderer.getRendIn().setColor(Color.BLACK);
		renderer.getRendIn().drawString("Use [ARROWKEYS] to move map.", 15, 540 - 14);
		renderer.getRendIn().drawString("AI turn timer: " + GameState.getInstance().getTimer().toSeconds(), 15, 540 - 14 * 2);

		renderer.getRendIn().setStroke(new BasicStroke(1));
	}

	@Override
	public void close() {
		
	}
	
	public void resetSelection(){
		GameState.getInstance().setSheepStacks(((BattleSheepState)GameState.getInstance().getCurrentTurn()).cloneSheepStacks());
		maxSheep = 0;
		numToMove = 0;
		sourceHex = null;
		destinationHex = null;
	}

}
