package game;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {

	private static GameState instance = null;
	
	private HashMap<Integer, HexSpace> map;
	private ArrayList<Player> players;
	private int currentPlayerIndex;
	
	private GameState(){
		map = new HashMap<>();
		players = new ArrayList<>();
		setCurrentPlayerIndex(0);
	}
	
	public void addPlayer(Player player){
		players.add(player);
		player.setTurnNumber(players.size() - 1);
	}
	
	public int getPlayerCount(){ return players.size(); }
	
	public Player getCurrentPlayer(){ return players.get(currentPlayerIndex); }
	
	public Player getPlayer(int index){return players.get(index % players.size()); }
	
	public static synchronized GameState getInstance(){
		if(instance == null)
			instance = new GameState();
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

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}
	
}
