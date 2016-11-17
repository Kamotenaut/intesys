package window.game_engine.math.vector;

public class Vector3f {
private float[] array;
	

	public Vector3f(){
		array = new float[3];
		array[0] = array[1] = array[2] = 0.0f;
	}
	
	public Vector3f(float value){
		array = new float[3];
		array[0] =  value;
		array[1] = value;
		array[2] = value;
	}
	
	public Vector3f(float x, float y) {
		array = new float[3];
		array[0] = x;
		array[1] = y;
		array[2] = 0.0f;
	}
	
	public Vector3f(float x, float y, float z) {
		array = new float[3];
		array[0] = x;
		array[1] = y;
		array[2] = z;
	}
	
	public void setValue(Vector3f v) {
		array[0] = v.getX();
		array[1] = v.getY();
		array[2] = v.getZ();
	}
	
	public void setValue(float x, float y, float z) {
		array[0] = x;
		array[1] = y;
		array[2] = z;
	}
	
	public Vector3f multiply(float value){
		return new Vector3f(array[0] * value, array[1] * value, array[2] * value);
	}
	
	public void incValue(Vector3f v) {
		array[0] += v.getX();
		array[1] += v.getY();
		array[2] += v.getZ();
	}
	
	public void incValue(float x, float y, float z) {
		array[0] += x;
		array[1] += y;
		array[2] += z;
	}
	
	public Vector3f invert(){
		return new Vector3f(-array[0], -array[1], -array[2]);
	}
	
	public Vector3f incValue(Vector2f v2){
		return new Vector3f(getX() + v2.getX(), getY() + v2.getY(), getZ());
	}
	
	public Vector3f getValue(){return this;}
	
	public void incX(float x){this.array[0] += x;}
	public void incY(float y){this.array[1] += y;}
	public void incZ(float z){this.array[2] += z;}
	
	public float getX(){return array[0];}
	public float getY(){return array[1];}
	public float getZ(){return array[2];}

	public void setX(float x){this.array[0] = x;}
	public void setY(float y){this.array[1] = y;}
	public void setZ(float z){this.array[2] = z;}
}
