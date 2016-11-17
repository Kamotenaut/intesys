package window.game_engine.model;

import java.awt.Graphics2D;

import window.game_engine.math.vector.Vector3f;
import window.game_engine.model.camera.Camera;

public abstract class GameObject {
	
	public static final int NO_CAP = -1;
	
	private Vector3f position;
	private Vector3f rotation;
	private Vector3f scale; // acceleration vector
	
	// Movement
	private Vector3f accelVector; // acceleration vector
	private Vector3f speedVector; // acceleration vector
	
	// max speed and acceleration
	private float speedMax;
	private float accelMax;
	
	// amount of decay
	private float accelDecay;
	
	public GameObject(){
		setAccelVector(new Vector3f());
		setSpeedVector(new Vector3f());
		setScale(new Vector3f());
		setPosition(new Vector3f());
		setRotation(new Vector3f());
		setSpeedMax(NO_CAP);
		setAccelMax(NO_CAP);
		setAccelDecay(0);
	}
	
	public abstract void input();
	public abstract void update(float deltaTime);
	public abstract void render(Graphics2D rendIn, Camera camera);
	
	// acceleration decay (friction, drag or whatever)
	
	public void applyAccelerationDecay(){
		if(accelDecay > 0.0f){
			if(getAccelVector().getX() < 0.0f){
				incAccelerationX(accelDecay);
				if(getAccelVector().getX() > 0.0f)
					setAccelerationX(0.0f);
			} else if(getAccelVector().getX() > 0.0f) {
				incAccelerationX(-accelDecay);
				if(getAccelVector().getX() < 0.0f)
					setAccelerationX(0.0f);
				}
			
			if(getAccelVector().getY() < 0.0f){
				incAccelerationY(accelDecay);
				if(getAccelVector().getY() > 0.0f)
					setAccelerationY(0.0f);
			} else if(getAccelVector().getY() > 0.0f) {
				incAccelerationY(-accelDecay);
				if(getAccelVector().getY() < 0.0f)
					setAccelerationY(0.0f);
				}
			
			if(getAccelVector().getZ() < 0.0f){
				incAccelerationZ(accelDecay);
				if(getAccelVector().getZ() > 0.0f)
					setAccelerationZ(0.0f);
			} else if(getAccelVector().getZ() > 0.0f) {
				incAccelerationZ(-accelDecay);
				if(getAccelVector().getZ() < 0.0f)
					setAccelerationZ(0.0f);
				}
		}
	}
	
	// limit functions
	
	public void limitAcceleration(){
		if(accelMax != NO_CAP){
		if(getAccelVector().getX() > accelMax)
			getAccelVector().setX(accelMax);
		else if(getAccelVector().getX() < -accelMax)
			getAccelVector().setX(-accelMax);
		
		if(getAccelVector().getY() > accelMax)
			getAccelVector().setY(accelMax);
		else if(getAccelVector().getY() < -accelMax)
			getAccelVector().setY(-accelMax);
		
		if(getAccelVector().getZ() > accelMax)
			getAccelVector().setZ(accelMax);
		else if(getAccelVector().getZ() < -accelMax)
			getAccelVector().setZ(-accelMax);
		}
	}
	
	public void limitSpeed(){
		if(speedMax != NO_CAP){
		if(getSpeedVector().getX() > speedMax)
			getSpeedVector().setX(speedMax);
		else if(getSpeedVector().getX() < -speedMax)
			getSpeedVector().setX(-speedMax);
		
		if(getSpeedVector().getY() > speedMax)
			getSpeedVector().setY(speedMax);
		else if(getSpeedVector().getY() < -speedMax)
			getSpeedVector().setY(-speedMax);
		
		if(getSpeedVector().getZ() > speedMax)
			getSpeedVector().setZ(speedMax);
		else if(getSpeedVector().getZ() < -speedMax)
			getSpeedVector().setZ(-speedMax);
		}
	}
	
	// update functions
	
	public void updatePosition(float deltaTime){
		// velocity = acceleration * time
		applyAccelerationDecay();
		limitAcceleration();
		getSpeedVector().incValue(accelVector.multiply(deltaTime));
		limitSpeed();
		// distance = velocity * time
		incPositionX(getSpeedVector().getX() * deltaTime);
		incPositionY(getSpeedVector().getY() * deltaTime);
		incPositionZ(getSpeedVector().getZ() * deltaTime);
	}
	
	// reset functions
	
	public void reset(){
		accelVector.setValue(0.0f, 0.0f, 0.0f);
		speedVector.setValue(0.0f, 0.0f, 0.0f);
		position.setValue(0.0f, 0.0f, 0.0f);
		rotation.setValue(0.0f, 0.0f, 0.0f);
	}
	
	public void resetPosition(){
		position.setValue(0.0f, 0.0f, 0.0f);
	}
	
	public void resetRotation(){
		rotation.setValue(0.0f, 0.0f, 0.0f);
	}
	
	public void resetAcceleration(){
		accelVector.setValue(0.0f, 0.0f, 0.0f);
	}
	
	public void resetSpeed(){
		speedVector.setValue(0.0f, 0.0f, 0.0f);
	}
	
	// Increment functions

	public void incPositionX(float x){ position.incX(x); }
	public void incPositionY(float y){ position.incY(y); }
	public void incPositionZ(float z){ position.incZ(z); }
	
	public void incRotationX(float x){ rotation.incX(x); }
	public void incRotationY(float y){ rotation.incY(y); }
	public void incRotationZ(float z){ rotation.incZ(z); }
	
	// Getters and setters

	public Vector3f getPosition() {
		return position;
	}
	
	public float getPositionX(){ return position.getX(); }
	public float getPositionY(){ return position.getY(); }
	public float getPositionZ(){ return position.getZ(); }

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public void setPosition(float x, float y, float z){
		position.setValue(x, y, z);
	}
	

	public void setPositionX(float x){ position.setX(x); }
	public void setPositionY(float y){ position.setY(y); }
	public void setPositionZ(float z){ position.setZ(z); }

	public Vector3f getRotation() {
		return rotation;
	}

	public float getRotationX(){ return rotation.getX(); }
	public float getRotationY(){ return rotation.getY(); }
	public float getRotationZ(){ return rotation.getZ(); }

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public void setRotation(float x, float y, float z){
		rotation.setValue(x, y, z);
	}
	
	public void setRotationX(float x){ rotation.setX(x); }
	public void setRotationY(float y){ rotation.setY(y); }
	public void setRotationZ(float z){ rotation.setZ(z); }
	
	public Vector3f getAccelVector() {
		return accelVector;
	}
	
	public void incAccelerationX(float x){ accelVector.incX(x); }
	public void incAccelerationY(float y){ accelVector.incY(y); }
	public void incAccelerationZ(float z){ accelVector.incZ(z); }

	public void setAccelerationX(float x){ accelVector.setX(x); }
	public void setAccelerationY(float y){ accelVector.setY(y); }
	public void setAccelerationZ(float z){ accelVector.setZ(z); }
	
	public void setAccelVector(Vector3f accelVector) {
		this.accelVector = accelVector;
	}

	public Vector3f getSpeedVector() {
		return speedVector;
	}

	public void setSpeedVector(Vector3f speedVector) {
		this.speedVector = speedVector;
	}

	public Vector3f getScale() {
		return scale;
	}
	
	public float getScaleX(){ return scale.getX(); }
	public float getScaleY(){ return scale.getY(); }
	public float getScaleZ(){ return scale.getZ(); }

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}
	
	public void setScaleX(float x){ scale.setX(x); }
	public void setScaleY(float y){ scale.setY(y); }
	public void setScaleZ(float z){ scale.setZ(z); }

	public float getSpeedMax() {
		return speedMax;
	}

	public void setSpeedMax(float speedMax) {
		this.speedMax = speedMax;
	}

	public float getAccelMax() {
		return accelMax;
	}

	public void setAccelMax(float accelMax) {
		this.accelMax = accelMax;
	}

	public float getAccelDecay() {
		return accelDecay;
	}

	public void setAccelDecay(float accelDecay) {
		this.accelDecay = accelDecay;
	}

}
