package representations;

import java.util.*;

public class Disjunction extends Rule {
		
	public Disjunction(Map<Variable, String> ou){
		super(null,ou);		
	} 
	
	public Variable[] getScope() {
        return conclusion.keySet().toArray(new Variable[1]);
    }
	
	public String toString(){
		return "Disjonction : "+conclusion;
	}
	
}
