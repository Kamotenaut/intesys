/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author msi
 */

import AI.solver.*;
import AI.state.*;
import java.util.ArrayList;

public class TurnSolver extends Solver{
    
    private State initState, currState, finState;
    
    public TurnSolver(){
        //initialize initState
    }
    
    public void start(){
        
        ArrayList<State> explore = new ArrayList<>();
        ArrayList<State> visited = new ArrayList<>();
        ArrayList<State> next = new ArrayList<>();
        explore.add(initState);
        while(!explore.isEmpty()){
            currState = explore.get(0);
            explore.remove(0);
            visited.add(currState);
            if(((TurnState)currState.getParent()).isStop())
                    continue;
            else if(((TurnState)currState).isLeaf())
                ((TurnState)currState).computeScore();
            else{
                next = currState.getNextStates();
                for(State s: next){
                    boolean found = false;
                    //check if s is not in visited && s is not in explore && s.isValid()
                    for(int i=0; i<visited.size(); i++){
                        if(s.equals(visited.get(i)))
                            found = true;
                    }
                    for(int i=0; i<explore.size(); i++){
                        if(s.equals(explore.get(i)))
                            found = true;
                    }
                    if(!found){
                        explore.add(0,s);
                    }
                }
            }
        }
        
    }
    
}
