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

import AI.state.*;
import java.util.ArrayList;

public class BoardState  extends State{
    
    public ArrayList<State> getNextStates(){
        
        ArrayList<State> nextStates = new ArrayList<>();
        
        return nextStates;
        
    }
    
    public boolean equals(State state){
        
        boolean equal = true;
        
        return equal;
        
    }
    
    public boolean isValid(){
        
        boolean valid = true;
        
        return valid;
        
    }
    
    public boolean isFinal(){
        
        boolean check = true;
        
        return check;
        
    }
    
    public void printState(){
        
        
    }
    
    public boolean isLeaf(){
        
        boolean leaf = true;
        
        return leaf;
        
    }
    
    public void computeScore(){
        
        
    }
    
    public void propagateScore(){
        
        
    }
    
    public void submit(State s){
        
        
        
    }
    
}
