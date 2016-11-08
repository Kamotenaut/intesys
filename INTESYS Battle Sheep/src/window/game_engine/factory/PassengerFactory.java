package elevator_simulator.game_engine.factory;

import elevator_simulator.game_engine.model.Passenger;

public class PassengerFactory {

	private static PassengerFactory instance = null;
	
	private PassengerFactory(){}
	
	public static synchronized PassengerFactory getInstance(){
		if(instance == null)
			instance = new PassengerFactory();
		return instance;
	}
	
	public Passenger createPassenger(int destination, int weight, int patience, boolean downPreference){
		return new Passenger(BuildingFactory.getInstance().createBuilding().generatePassengerId(), destination, weight, patience, downPreference);
	}
	
}
