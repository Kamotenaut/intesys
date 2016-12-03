package battlesheep.AI.state;

import java.util.ArrayList;
import java.util.Random;

import battlesheep.GameState;
import battlesheep.actor.HexSpace;
import battlesheep.actor.Player;
import battlesheep.actor.SheepStack;

public class BattleSheepState extends State{

	public static final int STATUS_WIN = 1;
	public static final int STATUS_NONE = 0;
	public static final int STATUS_LOSE = -1;

	public static final int MAX_DEPTH = 4;
	
	private ArrayList<SheepStack> sheepStackEnemy;
	private ArrayList<SheepStack> sheepStackPlayer;
	
	private Player player;
	
	public BattleSheepState(Player player, ArrayList<SheepStack> sheepStackPlayer, ArrayList<SheepStack> sheepStackEnemy){
		this.sheepStackPlayer = sheepStackPlayer;
		this.sheepStackEnemy = sheepStackEnemy;
		this.player = player;
		setParent(null);
	}
	
	public BattleSheepState(Player player, ArrayList<SheepStack> sheepStackPlayer, ArrayList<SheepStack> sheepStackEnemy, State parent, int depth){
		this.sheepStackPlayer = sheepStackPlayer;
		this.sheepStackEnemy = sheepStackEnemy;
		this.player = player;
		setParent(parent);
	}
	
	@Override
	public ArrayList<State> getNextStates() {
		ArrayList<State> result = new ArrayList<>();
		boolean stop = false;
		if(!stop){
		ArrayList<SheepStack> tempSheeps;
		HexSpace currSpace = null;
		Player nextPlayer = GameState.getInstance().getNextPlayer(player);
		for(int sheepIndex = 0; sheepIndex < sheepStackPlayer.size() && !stop; sheepIndex++)
				if(sheepStackPlayer.get(sheepIndex).getNumberOfSheep() > 1)
					for(int sheepNo = sheepStackPlayer.get(sheepIndex).getNumberOfSheep() - 1 ; sheepNo > 0  && !stop; sheepNo--)
						for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS  && !stop; direction++)
							if((currSpace = getHexSpace(sheepStackPlayer.get(sheepIndex).getHexSpace(), direction)) != null){
								tempSheeps = cloneSheepStackPlayer();
								move(tempSheeps, sheepIndex, currSpace, sheepNo, player);
								result.add(new BattleSheepState(nextPlayer, cloneSheepStackEnemy(), tempSheeps, this, depth - 1));
								children++;
								stop = GameState.getInstance().isTurnFinish();
							}
		}
		return result;
	}

	@Override
	public boolean equals(State state) {
		if(!player.equals(((BattleSheepState)state).getPlayer()))
			return false;
		if(sheepStackPlayer.size() != ((BattleSheepState)state).getSheepStackPlayer().size() )
			return false;
		if(sheepStackEnemy.size() != ((BattleSheepState)state).getSheepStackEnemy().size() )
			return false;
		for(int i = 0; i < sheepStackPlayer.size(); i++)
			if(sheepStackPlayer.get(i).equals(((BattleSheepState)state).getSheepStackPlayer().get(i)))
				return false;
		for(int i = 0; i < sheepStackEnemy.size(); i++)
			if(sheepStackEnemy.get(i).equals(((BattleSheepState)state).getSheepStackEnemy().get(i)))
				return false;
		
		return true;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinal() {
		return !hasPossibleMove(player);
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
	}
	
	public boolean isMax(){
		return GameState.getInstance().getCurrentPlayer().equals(player);
	}
	
	public void calculateScore(){
		int heuristic = 0;
		
		// taken spaces near enemy (low sheep no)
		int x = 0;
		// taken spaces in clear area (high sheep no)
		int y = 0;
		
		for(SheepStack player: sheepStackPlayer)
			for(SheepStack enemy: sheepStackEnemy){
				if(player.getHexSpace().isNeighbor(enemy.getHexSpace()))
					x += (GameState.INIT_SHEEP_SIZE - player.getNumberOfSheep()) * 400;
				y+= player.getHexSpace().countEmptyNeighbors() * 300;
			}
				
		heuristic = x + y;
		
		int status ;
		setScore(heuristic);
		
	}

	
	public void move(ArrayList<SheepStack> allSheep, int sheepIndex, HexSpace destination, int sheepNo, Player owner){
			
		allSheep.get(sheepIndex).setNumberOfSheep(allSheep.get(sheepIndex).getNumberOfSheep() - sheepNo);
		allSheep.add(new SheepStack(destination.getId(), sheepNo, owner));
				
	}
	
	public void move(ArrayList<SheepStack> allSheep, SheepStack sheepSource, HexSpace destination, int sheepNo, Player owner){
		
		sheepSource.setNumberOfSheep(sheepSource.getNumberOfSheep() - sheepNo);
		allSheep.add(new SheepStack(destination.getId(), sheepNo, owner));
				
	}
	
	public ArrayList<SheepStack> cloneSheepStackPlayer(){
		ArrayList<SheepStack> result = new ArrayList<>();
		for(SheepStack s: sheepStackPlayer)
			result.add(s.clone());
		return result;
	}
	
	public ArrayList<SheepStack> cloneSheepStackEnemy(){
		ArrayList<SheepStack> result = new ArrayList<>();
		for(SheepStack s: sheepStackEnemy)
			result.add(s.clone());
		return result;
	}
	
	public boolean isOccupied(int hexID){
		for(SheepStack s: sheepStackPlayer)
			if(s.getHexSpaceID() == hexID)
				return true;
		for(SheepStack s: sheepStackEnemy)
			if(s.getHexSpaceID() == hexID)
				return true;
		return false;
	}
	
	public ArrayList<HexSpace> validNextMoves(HexSpace space){
		ArrayList<HexSpace> result = new ArrayList<>();
		HexSpace temp;
		
		for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++)
			if((temp = getHexSpace(space, direction)) != null)
				result.add(temp);
		
		return result;
		
	}
	
	public HexSpace getHexSpace(HexSpace space, int direction){
		HexSpace result = space.getNeighbor(direction);
		HexSpace prev = space;
		if(result != null){
		if(isOccupied(result.getId()))
			return null;
		while(prev.getNeighbor(direction) != null && !isOccupied(result.getId())){
			prev = result;
			result = result.getNeighbor(direction);
			}
		result = prev;
		}
		return result;
	}
	
	public int getGameStatus(){
		int playerSheep = sheepStackPlayer.size();
		int enemySheep = sheepStackEnemy.size();
		
		if(playerSheep > enemySheep)
			return STATUS_WIN;
		return STATUS_LOSE;
	}
	
	public boolean hasPossibleMove(Player player){
		if(player.equals(this.player)){
			for(SheepStack s: sheepStackPlayer)
				if(s.getNumberOfSheep() > 1)
					for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++)
						if(getHexSpace(s.getHexSpace(), direction) != null)
							return true;
		} else{
			for(SheepStack s: sheepStackEnemy)
				if(s.getNumberOfSheep() > 1)
					for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++)
						if(getHexSpace(s.getHexSpace(), direction) != null)
							return true;
		}
		return false;
	}

	@Override
	public BattleSheepState clone(){
		BattleSheepState result = new BattleSheepState(player, cloneSheepStackPlayer(), cloneSheepStackEnemy(), parent, depth);
		result.setChildren(children);
		result.setScore(score);
		return result;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<SheepStack> getSheepStackEnemy() {
		return sheepStackEnemy;
	}

	public void setSheepStackEnemy(ArrayList<SheepStack> sheepStackEnemy) {
		this.sheepStackEnemy = sheepStackEnemy;
	}

	public ArrayList<SheepStack> getSheepStackPlayer() {
		return sheepStackPlayer;
	}

	public void setSheepStackPlayer(ArrayList<SheepStack> sheepStackPlayer) {
		this.sheepStackPlayer = sheepStackPlayer;
	}

}
