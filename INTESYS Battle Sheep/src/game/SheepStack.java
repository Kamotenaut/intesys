package game;

public class SheepStack {

	private int numSheep;
	private int spaceID;
	private Player owner;
	
	public SheepStack(int spaceID, int numSheep, Player owner){
		this.spaceID = spaceID;
		this.numSheep = numSheep;
		this.owner = owner;
	}

	public int getNumSheep() {
		return numSheep;
	}

	public void setNumSheep(int numSheep) {
		this.numSheep = numSheep;
	}

	public int getSpaceID() {
		return spaceID;
	}

	public void setSpaceID(int spaceID) {
		this.spaceID = spaceID;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
}
