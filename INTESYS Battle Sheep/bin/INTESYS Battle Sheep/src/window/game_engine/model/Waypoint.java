package window.game_engine.model;

public class Waypoint {
	
	private int x, y;
	
	public Waypoint(int x, int y){
		setX(x);
		setY(y);
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
}
