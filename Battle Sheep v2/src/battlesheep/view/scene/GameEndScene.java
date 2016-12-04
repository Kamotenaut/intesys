package battlesheep.view.scene;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyEvent;

import battlesheep.GameState;
import battlesheep.AI.state.BattleSheepState;
import battlesheep.actor.SheepStack;
import battlesheep.actor.SingleCamera;
import jGame.model.game.GameObject;
import jGame.model.game.GameScene;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.view.Renderer;

public class GameEndScene extends GameScene{

	private SingleCamera camera = null;
	
	public GameEndScene(String name) {
		super(name);
		setDoneLoading(true);
	}

	@Override
	public void init() {
		camera = SingleCamera.getInstance();
		GameState.getInstance().setSheepStack(
				GameState.getInstance().getCurrentPlayer(),
				((BattleSheepState)GameState.getInstance().getCurrentTurn()).cloneSheepStackPlayer()
				);
		GameState.getInstance().setSheepStack(
				GameState.getInstance().getNextPlayer(),
				((BattleSheepState)GameState.getInstance().getCurrentTurn()).cloneSheepStackEnemy()
				);
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
		if(input.getKeyboardKey(KeyEvent.VK_ENTER))if(GameState.getInstance().getStateChangeTimer().checkTime()){
			GameState.getInstance().reset();
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
		
		for(SheepStack s:GameState.getInstance().getSheepStackPlayer()){
			renderer.getRendIn().setColor(s.getOwner().getColor());
			renderer.getRendIn().drawPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setStroke(new BasicStroke(1));
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
		
		for(SheepStack s:GameState.getInstance().getSheepStackEnemy()){
			
			renderer.getRendIn().setColor(s.getOwner().getColor());
			renderer.getRendIn().drawPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setStroke(new BasicStroke(1));
			renderer.getRendIn().setColor(s.getOwner().getColor2());
			renderer.getRendIn().fillPolygon(s.getHexSpace().generateSprite(camera));
			renderer.getRendIn().setColor(s.getOwner().getColor());
			
			renderer.getRendIn().drawString("ID: "+s.getHexSpace().getId(),
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 14,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() - 14);
			
			renderer.getRendIn().drawString("S: "+s.getNumberOfSheep(), 
				camera.getPosition().getX() + s.getHexSpace().getPosition().getX() - 24,
				camera.getPosition().getY() + s.getHexSpace().getPosition().getY() + 3);
			
			renderer.getRendIn().setColor(Color.BLACK);

			renderer.getRendIn().drawString("Loser is " + GameState.getInstance().getCurrentPlayer().getName(), 740 / 2, 540 / 2);
			renderer.getRendIn().drawString("Press [ENTER] to restart.", 740 / 2, 540 / 2 + 15);
			}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

}
