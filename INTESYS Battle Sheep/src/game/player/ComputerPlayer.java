package game.player;

import AI.solver.TurnSolver;
import AI.state.State;
import game.GameState;

public class ComputerPlayer extends Player {

	private TurnSolver ai;
	
	public ComputerPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doTurn() {
		ai = new TurnSolver(GameState.getInstance().getCurrentTurn().clone());
		ai.start();
	}

}
