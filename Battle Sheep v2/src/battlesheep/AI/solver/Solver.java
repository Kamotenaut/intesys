package battlesheep.AI.solver;

import java.util.ArrayList;

import battlesheep.AI.state.State;


public abstract class Solver implements Runnable{
	
	Thread thread;
	
	public Solver(){
	}
	
	public void start(){
		thread = new Thread(this, "AI Solver - Thread");
		thread.start();
	}
	
	public abstract void init();
	
	public abstract void process();
	
	public abstract void end();
	
	@Override
	public void run(){
		init();
		process();
		end();
	}
	
	public abstract State getBestState(ArrayList<State> list);
	
}
