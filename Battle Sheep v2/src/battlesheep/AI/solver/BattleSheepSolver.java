package battlesheep.AI.solver;

import java.util.ArrayList;

import battlesheep.GameState;
import battlesheep.AI.state.BattleSheepState;
import battlesheep.AI.state.State;

public class BattleSheepSolver extends Solver{

	State initState, currState;
	private ArrayList<State> explore, visited, children, next;
	private boolean childrenListFilled;
	
	public BattleSheepSolver(State initState){
        super();
        this.initState = initState.clone();
    }
	
	@Override
	public void init() {
		explore = new ArrayList<>();
		visited = new ArrayList<>();
		children = new ArrayList<>();
		explore.add(initState);
		childrenListFilled = false;
	}

	@Override
	public void process() {
		GameState.getInstance().resetTimer();


		while(!explore.isEmpty()){

            currState = explore.get(0);
            explore.remove(0);
            visited.add(currState);
            
            if(currState.isFinal() || GameState.getInstance().isTurnFinish()){
            	((BattleSheepState)currState).calculateScore();
            } else {
            	if(!GameState.getInstance().isTurnFinish()){
            	next = currState.getNextStates();
            	for(State state: next){
            		if(!containsState(state, visited) && !containsState(state, explore)){
                        explore.add(state);
                        if(!childrenListFilled)
                        	children.add(state);
                    }
                
                if(!childrenListFilled)
                	childrenListFilled = true;
            	}
            	}
            }
		}
		
	}

	@Override
	public void end() {
		GameState.getInstance().resetTimer();
		GameState.getInstance().getTimer().setResult(true);
		State temp = GameState.getInstance().getCurrentTurn();
		if(children.size() != 0)
			temp = getBestState(children);
		GameState.getInstance().setCurrentTurn(temp);
		GameState.getInstance().nextTurn();
		GameState.getInstance().setTurnOver(true);
	}

	@Override
	public State getBestState(ArrayList<State> list) {
		int index = 0;
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getScore() >= list.get(index).getScore()){
				index = i;
			}
		}
		return list.remove(index);
	}
	
	public boolean containsState(State s, ArrayList<State> list){
		for(State l: list)
			if(l.equals(s))
				return true;
		return false;
	}

}
