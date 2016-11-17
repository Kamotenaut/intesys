package window.game_engine.Input;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;

public class Input {

	private boolean[] keyMap;
	private boolean[] mouseMap;
	private int[] mouseCoords;
	
	private int numOfMouseButtons;
	
	public Input(){
		numOfMouseButtons = MouseInfo.getNumberOfButtons();
		setKeyMap(new boolean[KeyEvent.KEY_LAST + 1]);
		setMouseMap(new boolean[numOfMouseButtons + 1]);
		setMouseCoords(new int[2]);
	}

	public int getMouseX(){return mouseCoords[0]; }
	public int getMouseY(){return mouseCoords[1]; }
	
	public void setMouseCoords(int x, int y){
		mouseCoords[0] = x;
		mouseCoords[1] = y;
	}
	
	public void setMouseButton(int button, boolean value){
		if(!(button < 0 || button > numOfMouseButtons))
			mouseMap[button] = value;
	}
	
	public boolean getMouseButton(int button){
		if(button < 0 || button > numOfMouseButtons)
			return false;
		return mouseMap[button];
	}

	public boolean[] getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(boolean[] keyMap) {
		this.keyMap = keyMap;
	}
	
	public boolean getKey(int key){
		if(key < 0 || key > KeyEvent.KEY_LAST)
			return false;
		return keyMap[key];
	}
	
	public void setKey(int key, boolean value){
		if(!(key < 0 || key > KeyEvent.KEY_LAST))
		keyMap[key] = value;
	}

	public boolean[] getMouseMap() {
		return mouseMap;
	}

	public void setMouseMap(boolean[] mouseMap) {
		this.mouseMap = mouseMap;
	}

	public int[] getMouseCoords() {
		return mouseCoords;
	}

	public void setMouseCoords(int[] mouseCoords) {
		this.mouseCoords = mouseCoords;
	}
	
	
}
