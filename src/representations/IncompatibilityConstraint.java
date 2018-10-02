package representations;

import java.util.*;

public class IncompatibilityConstraint extends Rule {
    
    public IncompatibilityConstraint(Map<Variable,String> premise) {
        super(premise, new HashMap<Variable,String>());
    }
}