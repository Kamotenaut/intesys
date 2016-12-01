package jGame.model.graphics;

import jGame.model.math.Matrix4f;
import jGame.model.math.Vector3f;

public class Camera {

	public static final float DEF_LIMIT = 4000f;
	public static final float DEF_ACCEL = 300f;
	
	private Vector3f position;
	
	private float pitch;
	private float yaw;
	private float roll;
	
	// Movement
	private Vector3f accelVector; // acceleration vector
	private float maxSpeed, accel;
	
	public Camera(){
		accelVector = new Vector3f();
		position = new Vector3f();
		roll = yaw = pitch = 0.0f;
		maxSpeed = DEF_LIMIT;
		accel = DEF_ACCEL;
		
	}
	
	public void resetCamera(){
		accelVector.setValue(0.0f, 0.0f, 0.0f);
		position.setValue(0.0f, 0.0f, 0.0f);
		roll = yaw = pitch = 0.0f;
		maxSpeed = DEF_LIMIT;
		accel = DEF_ACCEL;
	}

	public void setPosition(Vector3f pos){
		position = pos;
	}
	
	public void setPosition(float x, float y, float z){position.setValue(x, y, z);}
	
	public Vector3f getPosition() {
		return position;
	}
	public void reset(){resetPosition();resetRotation();}
	public void resetPosition(){position.setValue(0.0f, 0.0f, 0.0f);}
	public void resetRotation(){setPitch(0.0f);setRoll(0.0f);setYaw(0.0f);}
	public void incPitch(float pitch){this.pitch += pitch;}
	public void incYaw(float yaw){this.yaw += yaw;}
	public void incRoll(float roll){this.roll += roll;}
	
	public void setPitch(float pitch){this.pitch = pitch;}
	public void setYaw(float yaw){this.yaw = yaw;}
	public void setRoll(float roll){this.roll = roll;}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void logic(long deltaTime){
		
		if(accelVector.getX() > maxSpeed)
			accelVector.setX(maxSpeed);
		if(accelVector.getX() < -maxSpeed)
			accelVector.setX(-maxSpeed);
		if(accelVector.getY() > maxSpeed)
			accelVector.setY(maxSpeed);
		if(accelVector.getY() < -maxSpeed)
			accelVector.setY(-maxSpeed);

		position.incValue(accelVector.multiply(deltaTime / 1000000000.0f));

	}
	
	public void accelDecayX(float accel, boolean status){
		if(status)
		if (accelVector.getX() < 0.0f) {
			accelerateX(accel);
			if (accelVector.getX() >= 0.0f) 
				accelVector.setX(0.0f);
		} else {
			accelerateX(-accel);
			if (accelVector.getX() <= 0.0f) 
				accelVector.setX(0.0f);
		}
	}
	
	public void accelDecayY(float accel, boolean status){
		if(status)
		if (accelVector.getY() < 0.0f) {
			accelerateY(accel);
			if (accelVector.getY() >= 0.0f) 
				accelVector.setY(0.0f);
		} else {
			accelerateY(-accel);
			if (accelVector.getY() <= 0.0f) 
				accelVector.setY(0.0f);
		}
	}
	
	public void accelDecayZ(float accel, boolean status){
		if(status)
			if (accelVector.getZ() < 0.0f) {
				accelerateZ(accel);
				if (accelVector.getZ() >= 0.0f) 
					accelVector.setZ(0.0f);
			} else {
				accelerateZ(-accel);
				if (accelVector.getZ() <= 0.0f) 
					accelVector.setZ(0.0f);
			}
	}
	
	public void accelerateX(float accel){
		accelVector.incX(accel);
	}
	
	public void accelerateY(float accel){
		accelVector.incY(accel);
	}
	
	public void accelerateZ(float accel){
		accelVector.incZ(accel);
	}

	public Vector3f getAccelVector() {
		return accelVector;
	}

	public void setAccelVector(Vector3f accelVector) {
		this.accelVector = accelVector;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public float getAccel() {
		return accel;
	}

	public void setAccel(float accel) {
		this.accel = accel;
	}
}
