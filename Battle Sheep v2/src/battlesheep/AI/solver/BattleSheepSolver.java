package battlesheep.AI.solver;

import java.util.ArrayList;

import battlesheep.GameState;
import battlesheep.AI.state.BattleSheepState;
import battlesheep.AI.state.State;
import battlesheep.actor.SheepStack;

public class BattleSheepSolver extends Solver{

	State initState, currState;
	private ArrayList<State> children;
	
	public BattleSheepSolver(State initState){
        super();
        this.initState = initState.clone();
    }
	
	@Override
	public void init() {
		children = new ArrayList<>();
	}

	@Override
	public void process() {
		GameState.getInstance().resetTimer();
		int score = alphaBetaMiniMax((BattleSheepState) initState, Integer.MIN_VALUE, Integer.MAX_VALUE);
		System.out.println("Best state score: "+ score);
		
	}

	@Override
	public void end() {
		GameState.getInstance().resetTimer();
		GameState.getInstance().getTimer().setResult(true);

		for(State s: children)
			System.out.println(s.getScore());
		
		System.out.println("Generated Children: " + children.size());
		BattleSheepState temp = null;
		if(children.size() != 0)
			temp = (BattleSheepState) getBestState(children);
		System.out.println("Player: " + temp.getPlayer().getName());
		System.out.println("Children Count: " + temp.getChildren());
		System.out.println("Score: " + temp.getScore());
		System.out.println();
		BattleSheepState result = new BattleSheepState(
				temp.getPlayer(),
				((BattleSheepState)initState).getSheepStackEnemy(), 
				temp.getSheepStackEnemy());
		GameState.getInstance().setCurrentTurn(result);
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
	
	public int alphaBetaMiniMax(BattleSheepState state, int alpha, int beta){
		
		int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
		for(State s: state.getNextStates()){
			int currentScore = 0;
			if(state.isMax()){
				//MAX
				currentScore = alphaBetaMiniMax((BattleSheepState) s, alpha, beta, BattleSheepState.MAX_DEPTH - 1);
				maxValue = Math.max(maxValue, currentScore);
				alpha = Math.max(currentScore, alpha);
				s.setScore(currentScore);
				children.add(s);
				
			} else {
				//MIN
				currentScore = alphaBetaMiniMax((BattleSheepState) s, alpha, beta, BattleSheepState.MAX_DEPTH - 1);
				minValue = Math.min(minValue, currentScore);
				beta = Math.min(currentScore, beta);
				s.setScore(currentScore);
				children.add(s);
			}
			if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) 
				break;
		}
		
		return state.isMax() ? maxValue : minValue;
	}

	public int alphaBetaMiniMax(BattleSheepState state, int alpha, int beta, int depth){
		if(state.isFinal() || depth == 0){
			state.calculateScore();
			return state.getScore();
		}
		int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;
		for(State s: state.getNextStates()){
			int currentScore = 0;
			if(state.isMax()){
				//MAX
				currentScore = alphaBetaMiniMax((BattleSheepState) s, alpha, beta, depth - 1);
				maxValue = Math.max(maxValue, currentScore);
				alpha = Math.max(currentScore, alpha);
				
			} else {
				//MIN
				currentScore = alphaBetaMiniMax((BattleSheepState) s, alpha, beta, depth - 1);
				minValue = Math.min(minValue, currentScore);
				beta = Math.min(currentScore, beta);
			
			}
			if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) 
				break;
		}
		
		return state.isMax() ? maxValue : minValue;
	}
	
}
