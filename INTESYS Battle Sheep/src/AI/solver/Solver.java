package AI.solver;

import AI.state.State;

public abstract class Solver implements Runnable {

	private Thread thread;
	
	public Solver(){
		thread = new Thread(this, "Solver Thread");
	}
	
	public void start(){
		thread.start();
		run();
	}
	
	public abstract void run();
	
}
