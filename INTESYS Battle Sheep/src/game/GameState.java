package game;

import java.util.ArrayList;
import java.util.HashMap;

import AI.state.State;
import AI.state.TurnState;
import game.player.Player;
import utils.SimpleTimer;

public class GameState {

	public static final int SEC_WAIT = 10;
	public static final int NO_WAIT = -1;
	
	private static GameState instance = null;
	
	private HashMap<Integer, HexSpace> map;
	private ArrayList<HexSpace> hexList;
	private ArrayList<Player> players;
	private int currentPlayerIndex;
	private int hexSpaceCount;
	
	private SimpleTimer timer;
	private TurnState currTurn;
	
	private boolean isGameOver;
	private boolean isTurnOver;
	
	private GameState(){
		map = new HashMap<>();
		players = new ArrayList<>();
		setCurrentPlayerIndex(0);
		setHexSpaceCount(0);
		setGameOver(false);
		setTurnOver(true);
		hexList = new ArrayList<>();
	}
	
	public void init(Player player, int timerSec){
		if(timerSec != NO_WAIT)
		timer = new SimpleTimer((long) (timerSec * SimpleTimer.TO_SECONDS));
		currTurn = new TurnState(player);
	}
	
	public void init(Player player, int timerSec, ArrayList<SheepStack> sheepStacks){
		if(timerSec != NO_WAIT)
		timer = new SimpleTimer((long) (timerSec * SimpleTimer.TO_SECONDS));
		currTurn = new TurnState(player, sheepStacks);
	}

	public boolean isTurnFinish(){
		if(timer != null)
			return timer.checkTime();
		return false;
		}
	
	public void resetTimer(){ if(timer != null) timer.restart();}
	
	public void addPlayer(Player player){
		players.add(player);
		player.setTurnNumber(players.size() - 1);
	}
	
	// for testing
	public void autoRunPlayer(){
		while(!isGameOver && !isGameOver){
			if(isTurnOver){
				setTurnOver(false);
				getCurrentPlayer().doTurn();
				nextTurn();
				}
		}
		System.out.println("Game Over!");
	}
	
	public void nextTurn(){
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
	}
	
	public int getPlayerCount(){ return players.size(); }
	
	public Player getCurrentPlayer(){ return players.get(currentPlayerIndex); }
	
	public boolean isCurrentPlayer(Player player){ return player.getTurnNumber() == currentPlayerIndex; }
	
	public boolean isCurrentPlayer(int index){ return index == currentPlayerIndex; }
	
	public Player getNextPlayer(){ return players.get( (currentPlayerIndex + 1) % players.size()); }
	
	public Player getNextPlayer(Player player){ return players.get( (player.getTurnNumber() + 1) % players.size()); }
	
	public Player getPlayer(int index){return players.get(index % players.size()); }
	
	public Player getPlayer(String name){
		for(Player p: players)
			if(p.getName().equalsIgnoreCase(name))
				return p;
		return null;
		}
	
	public static synchronized GameState getInstance(){
		if(instance == null)
			instance = new GameState();
		return instance;
	}
	
	public void addHexSpace(int id, HexSpace space){
		if(!map.containsKey(id)){
		map.put(id, space);
		hexList.add(space);
		hexSpaceCount++;
		}
	}
	
	public void addHexSpace(int id){
		if(!map.containsKey(id)){
		map.put(id, new HexSpace(id));
		hexList.add(map.get(id));
		hexSpaceCount++;
		}
	}
	
	public HexSpace getHexSpace(int id){
		return map.get(id);
	}
	
	public void setHexNeighbor(int id, int neighbor_id, int direction){
		HexSpace neighbor = map.get(neighbor_id);
		map.get(id).setNeighbor(direction, map.get(neighbor_id));
		neighbor.setNeighbor((direction + 3) % HexSpace.MAX_NEIGHBORS, map.get(id));
		
		
		int x = 0;
		int y = 0;
		

			x = (int)(HexSpace.RADIUS * (2 - HexSpace.OFFSET) * Math.cos((1 + direction *2)*Math.PI/6));
			y = (int)(HexSpace.RADIUS * (2 - HexSpace.OFFSET) * Math.sin((1 + direction*2)*Math.PI/6));
			x += neighbor.getX();
			y += neighbor.getY();
			
		map.get(id).setX(x);
		map.get(id).setY(y);
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

	public TurnState getCurrentTurn() {
		return currTurn;
	}

	public void setCurrentTurn(TurnState currTurn) {
		this.currTurn = currTurn;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}

	public boolean isTurnOver() {
		return isTurnOver;
	}

	public void setTurnOver(boolean isTurnOver) {
		this.isTurnOver = isTurnOver;
	}

	public ArrayList<HexSpace> getHexList() {
		return hexList;
	}

	public void setHexList(ArrayList<HexSpace> hexList) {
		this.hexList = hexList;
	}
	
	public ArrayList<Player> getPlayers(){ return players;}
	
}
