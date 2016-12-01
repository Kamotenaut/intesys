package battlesheep.actor;

import java.awt.Color;
import java.util.Scanner;

import battlesheep.GameState;
import battlesheep.AI.state.State;
import battlesheep.AI.state.TurnState;


public class HumanPlayer extends Player{

	//private Scanner sc;
	
	public HumanPlayer(String name, Color color, Color color2) {
		super(name, color, color2);
		//sc = new Scanner(System.in);
	}

	@Override
	public void doTurn() {
		System.out.println("HUMAN TURN");
		/*
		State result = GameState.getInstance().getCurrentTurn().clone();
		int fromSpace = 0;
		int numSheep = 0;
		int toSpace = 0;
		boolean success = false;
		do{
		System.out.println("Enter what space you wish to select. ");
		fromSpace = sc.nextInt();
		System.out.println("Enter what space you wish to move sheep to. ");
		toSpace = sc.nextInt();
		System.out.println("Enter How many sheep you wish to move. ");
		numSheep = sc.nextInt();
		
		success = result.moveSheep(fromSpace, toSpace, numSheep, this);
		if(!success)
			System.out.println("Input may not be valid... ");
		}while(!success);
		result.setPlayer(GameState.getInstance().getNextPlayer());
		GameState.getInstance().setCurrentTurn(result);
		GameState.getInstance().nextTurn();
		GameState.getInstance().setTurnOver(true);
		*/
	}

}
