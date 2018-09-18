package representations;

import java.util.*;

public class Rule implements Constraint {
    
    private Map<Variable, String> premisse;
    private Map<Variable, String> conclusion;
    
    
    public Variable[] getScope() {
        // Extraction des Variables des Hashmap ? Probleme de modification des Hashmap
        // return null est juste pour pouvoir compiler
        return null;
    }
    
    public Rule(Map<Variable, String> premisse, Map<Variable, String> conclusion) {
        this.premisse = premisse;
        this.conclusion = conclusion;
    }
    
    public boolean isSatisfiedBy(Map<Variable, String> affectation) {
        boolean p = this.premisseSatisfiedBy(affectation);
        boolean c = this.conclusionSatisfiedBy(affectation);
        return !(p||c);
    }
    
    private boolean premisseSatisfiedBy(Map<Variable, String> affectation) {
        // Teste si la premisse est satisfaite par l'affectation
        if (this.premisse.isEmpty()) {
            return true;
        }
        for (Variable var:this.premisse.keySet()) {
            if (this.premisse.get(var) != affectation.get(var)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean conclusionSatisfiedBy(Map<Variable, String> affectation) {
        // Teste si la conclusion est satisfaite par l'affectation
        if (this.conclusion.isEmpty()) {
            return false;
        }
        for (Variable var:this.conclusion.keySet()) {
            if (this.conclusion.get(var) == affectation.get(var)) {
                return true;
            }
        }
        return false;
    }       
}
