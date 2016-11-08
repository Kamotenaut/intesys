package game.player;

import java.awt.Color;
import java.util.Scanner;

import AI.state.TurnState;
import game.GameState;

public class HumanPlayer extends Player{

	private Scanner sc;
	
	public HumanPlayer(String name, Color color) {
		super(name, color);
		sc = new Scanner(System.in);
	}

	@Override
	public void doTurn() {
		TurnState result = GameState.getInstance().getCurrentTurn().clone();
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
		
		success = result.moveSheep(fromSpace, toSpace, numSheep);
		if(!success)
			System.out.println("Input may not be valid... ");
		}while(!success);
		GameState.getInstance().setCurrentTurn(result);
		GameState.getInstance().setTurnOver(true);
	}

}
