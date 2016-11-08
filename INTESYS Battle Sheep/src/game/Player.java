package game;

public class Player {

	private String name;
	private int turnNo;
	
	
	
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
	
}
