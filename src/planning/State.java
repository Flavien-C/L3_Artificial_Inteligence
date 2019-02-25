package planning;

import java.util.*;
import java.util.Map.*;
import representations.*;

public class State {
    
    private Map<Variable,String> affectation;
    
    public State(Map<Variable,String> affectation) {
        this.affectation = affectation;
    }
    
    // equals
    public boolean egale(Map<Variable,String> partialState) {
        for (Entry<Variable,String> entry : partialState.entrySet()) {
            if (! this.affectation.containsKey(entry.getKey()) || entry.getValue() != this.affectation.get(entry.getKey())) {
                return false;
            }
        }
        return true;
    }
    
    public Map<Variable,String> getAffectation(){
        return this.affectation;
    }
    
    
    public State deepcopy() {
        Map<Variable,String> affectationCopy = new HashMap<Variable,String>();
        for (Entry<Variable,String> entry : this.affectation.entrySet()) {
            affectationCopy.put(entry.getKey(), entry.getValue());
        }
        State newEtat = new State(affectationCopy);
        return newEtat;
    }
    
    public boolean equals(Object o){
		if(o instanceof State){
			Map<Variable,String> affectationTmp = ((State) o).getAffectation() ;
			for(Variable v : affectation.keySet()){
				if(!affectationTmp.keySet().contains(v) || !affectationTmp.get(v).equals(affectation.get(v))){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public int hashCode(){
		return affectation.hashCode();
	}
	
	public String toString(){
		return affectation.toString();
	}
}
