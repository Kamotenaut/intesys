package game.player;

import AI.state.State;

public abstract class Player {
	
	private String name;
	private int turnNo;
	
	public Player(String name){
		setName(name);
	}
	
	public boolean equals(Player player){ return turnNo == player.getTurnNumber();}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTurnNumber() {
		return turnNo;
	}
	public void setTurnNumber(int turnNo) {
		this.turnNo = turnNo;
	}
	
	public abstract void doTurn();
}
