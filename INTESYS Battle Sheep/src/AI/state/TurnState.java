package AI.state;

import java.util.ArrayList;

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

	@Override
	public ArrayList<State> getNextStates() {
		ArrayList<State> result = new ArrayList<>();
		ArrayList<SheepStack> tempSheepStack;
		
		
		
		return result;
	}

	@Override
	public boolean equals(State state) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFinal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void printState() {
		// TODO Auto-generated method stub
		
	}

}
