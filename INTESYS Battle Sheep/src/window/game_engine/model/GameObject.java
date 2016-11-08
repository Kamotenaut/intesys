package window.game_engine.model;

public class GameObject {

	protected int x, y;
	
	public GameObject(){
		x = 0;
		y = 0;
	}
	
	public void setCoords(int x, int y){
		this.x = x;
		this.y = y;
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
