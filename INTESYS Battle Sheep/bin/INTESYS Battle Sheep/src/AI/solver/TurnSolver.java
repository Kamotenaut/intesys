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
                
                //int counter =10000;
        while(!explore.isEmpty()){
        	/*
            currState = explore.get(0);
            explore.remove(0);
            *
            * use only states with the highest score
            */
        	//System.out.println(1);
        	currState = explore.get(0);
                explore.remove(0);
        	//System.out.println(currState);
        	//System.out.println(2);
            visited.add(currState);
            // first state

        	//System.out.println(3);
        	//System.out.println(GameState.getInstance().isTurnFinish());
            if(((TurnState)currState).isLeaf() || ((TurnState)currState).getDepthcount() == 40 ){
                //System.out.println("counter: "+counter);
            	//System.out.println(4);
				((TurnState)currState).computeScore();
                for(int i=0; i<children.size(); i++){
                    if(children.get(i).getScore()==0)
                        ((TurnState)children.get(i)).computeScore();
                }
                for(int i=0; i<explore.size(); i++){
                    if(explore.get(i).getScore()==0)
                        ((TurnState)explore.get(i)).computeScore();
                }
				//if(GameState.getInstance().isTurnFinish())
	            	break;
                                /*int max = 0;
                                int index = 0;
                                for(int i=0; i<explore.size(); i++){
                                    if(max < explore.get(i).getScore()){
                                        max = explore.get(i).getScore();
                                        index = i;
                                    }
                                }*/
			}
            
            /*
            if(currState.getParent() != null)
            if(((TurnState)currState.getParent()).isStop())
                    continue;
            else if(((TurnState)currState).isLeaf())
                ((TurnState)currState).computeScore();
                */
            
            else{
                next = currState.getNextStates();
                //System.out.println(((TurnState)currState).getPlayer().getName());
                //System.out.println(next.size());
                for(State s: next){
                    //check if s is not in visited && s is not in explore && s.isValid()
                	//System.out.println(!containsState(s, visited));
                	//System.out.println(!containsState(s, explore));
                    /*if(s.getScore()==0)
                    System.out.println(s.getScore());*/
                    if(!containsState(s, visited) && !containsState(s, explore)){
                        explore.add(s);
                        if(!isInitialChildren)
                        	children.add(s);
                    }
                }
                if(isInitialChildren == false)
                	isInitialChildren = true;
                //counter--;
                
            }
        }
	}

	@Override
	public void end() {
		GameState.getInstance().getTimer().setResult(true);
		TurnState temp = GameState.getInstance().getCurrentTurn();
		if(children.size() != 0)
			temp =(TurnState)getBestState(children);
                System.out.println(temp.getScore());
		GameState.getInstance().setCurrentTurn(temp);
		GameState.getInstance().setTurnOver(true);
	}
	
	public boolean containsState(State s, ArrayList<State> list){
		for(State l: list)
			if(l.equals(s))
				return true;
		return false;
	}

	public State getBestState(){
		int index = 0;
		for(int i = 0; i < explore.size(); i++){
			if(explore.get(i).getScore() >= explore.get(index).getScore()){
				index = i;
			}
		}
		if(index == -1)
			System.out.println("ERROR no best state");
		return explore.remove(index);
	}
	
	@Override
	public State getBestState(ArrayList<State> list) {
		int index = 0;
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).getScore() >= list.get(index).getScore()){
				index = i;
			}
		}
		if(index == -1)
			System.out.println("ERROR no best state");
		return list.remove(index);
	}
    
}
