package intesys;

public class SheepStack {
	private int numOfSheep;
	private int player; //0 is us by default;
	
	public SheepStack(int numOfSheep) {
		this.numOfSheep = numOfSheep;
	}

	public int getNumOfSheep() {
		return numOfSheep;
	}

	public void setNumOfSheep(int numOfSheep) {
		this.numOfSheep = numOfSheep;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
	
	
}
