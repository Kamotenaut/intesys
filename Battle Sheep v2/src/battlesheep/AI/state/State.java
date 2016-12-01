package battlesheep.AI.state;

import java.util.ArrayList;

public abstract class State {
	
	protected int score;
	protected State parent;
	protected int children;
	protected int depth;

	public abstract ArrayList<State> getNextStates();
	public abstract boolean equals(State state);
	public abstract boolean isValid();
	public abstract boolean isFinal();
	public abstract void printState();
	public abstract State clone();
	
	public int getScore() { return score; }
	public void setScore(int score) { this.score = score; }
	public State getParent() {
		return parent;
	}
	public void setParent(State parent) {
		this.parent = parent;
	}
	public int getChildren() {
		return children;
	}
	public void setChildren(int children) {
		this.children = children;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}

}
