package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

import window.game_engine.model.GameObject;
import window.game_engine.model.camera.Camera;

public class HexSpace extends GameObject{
	
	public static int MAX_NEIGHBORS = 6;
	
	public static final int N = 0;
	public static final int NE = 1;
	public static final int NW = 2;
	public static final int S = 3;
	public static final int SW = 4;
	public static final int SE = 5;
	
	public static final int RADIUS = 30;
	public static final int X_OFFSET = 18;
	public static final int Y_OFFSET = 24;
	public static final int Y_FINE_OFFSET = -8;
	
	public static final Color DEFAULT_COLOR = Color.GRAY;
	private Polygon sprite;
	
	private HexSpace[] neighbors;
	private int id;
	
	public HexSpace(int id){
		super();
		neighbors = new HexSpace[MAX_NEIGHBORS];
		for(int i = 0; i < MAX_NEIGHBORS; i++)
			neighbors[i] = null;
		this.id = id;
		sprite = new Polygon();
		for(int i=0; i<6; i++) {
		    sprite.addPoint((int)(0 + RADIUS *Math.cos(i*2*Math.PI/6)), (int)(0 + RADIUS *Math.sin(i*2*Math.PI/6)));
		}
	}
	
	public HexSpace(int id, HexSpace[] neighbors){
		this.neighbors = neighbors;
		this.id = id;
	}
	
	public boolean hasEmptyNeighbors(int number){
		int count = 0;
		for(HexSpace h: neighbors)
			if(h == null)
				count++;
		return count >= number;
	}
	
	public boolean hasEmptyNeighbor(){
		for(HexSpace h: neighbors)
			if(h == null)
				return true;
		return false;
	}
	
	public HexSpace[] getNeighbors(){ return neighbors; }
	
	public void setNeighbor(int direction, HexSpace neighbor){
		neighbors[direction] = neighbor;
	}
	
	public HexSpace getNeighbor(int direction){
		return neighbors[direction];
	}
	
	public HexSpace getFarNeigbor(int direction){
		HexSpace result = neighbors[direction];
		if(result != null)
		while(result.getNeighbor(direction) != null)
			result = result.getNeighbor(direction);
		
		return result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void render(Graphics2D rendIn, Camera camera) {
		rendIn.setColor(HexSpace.DEFAULT_COLOR);
		rendIn.drawString("ID: "+ id, camera.getX() + getPositionX() - 14,
				camera.getY() + getPositionY() - 14);
	}

	@Override
	public void update(float deltaTime) {
		
	}

	@Override
	public void input(float deltaTime) {
		
	}
	
	
	
}
