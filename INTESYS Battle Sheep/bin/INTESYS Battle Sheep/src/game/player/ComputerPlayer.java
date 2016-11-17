package game.player;

import java.awt.Color;

import AI.solver.TurnSolver;
import AI.state.State;
import game.GameState;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Color color) {
		super(name, color);
		// TODO Auto-generated constructor stub
	}


	private TurnSolver ai;
	

	@Override
	public void doTurn() {
		ai = new TurnSolver(GameState.getInstance().getCurrentTurn().clone());
		ai.start();
	}

}
