package representations;

import java.util.*;

public class IncompatibilityConstraint extends Rule {
		
	public IncompatibilityConstraint(Map<Variable, String> non_et){
		super(non_et,null);		
	} 
	
	public Variable[] getScope() {
        return premisse.keySet().toArray(new Variable[1]);
    }
	
	public String toString(){
		return "Incompatible : "+premisse;
	}
}
