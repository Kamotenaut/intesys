package AI.state;

import java.util.ArrayList;

import game.GameState;
import game.HexSpace;
import game.player.Player;
import game.SheepStack;

public class TurnState extends State{
	
	public static final int MIN = 0;
	public static final int MAX = 1;
	
	private ArrayList<SheepStack> sheepStacks;
	private Player player;
	
    private int childrenLeft;
    private boolean stop = false;
    private int gameScore = 0;
    private int type;
    private int depthcount = 0;
    private State bestChild;
    
	public TurnState(Player player){
		sheepStacks = new ArrayList<>();
		this.player = player;
		setParent(null);
		setChildrenLeft(0);
		type = GameState.getInstance().isCurrentPlayer(player) ? MAX : MIN;
	}
	
	public TurnState(Player player, ArrayList<SheepStack> sheepStacks){
		this.sheepStacks = sheepStacks;
		this.player = player;
		setParent(null);
		setChildrenLeft(0);
		type = GameState.getInstance().isCurrentPlayer(player) ? MAX : MIN;
	}
	
	public TurnState(Player player, ArrayList<SheepStack> sheepStacks, State parent){
		this.sheepStacks = sheepStacks;
		this.player = player;
		setParent(parent);
		setChildrenLeft(0);
		type = GameState.getInstance().isCurrentPlayer(player) ? MAX : MIN;
	}
	
	public boolean isSpaceOccupied(HexSpace space){
		for(SheepStack s:sheepStacks)
			if(s.getHexSpaceID() == space.getId())
				return true;
		return false;
	}
	
	public boolean isSpaceOccupied(HexSpace space, ArrayList<SheepStack> sheepStacks){
		for(SheepStack s:sheepStacks)
			if(s.getHexSpaceID() == space.getId())
				return true;
		return false;
	}
	
	public HexSpace getFarthestUnoccupiedSpace(int direction, HexSpace space, ArrayList<SheepStack> sheepStacks){
		
		HexSpace result = space.getNeighbor(direction);
		HexSpace prev = null;
		if(result == null)
			return null;
		if(isSpaceOccupied(result, sheepStacks))
			return null;
		prev = result;
		while(result != null && prev != null){
			prev = result;
			if(result.getNeighbor(direction) != null)
			if(isSpaceOccupied(result.getNeighbor(direction), sheepStacks))
				return prev;
			
			result = result.getNeighbor(direction);
		}
		if(result == null)
			return prev;
		return result;
	}

	public boolean moveSheep(int direction, int numberToMove , int sheepStackIndex, ArrayList<SheepStack> sheepStacks){

		HexSpace space = getFarthestUnoccupiedSpace(direction, sheepStacks.get(sheepStackIndex).getHexSpace(), sheepStacks);
		if(space != null){
			if(sheepStacks.get(sheepStackIndex).divide(numberToMove))
				sheepStacks.add(new SheepStack(space.getId(), numberToMove, sheepStacks.get(sheepStackIndex).getOwner()));
		return true;	
		}
		return false;
	}
	
	public boolean moveSheep(int fromSpace, int toSpace, int numSheep, Player player){
		if(!GameState.getInstance().getCurrentPlayer().equals(player))
			return false;
		HexSpace result = GameState.getInstance().getHexSpace(fromSpace);
		HexSpace prev = null;
		if(result == null)
			return false;
		for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++){
			result = GameState.getInstance().getHexSpace(fromSpace).getNeighbor(direction);
			if(result == null)
				continue;
			if(isSpaceOccupied(result))
				continue;
			prev = result;
			while(result != null && prev != null){
				prev = result;
				if(result.getId() == toSpace){
					for(SheepStack s:sheepStacks)
						if(s.getHexSpaceID() == fromSpace)
							if(s.divide(numSheep)){
								sheepStacks.add(new SheepStack(result.getId(), numSheep, s.getOwner()));
								return true;
							} else return false;
							
				}
				//System.out.println(result.getId());
				result = result.getNeighbor(direction);
			}
		}
		return false;
					
	}
	
	@Override
	public ArrayList<State> getNextStates() {
		int child = 0;
		ArrayList<State> result = new ArrayList<>();
		ArrayList<SheepStack> tempSheepStack;
		boolean quit = false;
		for(int sheepStackIndex = 0; sheepStackIndex < sheepStacks.size() && !quit; sheepStackIndex++){
			// if the stack belongs to the current player and the numSheep is > 1
			if(sheepStacks.get(sheepStackIndex).getOwner().equals(player) && sheepStacks.get(sheepStackIndex).getNumberOfSheep() > 1){
				// move sheep in 6 directions
				for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS && !quit; direction++){
					//System.out.println("T");
					// iterate using the number of sheep
					for(int numberToMove = sheepStacks.get(sheepStackIndex).getNumberOfSheep() - 1; numberToMove > 0  && !quit; numberToMove--){
						// create new ArrayList of sheep stacks and deep copy
						tempSheepStack = new ArrayList<>();
						for(SheepStack s: sheepStacks)
							tempSheepStack.add(s.clone());
						// move the sheep
						if(moveSheep(direction, numberToMove, sheepStackIndex, tempSheepStack)){
							// add to children
							child++;
							result.add(new TurnState(GameState.getInstance().getNextPlayer(), tempSheepStack, this));
                                                        ((TurnState)result.get(result.size()-1)).setDepthcount(this.depthcount++);
                                                        if(this.type == 1)
                                                            ((TurnState)result.get(result.size()-1)).setType(0);
                                                        else
                                                            ((TurnState)result.get(result.size()-1)).setType(1);
							if(GameState.getInstance().isTurnFinish()) quit = true;
						}
					}
				}
			}
				
		}
		childrenLeft = child;
		return result;
	}
	
	public boolean isLeaf(){
		int freeNeighbors;
		int numNeighbors;
		for(SheepStack s: sheepStacks)
			if(s.getNumberOfSheep() > 1){
				numNeighbors = 0;
				for(HexSpace h : s.getHexSpace().getNeighbors())
					if(h != null)
						numNeighbors++;
				for(HexSpace h : s.getHexSpace().getNeighbors())
					if(h != null){
						freeNeighbors = numNeighbors;
						for(SheepStack compare: sheepStacks){
							if(compare.getHexSpaceID() == h.getId())
								freeNeighbors--;
							
						}
						if(freeNeighbors > 0)
							return false;
								}
			}
		
        return true;
    }
    
    public void computeScore(){
    	int takenSpaces = 0;
    	int spacesWithSingleSheep = 0;
    	int numEnemySpaces = 0;
        int enemyNeighbors = 0;
        int heuristicScore = 0;
    	
        for(SheepStack s: sheepStacks)
        	if(s.getOwner().equals(player)){
        		takenSpaces++;
        		if(s.getNumberOfSheep() > 3)
        		spacesWithSingleSheep++;
                        /*for(int i=0; i<6; i++){
                            if(s.getHexSpace().getNeighbor(i)!=null && !(s.getHexSpace().getNeighbor(i).){
                                enemyNeighbors++;
                            }
                        }*/
                    /*numEmptySpaces = this.getSheepStack(s.getNumberOfSheep()).getHexSpace().getEmptyNeighbors();
                    numEnemySpaces = 10*(6-numEmptySpaces);
                    if(gameScore<numEmptySpaces+numEnemySpaces)
                        gameScore = numEmptySpaces+numEnemySpaces;*/
        	} else if(s.getOwner()!=null)numEnemySpaces++;
        
       
        heuristicScore = takenSpaces * 100 + spacesWithSingleSheep * 10 - numEnemySpaces;
	

        setScore(heuristicScore);
        propagateScore();
        
    }	
    
    public void propagateScore(){
        if(parent!=null /*&& childrenLeft==0*/)
            ((TurnState) parent).submit(this);
    }
    
    public void submit(State s){
        if(getType() == Integer.MIN_VALUE){
            gameScore=Math.min(gameScore,((TurnState)s).gameScore);
        } else{
            gameScore=Math.max(gameScore, ((TurnState)s).gameScore);
        }
        //gameScore = ((TurnState)s).gameScore;
        /*if(childrenLeft==0){
            propagateScore();
        } else{
            if(type==MAX && ((TurnState) parent).getGameScore()<=gameScore){
                stop=true;
            }
            else if(type==MIN&&gameScore<=((TurnState) parent).getGameScore()){
                stop = true;
            }
        }*/
        childrenLeft--;
        setScore(s.getScore());
        propagateScore();
        
    }

	@Override
	public boolean equals(State state) {
		TurnState other = (TurnState)state;
		if(!player.equals(other.getPlayer()))
			return false;
		if(sheepStacks.size() != other.getSheepStacks().size() )
			return false;
		for(int i = 0; i < sheepStacks.size(); i++)
			if(sheepStacks.get(i).equals(other.getSheepStack(i)))
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
		return isLeaf();
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		
	}
	
	public TurnState clone(){
		ArrayList<SheepStack> tempSheepStacks = new ArrayList<>();
		for(SheepStack s: sheepStacks)
			tempSheepStacks.add(s.clone());
		TurnState result = new TurnState(player, tempSheepStacks, parent);
		result.setChildrenLeft(childrenLeft);
		result.setStop(stop);
		result.setGameScore(gameScore);
		result.setType(type);
		result.setScore(score);
		return result;
	}
	public void setStop(boolean stop){this.stop = stop;}
	public void setGameScore(int gameScore){this.gameScore = gameScore;}
	public void setType(int type){this.type = type;}
	
	public boolean isStop(){return stop;}
	public void setPlayer(Player player){this.player = player;}
	public Player getPlayer(){return player;}
	public ArrayList<SheepStack> getSheepStacks(){return sheepStacks;}
	public SheepStack getSheepStack(int index){return sheepStacks.get(index);}

	public int getChildrenLeft() {
		return childrenLeft;
	}

	public void setChildrenLeft(int childrenLeft) {
		this.childrenLeft = childrenLeft;
	}
	
	public int getGameScore(){return gameScore; }

    public int getDepthcount() {
        return depthcount;
    }

    public void setDepthcount(int depthcount) {
        this.depthcount = depthcount;
    }
    
    public int getType(){return GameState.getInstance().getCurrentPlayer().equals(player) ? Integer.MAX_VALUE : Integer.MIN_VALUE;}

}
