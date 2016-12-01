package battlesheep.actor;

import java.awt.Color;

import battlesheep.GameState;
import battlesheep.AI.solver.BattleSheepSolver;


public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color, Color color2) {
		super(name, color, color2);
		// TODO Auto-generated constructor stub
	}


	private BattleSheepSolver ai;
	

	@Override
	public void doTurn() {
		System.out.println("COMPUTER TURN");
		ai = new BattleSheepSolver(GameState.getInstance().getCurrentTurn().clone());
		ai.start();
	}

}
