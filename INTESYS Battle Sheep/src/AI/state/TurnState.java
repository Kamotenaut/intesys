package AI.state;

import java.util.ArrayList;

import game.GameState;
import game.HexSpace;
import game.Player;
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
	
	public TurnState(Player player, ArrayList<SheepStack> sheepStacks, TurnState parent){
		this.sheepStacks = sheepStacks;
		this.player = player;
		setParent(parent);
		setChildrenLeft(0);
		type = GameState.getInstance().isCurrentPlayer(player) ? MAX : MIN;
	}
	
	public static HexSpace getFarthestUnoccupiedSpace(int direction, HexSpace space, ArrayList<SheepStack> sheepStacks){
		HexSpace result = space.getNeighbor(direction);
		boolean isDone = false;
		do{
			if(result != null){
				for(SheepStack s:sheepStacks)
					if(s.getHexSpaceID() == result.getId())
						return null;
			} else isDone = true;
			if(result.getNeighbor(direction) != null)
				result = result.getNeighbor(direction);
			else isDone = true;
		}while(!isDone);
		return result;
	}

	public static boolean moveSheep(int direction, int numberToMove , int sheepStackIndex, ArrayList<SheepStack> sheepStacks){
		HexSpace space = getFarthestUnoccupiedSpace(direction, sheepStacks.get(sheepStackIndex).getHexSpace(), sheepStacks);
		if(space != null){
			if(sheepStacks.get(sheepStackIndex).divide(numberToMove))
				sheepStacks.add(new SheepStack(space.getId(), numberToMove, sheepStacks.get(sheepStackIndex).getOwner()));
		return true;	
		}
		return false;
	}
	
	@Override
	public ArrayList<State> getNextStates() {
		int child = 0;
		ArrayList<State> result = new ArrayList<>();
		ArrayList<SheepStack> tempSheepStack;

		for(int sheepStackIndex = 0; sheepStackIndex > sheepStacks.size(); sheepStackIndex++){
			// if the stack belongs to the current player and the numSheep is > 1
			if(sheepStacks.get(sheepStackIndex).getOwner().equals(player) && sheepStacks.get(sheepStackIndex).getNumberOfSheep() > 1){
				// move sheep in 6 directions
				for(int direction = 0; direction < HexSpace.MAX_NEIGHBORS; direction++){
					// iterate using the number of sheep
					for(int numberToMove = sheepStacks.get(sheepStackIndex).getNumberOfSheep() - 1; numberToMove > 0; numberToMove--){
						// create new ArrayList of sheep stacks and deep copy
						tempSheepStack = new ArrayList<>();
						for(SheepStack s: sheepStacks)
							tempSheepStack.add(s.clone());
						// move the sheep
						if(moveSheep(direction, numberToMove, sheepStackIndex, tempSheepStack)){
							// add to children
							child++;
							result.add(new TurnState(GameState.getInstance().getNextPlayer(player), tempSheepStack, this));
						}
					}
				}
			}
				
		}
		childrenLeft = child;
		return result;
	}
	
	public boolean isLeaf(){
		
        return sheepStacks.size() == GameState.getInstance().getHexSpaceCount();
    }
    
    public void computeScore(){
        if(type == MIN)
            gameScore = Integer.MIN_VALUE;
        else
	gameScore = Integer.MAX_VALUE;
	
        propagateScore();
	
        setScore(gameScore);
    }
    
    public void propagateScore(){
        if(parent!=null && childrenLeft==0)
            ((TurnState) parent).submit(this);
    }
    
    public void submit(State s){
        if(type==MIN){
            gameScore=Math.min(gameScore,((TurnState)s).gameScore);
        } else{
            gameScore=Math.max(gameScore, ((TurnState)s).gameScore);
        }
        if(childrenLeft==0){
            propagateScore();
        } else{
            if(type==MAX && ((TurnState) parent).getGameScore()<=gameScore){
                stop=true;
            }
            else if(type==MIN&&gameScore<=((TurnState) parent).getGameScore()){
                stop = true;
            }
        }
        
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

}
