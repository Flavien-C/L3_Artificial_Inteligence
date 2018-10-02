package representations;

import java.util.*;
import java.util.Map.*;

public class Rule implements Constraint {
    
    private Map<Variable,String> premise;
    private Map<Variable,String> conclusion;
    
    public Rule(Map<Variable,String> premise, Map<Variable,String> conclusion) {
        this.premise = premise;
        this.conclusion = conclusion;
    }
    
    public Map<Variable,String> getPremise() {
        return this.premise;
    }
    
    public Map<Variable,String> getConclusion() {
        return this.conclusion;
    }
    
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<Variable>(this.premise.keySet());
        scope.addAll(this.conclusion.keySet());
        return scope;
    }
    
    @Override
    public boolean isSatisfiedBy(Map<Variable,String> allocation) {
        boolean p = this.premiseSatisfiedBy(allocation);
        boolean c = this.conclusionSatisfiedBy(allocation);
        return !p||c;
    }
    
    private boolean premiseSatisfiedBy(Map<Variable,String> allocation) {
        if (this.premise.isEmpty()) {
            return true;
        }
        for (Entry<Variable,String> entry : this.premise.entrySet()) {
            if (!entry.getValue().equals(allocation.get(entry.getKey()))) {
                return false;
            }
        }
        return true;
    }
    
    private boolean conclusionSatisfiedBy(Map<Variable,String> allocation) {
        //true ssi a satisf conclusion
        if (this.conclusion.isEmpty()) {
            return false;
        }
        for (Entry<Variable,String> entry : this.conclusion.entrySet()) {
            if (!entry.getValue().equals(allocation.get(entry.getKey()))) {
                return true;
            }
        }
        return false;
    }
}