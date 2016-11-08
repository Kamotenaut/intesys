package AI.solver;

import java.util.ArrayList;

import AI.state.State;

public abstract class Solver implements Runnable {

	protected Thread thread;
	
	public Solver(){
		thread = new Thread(this, "Computer AI Thread");
	}
	
	public void start(){
		thread.start();
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
