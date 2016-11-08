package game;

import java.util.ArrayList;
import java.util.HashMap;

import utils.SimpleTimer;

public class GameState {

	public static final int SEC_WAIT = 10;
	
	private static GameState instance = null;
	
	private HashMap<Integer, HexSpace> map;
	private ArrayList<Player> players;
	private int currentPlayerIndex;
	private int hexSpaceCount;
	
	private SimpleTimer timer;
	
	private GameState(){
		map = new HashMap<>();
		players = new ArrayList<>();
		setCurrentPlayerIndex(0);
		setHexSpaceCount(0);
		timer = new SimpleTimer((long) (SEC_WAIT * SimpleTimer.TO_SECONDS));
	}

	public boolean isTurnFinish(){return timer.checkTime();}
	
	public void resetTimer(){timer.restart();}
	
	public void addPlayer(Player player){
		players.add(player);
		player.setTurnNumber(players.size() - 1);
	}
	
	public int getPlayerCount(){ return players.size(); }
	
	public Player getCurrentPlayer(){ return players.get(currentPlayerIndex); }
	
	public boolean isCurrentPlayer(Player player){ return player.getTurnNumber() == currentPlayerIndex; }
	
	public boolean isCurrentPlayer(int index){ return index == currentPlayerIndex; }
	
	public Player getNextPlayer(){ return players.get( (currentPlayerIndex + 1) % players.size()); }
	
	public Player getNextPlayer(Player player){ return players.get( (player.getTurnNumber() + 1) % players.size()); }
	
	public Player getPlayer(int index){return players.get(index % players.size()); }
	
	public static synchronized GameState getInstance(){
		if(instance == null)
			instance = new GameState();
		return instance;
	}
	
	public void addHexSpace(int id, HexSpace space){
		if(!map.containsKey(id)){
		map.put(id, space);
		hexSpaceCount++;
		}
	}
	
	public void addHexSpace(int id){
		if(!map.containsKey(id)){
		map.put(id, new HexSpace(id));
		hexSpaceCount++;
		}
	}
	
	public HexSpace getHexSpace(int id){
		return map.get(id);
	}
	
	public void setHexNeighbor(int id, int neighbor_id, int direction){
		map.get(id).setNeighbor(direction, map.get(neighbor_id));
		map.get(neighbor_id).setNeighbor((direction + 3) % HexSpace.MAX_NEIGHBORS, map.get(id));
	}

	public int getCurrentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void setCurrentPlayerIndex(int currentPlayerIndex) {
		this.currentPlayerIndex = currentPlayerIndex;
	}

	public int getHexSpaceCount() {
		return hexSpaceCount;
	}

	public void setHexSpaceCount(int hexSpaceCount) {
		this.hexSpaceCount = hexSpaceCount;
	}

	public SimpleTimer getTimer() {
		return timer;
	}

	public void setTimer(SimpleTimer timer) {
		this.timer = timer;
	}
	
}
