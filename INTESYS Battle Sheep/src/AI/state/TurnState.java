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
		ArrayList<State> result = new ArrayList<>();
		ArrayList<SheepStack> tempSheepStack;
		

		
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
