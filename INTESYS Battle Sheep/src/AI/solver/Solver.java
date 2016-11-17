package AI.solver;

import java.util.ArrayList;

import AI.state.State;

public abstract class Solver {
	
	public Solver(){
	}
	
	public void start(){
		run();
	}
	
	public abstract void init();
	
	public abstract void process();
	
	public abstract void end();
	
	public void run(){
		init();
		process();
		end();
	}
	
	public abstract State getBestState(ArrayList<State> list);
	
}
