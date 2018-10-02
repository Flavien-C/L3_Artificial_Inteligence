package representations;

import java.util.*;

public class Disjunction extends Rule {
    
    public Disjunction(Map<Variable,String> conclusion) {
        super(new HashMap<Variable,String>(), conclusion);
    }
}