package AI.state;

import java.util.ArrayList;

public abstract class State {
	
	protected int score;
	private State parent;

	public abstract ArrayList<State> getNextStates();
	public abstract boolean equals(State state);
	public abstract boolean isValid();
	public abstract boolean isFinal();
	public abstract void printState();
	
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }
	public State getParent() {
		return parent;
	}
	public void setParent(State parent) {
		this.parent = parent;
	}

}
