package game;

import game.player.Player;

public class SheepStack {
	
	private int numSheep;
	private int spaceID;
	private Player owner;
	
	public SheepStack(int spaceID, int numSheep, Player owner){
		this.spaceID = spaceID;
		this.numSheep = numSheep;
		this.owner = owner;
	}
	
	// returns true if divide is successful (eg before div sheep at least 2)
	public boolean divide(int numberToTake){
		if(numSheep == 1 && numberToTake >= numSheep)
			return false;
		numSheep -= numberToTake;
		return true;
	}
	
	public SheepStack clone(){
		return new SheepStack(spaceID, numSheep, owner);
	}
	
	public boolean equals(SheepStack sheepStack){
		if(spaceID != sheepStack.getHexSpaceID())
			return false;
		if(!owner.equals(sheepStack.getOwner()))
			return false;
		return numSheep == sheepStack.getNumberOfSheep();
	}
	
	public HexSpace getHexSpace(){
		return GameState.getInstance().getHexSpace(spaceID); 
		}

	public int getNumberOfSheep() {
		return numSheep;
	}

	public void setNumberOfSheep(int numSheep) {
		this.numSheep = numSheep;
	}

	public int getHexSpaceID() {
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

    public int getSpaceID() {
        return spaceID;
    }
	
}
