package battlesheep;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import battlesheep.AI.state.BattleSheepState;
import battlesheep.AI.state.State;
import battlesheep.actor.ComputerPlayer;
import battlesheep.actor.HexSpace;
import battlesheep.actor.HumanPlayer;
import battlesheep.actor.Player;
import battlesheep.actor.SheepStack;
import jGame.model.timer.SimpleTimer;


public class GameState {

	public static final int INIT_SHEEP_SIZE = 16;
	public static final float INIT_TURN_TIME = 30.0f;
	
	public static final String HUM_PLAYER = "HUMAN";
	public static final String COM_PLAYER = "COMPUTER";
	
	private static GameState instance = null;
	
	private HashMap<Integer, HexSpace> map;
	private ArrayList<HexSpace> hexList;
	private ArrayList<Player> players;
	private HashMap<Player, ArrayList<SheepStack>> sheepStackMap;
	private int currentPlayerIndex;
	private Player currentPlayer;
	private int hexSpaceCount;
	
	private SimpleTimer timer;
	private State currTurn;

	private boolean isGameOver;
	private boolean isTurnOver;

	private SimpleTimer stateChangeTimer;
	
	private GameState(){
		setSheepStackMap(new HashMap<>());
		setHexSpaceCount(0);
		map = new HashMap<>();
		hexList = new ArrayList<>();
		addHexSpace(0);
		players = new ArrayList<>();
		addPlayer(new HumanPlayer(HUM_PLAYER, Color.BLUE, Color.CYAN));
		addPlayer(new ComputerPlayer(COM_PLAYER, Color.RED, Color.ORANGE));
		setStateChangeTimer(new SimpleTimer(0.50f));
		setTimer(new SimpleTimer(INIT_TURN_TIME));
		stateChangeTimer.setResult(true);
		reset();
	}
	
	public void reset(){
		setCurrentPlayerIndex(0);
		setGameOver(false);
		setTurnOver(true);
		setCurrentTurn(null);
		setCurrentPlayer(null);
	}

	
	public void init(){
		timer.restart();
		currTurn = new BattleSheepState(currentPlayer, getSheepStack(currentPlayer), getSheepStack(getNextPlayer()));
	}
	
	public void setSheepStack(Player player, ArrayList<SheepStack> sheepStack){
		sheepStackMap.put(player, sheepStack);
	}
	
	public ArrayList<SheepStack> getSheepStack(Player player){
		if(sheepStackMap.get(player) == null)
			sheepStackMap.put(player, new ArrayList<>());
		return sheepStackMap.get(player);
	}
	
	public ArrayList<SheepStack> getSheepStackPlayer(){
		return sheepStackMap.get(currentPlayer);
	}
	
	public ArrayList<SheepStack> getSheepStackEnemy(){
		return sheepStackMap.get(getNextPlayer(currentPlayer));
	}

	public boolean isTurnFinish(){
		if(timer != null)
			return timer.checkTime();
		return false;
		}
	
	public boolean isPlayerHuman(){
		return getCurrentPlayer() instanceof HumanPlayer;
			
	}
	
	public void resetTimer(){ if(timer != null) timer.restart();}
	
	public void addPlayer(Player player){
		players.add(player);
		if(currentPlayer == null)
			currentPlayer = player;
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
		currentPlayer = players.get(currentPlayerIndex);
	}
	
	public int getPlayerCount(){ return players.size(); }
	
	public Player getCurrentPlayer(){ return currentPlayer; }
	
	public boolean isCurrentPlayer(Player player){ return currentPlayer.equals(player); }
	
	public boolean isCurrentPlayer(int index){ return index == currentPlayerIndex; }
	
	public Player getNextPlayer(){
		return players.get( (currentPlayerIndex + 1) % players.size()); 
		}
	
	public Player getNextPlayer(Player player){ 
		int tempIndex = 0;
		for(int i = 0; i < players.size(); i++)
			if(players.get(i).equals(player))
				tempIndex = i;
		return players.get( (tempIndex + 1) % players.size()); 
		}
	
	public int getPlayerIndex(Player player){
		for(int i = 0; i < players.size(); i++)
			if(players.get(i).equals(player))
				return i;
		return 0; 
	}
	
	public Player getPlayer(int index){return players.get(index % players.size()); }
	
	public Player getPlayer(String name){
		for(Player p: players)
			if(p.getName().equalsIgnoreCase(name))
				return p;
		return null;
		}
	
	public void setPlayer(String name, int hexID){
		Player player = getPlayer(name);
		SheepStack selectedSheep = null;
		SheepStack playerSheep = null;
		int playerIndex = getPlayerIndex(player);
		ArrayList<SheepStack> playerStack = getSheepStack(player);
		ArrayList<SheepStack> enemyStack = getSheepStack(getNextPlayer(player));
		
		for(SheepStack e: enemyStack)
		if(e.getHexSpaceID() == hexID)
			selectedSheep = e;
	
		for(SheepStack p: playerStack){
				playerSheep = p;
			if(p.getHexSpaceID() == hexID)
				selectedSheep = p;
		}
		
		// space is unclaimed
		if(selectedSheep == null){
			if(playerSheep != null)
				playerStack.remove(playerSheep);
			if(playerStack.size() == 0){
				players.remove(playerIndex);
				players.add(0, player);
				setCurrentPlayer(player);
			}
			playerStack.add(new SheepStack(hexID, INIT_SHEEP_SIZE, player));
			
		}
		
		// space claimed by player
		else {
			if(selectedSheep.getOwner().equals(player)){
				if(playerStack.size() == 1){
					setCurrentPlayer(null);
					playerStack.remove(selectedSheep);
				} else
				enemyStack.remove(selectedSheep);
		// space claimed by other player
			} else {
				enemyStack.remove(selectedSheep);
				if(playerSheep != null)
					playerStack.remove(playerSheep);
				playerStack.add(new SheepStack(hexID, INIT_SHEEP_SIZE, player));
				if(playerStack.size() == 1){
					players.remove(playerIndex);
					players.add(0, player);
					setCurrentPlayer(player);
				}
			}
		}
		//System.out.println(sheepStacks.size());
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
			x += neighbor.getPosition().getX();
			y += neighbor.getPosition().getY();
			
		map.get(id).getPosition().setX(x);
		map.get(id).getPosition().setY(y);
	}
	
	public boolean removeHex(int id){
		if(GameState.getInstance().getHexList().size() <= 1)
			return false;
		HexSpace currHex = map.get(id);
		if(currHex == null)
			return false;
		if(currTurn != null){
		for(SheepStack s: ((BattleSheepState)currTurn).getSheepStackPlayer())
			if(s.getHexSpaceID() == id)
				return false;
		for(SheepStack s: ((BattleSheepState)currTurn).getSheepStackEnemy())
			if(s.getHexSpaceID() == id)
				return false;
		}
		for(HexSpace neighbor : currHex.getNeighbors())
			if(neighbor != null)
				neighbor.removeNeighbor(currHex);
		for(int i = 0 ; i < hexList.size(); i++)
			if(hexList.get(i).equals(currHex)){
				hexList.remove(i);
				break;
			}
		map.remove(id);
		return true;
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

	public State getCurrentTurn() {
		return currTurn;
	}

	public void setCurrentTurn(State currTurn) {
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

	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public SimpleTimer getStateChangeTimer() {
		return stateChangeTimer;
	}

	public void setStateChangeTimer(SimpleTimer stateChangeTimer) {
		this.stateChangeTimer = stateChangeTimer;
	}

	public HashMap<Player, ArrayList<SheepStack>> getSheepStackMap() {
		return sheepStackMap;
	}

	public void setSheepStackMap(HashMap<Player, ArrayList<SheepStack>> sheepStackMap) {
		this.sheepStackMap = sheepStackMap;
	}

}
