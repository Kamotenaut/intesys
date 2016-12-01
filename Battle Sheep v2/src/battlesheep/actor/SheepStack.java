package battlesheep.actor;

import battlesheep.GameState;
import jGame.model.game.GameObject;
import jGame.model.graphics.Camera;
import jGame.model.input.Input;
import jGame.view.Renderer;

public class SheepStack implements GameObject{
	
	private int numSheep;
	private int spaceID;
	private Player owner;
	private HexSpace space;
	
	public SheepStack(int spaceID, int numSheep, Player owner){
		this.spaceID = spaceID;
		this.numSheep = numSheep;
		this.owner = owner;
		this.space = null;
	}
	
	// returns true if divide is successful (eg before div sheep at least 2)
	public boolean divide(int numberToTake){
		if(numSheep == 1 && numberToTake >= numSheep)
			return false;
		numSheep -= numberToTake;
		return true;
	}
	
	public SheepStack clone(){
		return new SheepStack(spaceID, numSheep, owner);
	}
	
	public boolean equals(SheepStack sheepStack){
		if(spaceID != sheepStack.getHexSpaceID())
			return false;
		if(!owner.equals(sheepStack.getOwner()))
			return false;
		return numSheep == sheepStack.getNumberOfSheep();
	}
	
	public HexSpace getHexSpace(){
		if(space == null)
			space = GameState.getInstance().getHexSpace(spaceID);
		return space; 
		}

	public int getNumberOfSheep() {
		return numSheep;
	}

	public void setNumberOfSheep(int numSheep) {
		this.numSheep = numSheep;
	}

	public int getHexSpaceID() {
		return spaceID;
	}

	public void setSpaceID(int spaceID) {
		this.spaceID = spaceID;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	@Override
	public void input(Input input, long deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logic(long deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Renderer renderer, Camera camera) {

		float x = getHexSpace().getPosition().getX();
		float y = getHexSpace().getPosition().getY();
		renderer.getRendIn().setColor(owner.getColor());
		renderer.getRendIn().drawPolygon(getHexSpace().generateSprite(camera));
		renderer.getRendIn().drawString("ID: "+getHexSpace().getId(),
				camera.getPosition().getX() + x - 14,
				camera.getPosition().getY() + y - 14);
		renderer.getRendIn().drawString("S: "+getNumberOfSheep(), 
				camera.getPosition().getX() + x - 24,
				camera.getPosition().getY() + y + 3);
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	
}
