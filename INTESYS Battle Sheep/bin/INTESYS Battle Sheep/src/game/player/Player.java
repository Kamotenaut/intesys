package game.player;

import java.awt.Color;

public abstract class Player {
	
	private String name;
	private Color color;
	private int turnNo;
	
	public Player(String name, Color color){
		setName(name);
		setColor(color);
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
