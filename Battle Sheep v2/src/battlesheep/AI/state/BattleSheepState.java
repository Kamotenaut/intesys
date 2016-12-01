package battlesheep.AI.state;

import java.util.ArrayList;

import battlesheep.GameState;
import battlesheep.actor.HexSpace;
import battlesheep.actor.Player;
import battlesheep.actor.SheepStack;

public class BattleSheepState extends State{

	public static final int STATUS_WIN = 0;
	public static final int STATUS_LOSE = 1;

	public static final int MAX_DEPTH = 4;
	
	private ArrayList<SheepStack> sheepStacks;
	private Player player;
	
	public BattleSheepState(Player player, ArrayList<SheepStack> sheepStacks){
		this.sheepStacks = sheepStacks;
		this.player = player;
		setParent(null);
		setDepth(MAX_DEPTH);
		setChildren(0);
		setScore(GameState.getInstance().getCurrentPlayer().equals(player) ? Integer.MIN_VALUE: Integer.MAX_VALUE);
	}
	
	public BattleSheepState(Player player, ArrayList<SheepStack> sheepStacks, State parent, int depth){
		this.sheepStacks = sheepStacks;
		this.player = player;
		setParent(parent);
		setDepth(depth);
		setChildren(0);
		setScore(GameState.getInstance().getCurrentPlayer().equals(player) ? Integer.MIN_VALUE: Integer.MAX_VALUE);
	}
	
	@Override
	public ArrayList<State> getNextStates() {
		ArrayList<State> result = new ArrayList<>();
		ArrayList<SheepStack> tempSheeps;
		HexSpace currSpace = null;
		Player nextPlayer = GameState.getInstance().getNextPlayer(player);
		for(int sheepIndex = 0; sheepIndex < sheepStacks.size(); sheepIndex++)
			if(sheepStacks.get(sheepIndex).getOwner().equals(player))
				if(sheepStacks.get(sheepIndex).getNumberOfSheep() > 1)
					for(int sheepNo = sheepStacks.get(sheepIndex).getNumberOfSheep() - 1; sheepNo > 0; sheepNo--)
						for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++)
							if((currSpace = getHexSpace(sheepStacks.get(sheepIndex).getHexSpace(), direction)) != null){
								tempSheeps = cloneSheepStacks();
								move(tempSheeps, sheepIndex, currSpace, sheepNo, player);
								result.add(new BattleSheepState(nextPlayer, tempSheeps, this, depth - 1));
								children++;
							}
		return result;
	}

	@Override
	public boolean equals(State state) {
		if(!player.equals(((BattleSheepState)state).getPlayer()))
			return false;
		if(sheepStacks.size() != ((BattleSheepState)state).getSheepStacks().size() )
			return false;
		for(int i = 0; i < sheepStacks.size(); i++)
			if(sheepStacks.get(i).equals(((BattleSheepState)state).getSheepStacks().get(i)))
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
		if(depth == 0)
			return true;
		return !hasPossibleMove(player);
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
	}
	
	public void propagateScore(){
        if(parent!=null && children <=0)
            ((BattleSheepState) parent).submit(this);
    }
	
	public void submit(BattleSheepState currentState){
		boolean isMax = GameState.getInstance().getCurrentPlayer().equals(player);
        if(isMax)
            score = Math.max(score,currentState.getScore());
        else
            score = Math.min(score, currentState.getScore());
        children--;
        if(parent != null)
            if(isMax && score >= parent.getScore() || !isMax && score <= parent.getScore()){
                propagateScore();
            }
        
        
    }
	
	public void calculateScore(){
		int heuristic = 0;
		
		// taken spaces near enemy (low sheep no)
		int x = 0;
		// taken spaces in clear area (high sheep no)
		int y = 0;
		
		ArrayList<SheepStack> playerSheep = new ArrayList<>();
		ArrayList<SheepStack> enemySheep = new ArrayList<>();
		
		for(SheepStack s: sheepStacks)
			if(s.getOwner().equals(player))
				playerSheep.add(s);
			else
				enemySheep.add(s);
		
		for(SheepStack player: playerSheep)
			for(SheepStack enemy: enemySheep){
				if(player.getHexSpace().isNeighbor(enemy.getHexSpace()))
					x += GameState.INIT_SHEEP_SIZE - player.getNumberOfSheep() * 1000;
				y+= player.getHexSpace().countEmptyNeighbors() * 300;
			}
				
		
		
				
				
		
		heuristic = x + y;
		
		setScore(score + heuristic);
		propagateScore();
	}

	
	public void move(ArrayList<SheepStack> allSheep, int sheepIndex, HexSpace destination, int sheepNo, Player owner){
			
		allSheep.get(sheepIndex).setNumberOfSheep(allSheep.get(sheepIndex).getNumberOfSheep() - sheepNo);
		allSheep.add(new SheepStack(destination.getId(), sheepNo, owner));
				
	}
	
	public void move(ArrayList<SheepStack> allSheep, SheepStack sheepSource, HexSpace destination, int sheepNo, Player owner){
		
		sheepSource.setNumberOfSheep(sheepSource.getNumberOfSheep() - sheepNo);
		allSheep.add(new SheepStack(destination.getId(), sheepNo, owner));
				
	}
	
	public ArrayList<SheepStack> cloneSheepStacks(){
		ArrayList<SheepStack> result = new ArrayList<>();
		for(SheepStack s: sheepStacks)
			result.add(s.clone());
		return result;
	}
	
	public boolean isOccupied(int hexID){
		for(SheepStack s: sheepStacks)
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
		int playerSheep = 0;
		int enemySheep = 0;
		
		for(SheepStack s: sheepStacks)
			if(s.getOwner().equals(player)){
				playerSheep++;
			} else {
				enemySheep++;
			}
		if(playerSheep > enemySheep)
			return STATUS_WIN;
		return STATUS_LOSE;
	}
	
	public boolean hasPossibleMove(Player player){
		for(SheepStack s: sheepStacks)
			if(s.getOwner().equals(player))
				for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++)
					if(getHexSpace(s.getHexSpace(), direction) != null)
						return true;
		return false;
	}

	@Override
	public BattleSheepState clone(){
		BattleSheepState result = new BattleSheepState(player, cloneSheepStacks(), parent, depth);
		result.setChildren(children);
		result.setScore(score);
		return result;
	}
	
	public ArrayList<SheepStack> getSheepStacks() {
		return sheepStacks;
	}

	public void setSheepStacks(ArrayList<SheepStack> sheepStacks) {
		this.sheepStacks = sheepStacks;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
