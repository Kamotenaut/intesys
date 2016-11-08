package AI.solver;

import AI.state.*;
import game.GameState;

import java.util.ArrayList;

public class TurnSolver extends Solver{
    
    private State initState, currState;
    
    private ArrayList<State> explore, visited, next, children;
    private boolean isInitialChildren;
    
    public TurnSolver(TurnState initState){
        super();
        this.initState = initState;
    }

	@Override
	public void init() {
		explore = new ArrayList<>();
		visited = new ArrayList<>();
		next = new ArrayList<>();
		children = new ArrayList<>();
        explore.add(initState);
        isInitialChildren = false;
	}

	@Override
	public void process() {
		GameState.getInstance().resetTimer();
		//GameState.getInstance().isTurnFinish();
		
        while(!explore.isEmpty()){
        	/*
            currState = explore.get(0);
            explore.remove(0);
            *
            * use only states with the highest score
            */
        	currState = getBestState(explore);
            visited.add(currState);
            if(((TurnState)currState.getParent()).isStop())
                    continue;
            else if(((TurnState)currState).isLeaf())
                ((TurnState)currState).computeScore();
            else{
                next = currState.getNextStates();
                for(State s: next){
                    //check if s is not in visited && s is not in explore && s.isValid()
                    if(!containsState(s, visited) && !containsState(s, explore)){
                        explore.add(0,s);
                        if(!isInitialChildren)
                        	children.add(s);
                    }
                }
                if(isInitialChildren == false)
                	isInitialChildren = true;
                if(GameState.getInstance().isTurnFinish())
                	break;
            }
        }
	}

	@Override
	public void end() {
		GameState.getInstance().setCurrentTurn((TurnState)getBestState(children));
	}
	
	public boolean containsState(State s, ArrayList<State> list){
		for(State l: list)
			if(l.equals(s))
				return true;
		return false;
	}

	@Override
	public State getBestState(ArrayList<State> list) {
		int score = Integer.MIN_VALUE;
		int index = -1;

		for(int i = 0; i < explore.size();i++){
			if(explore.get(i).getScore() >= score){
				score = explore.get(i).getScore();
				index = i;
						}
		}
		if(index != -1)
			return explore.remove(0);
		return null;
	}
    
}
