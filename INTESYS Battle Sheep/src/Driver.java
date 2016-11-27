import java.awt.Color;
import java.util.ArrayList;

import AI.state.TurnState;
import game.GameState;
import game.HexSpace;
import game.SheepStack;
import game.player.*;
import window.GameWindow;

public class Driver {

	public static void main(String[] args) {
		/*
		 * sample map
		 *    
		 *  /1\_/2\_/3\
		 *  \ /4\ /5\ /
		 *  /6\_/7\_/8\
		 *  \ /9\ /10 /
		 *  /11_/12_/13
		 *  \ /14_/15_/
		 *  /16_/
		 *  
		 *  
		 *  
		 * */
		
		// add all spaces first
		GameState.getInstance().addHexSpace(0);
		GameState.getInstance().addHexSpace(1);
		// Set space 1 as neighbors to space 2
		// setting a neighbor for one space will auto set for its neighbor
		GameState.getInstance().setHexNeighbor(0, 1, HexSpace.NE);
		
		
		// add players
		Player p1 = new HumanPlayer("P1", Color.BLUE);
		Player p2 = new ComputerPlayer("P2", Color.RED);
		GameState.getInstance().addPlayer(p1);
		GameState.getInstance().addPlayer(p2);
		
		// Create initial SheepStacks
		ArrayList<SheepStack> sheepStacks = new ArrayList<>();
		sheepStacks.add(new SheepStack(0, 8, p1));
		sheepStacks.add(new SheepStack(1, 8, p2));
		
		// set up initial state and timer
		GameState.getInstance().init(
				GameState.getInstance().getCurrentPlayer(), 
				3, 
				sheepStacks);
		
		// test start game
		new GameWindow("Battle Sheeps", 740, 540, 15).start();
		
		GameState.getInstance().autoRunPlayer();
	}

}
