package battlesheep.actor;

import jGame.model.graphics.Camera;

public class SingleCamera extends Camera{

	private static SingleCamera instance = null;
	
	private SingleCamera(){}
	
	public static synchronized SingleCamera getInstance(){
		if(instance == null)
			instance = new SingleCamera();
		return instance;
	}
	
}
