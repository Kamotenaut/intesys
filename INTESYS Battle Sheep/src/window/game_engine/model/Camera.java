package window.game_engine.model;

public class Camera {

	private int x, y;
	private int speed;
	
	public static int NO_LIMIT = -1;
	
	private int top_limit, bottom_limit, left_limit, right_limit;

	public Camera(int x, int y, int speed){
		setX(x);
		setY(y);
		setSpeed(speed);
		setTop_limit(NO_LIMIT);
		setBottom_limit(NO_LIMIT);
		setLeft_limit(NO_LIMIT);
		setRight_limit(NO_LIMIT);
	}
	
	public void process(boolean up, boolean down, boolean left, boolean right){
		if(up)
			y = y - speed;
		if(down)
			y = y + speed;
		if(left)
			x = x - speed;
		if(right)
			x = x + speed;

		if(y > bottom_limit && bottom_limit > 0 && bottom_limit != NO_LIMIT)
			y = bottom_limit;
		if(y < top_limit && top_limit < 0 && top_limit != NO_LIMIT)
			y = top_limit;
		if(x < left_limit && left_limit < 0 && left_limit != NO_LIMIT)
			x = left_limit;
		if(x > right_limit && right_limit > 0 && right_limit != NO_LIMIT)
			x = right_limit;
	}
	
	public void reset(){
		x = y = 0;}
	
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getLeft_limit() {
		return left_limit;
	}

	public void setLeft_limit(int left_limit) {
		this.left_limit = -left_limit;
	}

	public int getBottom_limit() {
		return bottom_limit;
	}

	public void setBottom_limit(int bottom_limit) {
		this.bottom_limit = bottom_limit;
	}

	public int getTop_limit() {
		return top_limit;
	}

	public void setTop_limit(int top_limit) {
		this.top_limit = -top_limit;
	}

	public int getRight_limit() {
		return right_limit;
	}

	public void setRight_limit(int right_limit) {
		this.right_limit = right_limit;
	}
	
}
