package game;

public class HexSpace {
	
	public static int MAX_NEIGHBORS = 6;
	
	public static int N = 0;
	public static int NE = 1;
	public static int NW = 2;
	public static int S = 3;
	public static int SW = 4;
	public static int SE = 5;
	
	
	private HexSpace[] neighbors;
	private int id;
	
	public HexSpace(int id){
		neighbors = new HexSpace[MAX_NEIGHBORS];
		for(int i = 0; i < MAX_NEIGHBORS; i++)
			neighbors[i] = null;
		this.id = id;
	}
	
	public HexSpace(int id, HexSpace[] neighbors){
		this.neighbors = neighbors;
		this.id = id;
	}
	
	public void setNeighbor(int direction, HexSpace neighbor){
		neighbors[direction] = neighbor;
	}
	
	public HexSpace getNeighbor(int direction){
		return neighbors[direction];
	}
	
	public HexSpace getFarNeigbor(int direction){
		HexSpace result = neighbors[direction];
		if(result != null)
		while(result.getNeighbor(direction) != null)
			result = result.getNeighbor(direction);
		
		return result;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
