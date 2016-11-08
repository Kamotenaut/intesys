package game;

import java.util.HashMap;

public class GameMap {

	private static GameMap instance = null;
	
	private HashMap<Integer, HexSpace> map;
	
	private GameMap(){
		map = new HashMap<>();
	}
	
	public static synchronized GameMap getInstance(){
		if(instance == null)
			instance = new GameMap();
		return instance;
	}
	
	public void addHexSpace(int id){
		if(!map.containsKey(id))
		map.put(id, new HexSpace(id));
	}
	
	public HexSpace getHexSpace(int id){
		return map.get(id);
	}
	
	public void setHexNeighbor(int id, int neighbor_id, int direction){
		map.get(id).setNeighbor(direction, map.get(neighbor_id));
	}
	
}
