package game.player;

import java.awt.Color;

import game.GameState;

public class HumanPlayer extends Player{


	public HumanPlayer(String name, Color color) {
		super(name, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doTurn() {
		GameState.getInstance().setTurnOver(true);
	}

}
