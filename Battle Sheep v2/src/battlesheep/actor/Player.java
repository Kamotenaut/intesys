package battlesheep.actor;

import java.awt.Color;

import jGame.model.game.GameObject;

public abstract class Player{
	
	private String name;
	private Color color;
	private Color color2;
	
	public Player(String name, Color color, Color color2){
		setName(name);
		setColor(color);
		setColor2(color2);
	}
	
	public boolean equals(Player player){ return name.equalsIgnoreCase(player.getName());}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void doTurn();

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}

}
