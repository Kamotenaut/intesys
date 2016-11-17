package utils;

public class SimpleTimer {

	public static final double TO_SECONDS = 1000000000.0;
	
	private long prevTime;
	private long waitTime;
	private long lastTime;
	private boolean result = false;
	//private boolean status = false;
	
	public SimpleTimer(long wait){
		waitTime = (long) (wait);
		prevTime = System.nanoTime();
		lastTime = 0;
	}
	
	public void restart(){
		prevTime = System.nanoTime();
		setResult(false);
	}
	public long checkCurrentTime(){
		return result? lastTime: System.nanoTime() - prevTime; 
	}
	
	public boolean checkTime(){ // true if set time has passed
		if(System.nanoTime() - prevTime > waitTime){
			setResult(true);
			lastTime = System.nanoTime() - prevTime;
			return true;
			}
		setResult(false);
		return false;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
}