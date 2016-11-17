package game;

import java.awt.Color;

import window.game_engine.model.GameObject;

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
	
	private HexSpace[] neighbors;
	private int id;
	
	public HexSpace(int id){
		super();
		neighbors = new HexSpace[MAX_NEIGHBORS];
		for(int i = 0; i < MAX_NEIGHBORS; i++)
			neighbors[i] = null;
		this.id = id;
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
	
	
	
}
