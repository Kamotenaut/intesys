package utils;

public class SimpleTimer {

	public static final double TO_SECONDS = 1000000000.0;
	
	private long prevTime;
	private long waitTime;
	private boolean result = false;
	//private boolean status = false;
	
	public SimpleTimer(long wait){
		waitTime = wait;
		prevTime = System.nanoTime();
	}
	
	public void restart(){
		prevTime = System.nanoTime();
		setResult(false);
	}
	
	public boolean checkTime(){ // true if set time has passed
		if(System.nanoTime() - prevTime > waitTime){
			setResult(true);
			return true;
			}
		setResult(false);
		return false;
	}

	public boolean getResult() {
		return result;
	}

	private void setResult(boolean result) {
		this.result = result;
	}
}