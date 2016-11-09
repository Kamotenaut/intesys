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
		GameState.getInstance().addHexSpace(1);
		GameState.getInstance().addHexSpace(2);
		GameState.getInstance().addHexSpace(3);
		GameState.getInstance().addHexSpace(4);
		GameState.getInstance().addHexSpace(5);
		GameState.getInstance().addHexSpace(6);
		GameState.getInstance().addHexSpace(7);
		GameState.getInstance().addHexSpace(8);
		GameState.getInstance().addHexSpace(9);
		GameState.getInstance().addHexSpace(10);
		GameState.getInstance().addHexSpace(11);
		GameState.getInstance().addHexSpace(12);
		GameState.getInstance().addHexSpace(13);
		GameState.getInstance().addHexSpace(14);
		GameState.getInstance().addHexSpace(15);
		GameState.getInstance().addHexSpace(16);
		// Set space 1 as neighbors to space 2
		// setting a neighbor for one space will auto set for its neighbor
		GameState.getInstance().setHexNeighbor(1, 4, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(4, 2, HexSpace.NE);
		GameState.getInstance().setHexNeighbor(2, 5, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(5, 3, HexSpace.NE);

		GameState.getInstance().setHexNeighbor(1, 6, HexSpace.S);
		GameState.getInstance().setHexNeighbor(2, 7, HexSpace.S);
		GameState.getInstance().setHexNeighbor(3, 8, HexSpace.S);

		GameState.getInstance().setHexNeighbor(6, 4, HexSpace.NE);
		GameState.getInstance().setHexNeighbor(4, 7, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(7, 5, HexSpace.NE);
		GameState.getInstance().setHexNeighbor(5, 8, HexSpace.SE);


		
		GameState.getInstance().setHexNeighbor(6, 11, HexSpace.S);
		GameState.getInstance().setHexNeighbor(7, 12, HexSpace.S);
		GameState.getInstance().setHexNeighbor(8, 13, HexSpace.S);

		GameState.getInstance().setHexNeighbor(6, 9, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(11, 9, HexSpace.NE);
		GameState.getInstance().setHexNeighbor(9, 12, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(7, 10, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(12, 10, HexSpace.NE);
		GameState.getInstance().setHexNeighbor(10, 13, HexSpace.SE);

		GameState.getInstance().setHexNeighbor(4, 9, HexSpace.S);
		GameState.getInstance().setHexNeighbor(5, 10, HexSpace.S);
		GameState.getInstance().setHexNeighbor(9, 14, HexSpace.S);

		GameState.getInstance().setHexNeighbor(11, 16, HexSpace.S);
		GameState.getInstance().setHexNeighbor(10, 15, HexSpace.S);
		GameState.getInstance().setHexNeighbor(10, 8, HexSpace.NE);
		

		GameState.getInstance().setHexNeighbor(11, 14, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(14, 12, HexSpace.NE);
		GameState.getInstance().setHexNeighbor(12, 15, HexSpace.SE);
		GameState.getInstance().setHexNeighbor(15, 13, HexSpace.NE);
		
		GameState.getInstance().setHexNeighbor(16, 14, HexSpace.NE);
		
		
		// add players
		Player p1 = new ComputerPlayer("P1", Color.BLUE);
		Player p2 = new ComputerPlayer("P2", Color.RED);
		GameState.getInstance().addPlayer(p1);
		GameState.getInstance().addPlayer(p2);
		
		// Create initial SheepStacks
		ArrayList<SheepStack> sheepStacks = new ArrayList<>();
		sheepStacks.add(new SheepStack(6, 8, p1));
		sheepStacks.add(new SheepStack(8, 8, p2));
		
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
