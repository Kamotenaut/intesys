package AI.solver;


public abstract class Solver implements Runnable {

	private Thread thread;
	
	public Solver(){
		thread = new Thread(this, "Solver Thread");
	}
	
	public void start(){
		thread.start();
	}
	
	public void run(){
		
	}
	
}
