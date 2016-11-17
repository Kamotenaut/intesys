package window.game_engine.view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import game.GameState;
import game.HexSpace;
import game.SheepStack;
import game.player.Player;
import utils.SimpleTimer;
import window.game_engine.model.camera.Camera;

public class GameRenderer extends Renderer{

	//private GameState state;
	
	public GameRenderer(int width, int height, Camera camera) {
		super(width, height, camera);
	}

	@Override
	public void render(Graphics2D rendIn) {
		for(HexSpace h :getState().getHexList()){
			h.render(rendIn, camera);
		
		for(SheepStack s: getState().getCurrentTurn().getSheepStacks()){
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
		rendIn.setColor(getState().getCurrentPlayer().getColor());
		rendIn.drawString(getState().getCurrentPlayer().getName(),x + 80,y);
		rendIn.setColor(Color.BLACK);
		rendIn.drawString("Players: ",x,y + 14);
		for(int i = 0; i < getState().getPlayers().size(); i++){
			rendIn.setColor(getState().getPlayers().get(i).getColor());
			rendIn.drawString(getState().getPlayers().get(i).getName(),x + 3,(y + ((i+2) * 14)) + 3);
		}
		rendIn.setColor(Color.BLACK);
		rendIn.drawString("Use [ARROWKEYS] to move map.", x, height - 14);
		rendIn.drawString("AI turn timer: " + getState().getTimer().checkCurrentTime() / SimpleTimer.TO_SECONDS, x, height - 14 * 2);
		
		if(getState().isGameOver()){
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
		return GameState.getInstance();
	}

}
