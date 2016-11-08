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
		 *  \_/ \_/ \_/
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
		
		// add players
		Player p1 = new HumanPlayer("P1", Color.BLUE);
		Player p2 = new ComputerPlayer("P2", Color.RED);
		GameState.getInstance().addPlayer(p1);
		GameState.getInstance().addPlayer(p2);
		
		// Create initial SheepStacks
		ArrayList<SheepStack> sheepStacks = new ArrayList<>();
		sheepStacks.add(new SheepStack(1, 4, p1));
		sheepStacks.add(new SheepStack(8, 4, p2));
		
		// set up initial state and timer
		GameState.getInstance().init(GameState.getInstance().getCurrentPlayer(), GameState.NO_WAIT, sheepStacks);
		
		// test start game
		new GameWindow("Battle Sheeps", 740, 540, 15).start();
		
		//GameState.getInstance().autoRunPlayer();
	}

}
