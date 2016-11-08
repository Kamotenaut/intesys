package AI.state;

import java.util.ArrayList;

import game.GameState;
import game.HexSpace;
import game.Player;
import game.SheepStack;

public class TurnState extends State{
	
	ArrayList<SheepStack> sheepStacks;
	Player player;
	
	public TurnState(Player player){
		sheepStacks = new ArrayList<>();
		this.player = player;
	}
	
	public TurnState(Player player, ArrayList<SheepStack> sheepStacks){
		this.sheepStacks = sheepStacks;
		this.player = player;
	}

	public static void moveSheep(int direction, int numberToMove , int sheepStackIndex, ArrayList<SheepStack> sheepStacks){
		HexSpace space;
		if((space = sheepStacks.get(sheepStackIndex).getHexSpace().getFarNeigbor(direction)) != null)
			if(sheepStacks.get(sheepStackIndex).divide(numberToMove))
				sheepStacks.add(new SheepStack(space.getId(), numberToMove, sheepStacks.get(sheepStackIndex).getOwner()));
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
						moveSheep(direction, numberToMove, sheepStackIndex, tempSheepStack);
						
						// add to children
						child++;
						result.add(new TurnState(GameState.getInstance().getNextPlayer(player), tempSheepStack));
					}
				}
			}
				
		}
		
		return result;
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
		return sheepStacks.size() == GameState.getInstance().getHexSpaceCount();
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		
	}
	
	public Player getPlayer(){return player;}
	public ArrayList<SheepStack> getSheepStacks(){return sheepStacks;}
	public SheepStack getSheepStack(int index){return sheepStacks.get(index);}

}
