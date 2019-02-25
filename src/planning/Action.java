package planning;

import java.util.*;
import representations.*;

public class Action {
    
    private Map<Variable,String> premisse;
    private Map<Variable,String> conclusion;
    private String nom;
    
    public Action(Map<Variable,String> premisse, Map<Variable,String> conclusion, String nom) {
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.nom = nom;
    }
    
    public boolean isApplicable(State state) {
        return state.egale(this.premisse);
    }
    
    public State apply(State state) {
		//La deepcopy permet de sauvegarder l'état avant l'éxécution d'une action.
        State newState = state.deepcopy();
        if (this.isApplicable(state)) {
            for (Variable v : this.conclusion.keySet()){
                newState.getAffectation().put(v,this.conclusion.get(v));
            }
        }
        return newState;
    }
    
	public Map<Variable,String> getConclusion() {
		return this.conclusion;
	}
    
    public String toString() {
        return this.nom;
    }
}
