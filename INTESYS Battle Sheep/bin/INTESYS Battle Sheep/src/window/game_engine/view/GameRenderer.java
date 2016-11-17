package window.game_engine.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import game.GameState;
import game.HexSpace;
import game.SheepStack;
import game.player.Player;
import utils.SimpleTimer;
import window.game_engine.model.Camera;

public class GameRenderer extends Renderer{

	private GameState state;
	
	public GameRenderer(int width, int height, Camera camera, GameState state) {
		super(width, height, camera);
		setState(state);
	}

	@Override
	public void render(Graphics2D rendIn) {
		for(HexSpace h :GameState.getInstance().getHexList()){
		rendIn.setColor(HexSpace.DEFAULT_COLOR);
		drawHex(rendIn, width / 2 + camera.getX() + h.getX(), height / 2 + camera.getY() + h.getY(), HexSpace.RADIUS);
		rendIn.drawString("ID: "+h.getId(), width / 2 + camera.getX() + h.getX() - 14,
				height / 2 + camera.getY() + h.getY() - 14);
		
		for(SheepStack s:GameState.getInstance().getCurrentTurn().getSheepStacks()){
			rendIn.setColor(s.getOwner().getColor());
			drawHex(rendIn, width / 2 + camera.getX() + s.getHexSpace().getX(), height / 2 + camera.getY() + s.getHexSpace().getY(), HexSpace.RADIUS);
			rendIn.drawString("ID: "+s.getHexSpace().getId(), width / 2 + camera.getX() + s.getHexSpace().getX() - 14,
					height / 2 + camera.getY() + s.getHexSpace().getY() - 14);
			rendIn.drawString("S: "+s.getNumberOfSheep(), width / 2 + camera.getX() + s.getHexSpace().getX() - 24,
				height / 2 + camera.getY() + s.getHexSpace().getY() + 3);
			}
		}
		
		drawInfo(rendIn, 15, 15);
	}
	
	public void drawInfo(Graphics2D rendIn, int x, int y){
		rendIn.setColor(Color.BLACK);
		rendIn.drawString("Current Turn: ",x,y);
		rendIn.setColor(state.getCurrentPlayer().getColor());
		rendIn.drawString(state.getCurrentPlayer().getName(),x + 80,y);
		rendIn.setColor(Color.BLACK);
		rendIn.drawString("Players: ",x,y + 14);
		for(int i = 0; i < state.getPlayers().size(); i++){
			rendIn.setColor(state.getPlayers().get(i).getColor());
			rendIn.drawString(state.getPlayers().get(i).getName(),x + 3,(y + ((i+2) * 14)) + 3);
		}
		rendIn.setColor(Color.BLACK);
		rendIn.drawString("Use [ARROWKEYS] to move map.", x, height - 14);
		rendIn.drawString("AI turn timer: " + GameState.getInstance().getTimer().checkCurrentTime() / SimpleTimer.TO_SECONDS, x, height - 14 * 2);
		
		if(GameState.getInstance().isGameOver()){
			rendIn.setColor(Color.BLACK);
			rendIn.drawString("Game Over!", width/2, height/2);
		}
	}
	
	public void drawHex(Graphics2D rendIn, int x, int y, int r){
		Polygon sprite = new Polygon(); 
		for(int i=0; i<6; i++) {
		    sprite.addPoint((int)(x + r*Math.cos(i*2*Math.PI/6)), (int)(y + r*Math.sin(i*2*Math.PI/6)));
		}
		rendIn.drawPolygon(sprite);
	}

	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}

}
