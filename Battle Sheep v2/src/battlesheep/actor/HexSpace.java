package battlesheep.actor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;

import jGame.model.game.GameObject;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.model.physics.Physics3D;
import jGame.view.Renderer;

public class HexSpace extends Physics3D implements GameObject{

	public static int MAX_NEIGHBORS = 6;
	
	public static final int N = 4;
	public static final int NE = 5;
	public static final int SE = 0;
	public static final int S = 1;
	public static final int SW = 2;
	public static final int NW = 3;
	
	public static final int RADIUS = 30;
	public static final float OFFSET = 0.1f;
	
	public static final Color DEFAULT_COLOR = Color.GREEN.darker();
	public static final Color DEFAULT_COLOR_FILL = Color.GREEN;
	
	private HexSpace[] neighbors;
	private int id;
	
	public HexSpace(int id){
		super();
		neighbors = new HexSpace[MAX_NEIGHBORS];
		for(int i = 0; i < MAX_NEIGHBORS; i++)
			neighbors[i] = null;
		this.id = id;
	}
	
	public Polygon generateSprite(Camera camera){
		Polygon sprite = new Polygon(); 
		for(int i=0; i<6; i++) {
		    sprite.addPoint((int)(getPosition().getX() + RADIUS*Math.cos(i*2*Math.PI/6) + camera.getPosition().getX()),
		    		(int)(getPosition().getY() + RADIUS*Math.sin(i*2*Math.PI/6) + camera.getPosition().getY()));
		}
		return sprite;
	}
	
	public HexSpace(int id, HexSpace[] neighbors){
		this.neighbors = neighbors;
		this.id = id;
	}
	
	public boolean isNeighbor(HexSpace space){
		for(HexSpace h: neighbors)
			if(h != null)
				if(h.equals(space))
					return true;
		return false;
	}
	
	public int countEmptyNeighbors(){
		int count = 0;
		for(HexSpace h: neighbors)
			if(h == null)
				count++;
		return count;
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
	
	public boolean equals(HexSpace hex){
		return id == hex.getId();
	}
	
	@Override
	public void input(Input input, long deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void logic(long deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Renderer renderer, Camera camera) {

		renderer.getRendIn().setStroke(new BasicStroke(3));
		renderer.getRendIn().setColor(DEFAULT_COLOR);
		renderer.getRendIn().drawPolygon(generateSprite(camera));
		renderer.getRendIn().setColor(DEFAULT_COLOR_FILL);
		renderer.getRendIn().fillPolygon(generateSprite(camera));
	}

	
	public HexSpace[] getNeighbors(){ return neighbors; }
	
	public void removeNeighbor(HexSpace hex){
		for(int i = 0; i < MAX_NEIGHBORS; i++)
			if(neighbors[i] != null)
				if(neighbors[i].equals(hex)){
					neighbors[i] = null;
					break;
					}
	}
	
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
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
}
