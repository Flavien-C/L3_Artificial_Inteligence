package representations;

import java.util.*;

public interface Constraint {

	public Variable[] getScope();
	
	public boolean isSatisfiedBy(Map<Variable, String> affectation);
	
	//En fonction du type de contrainte et de la situation : elagage du domaine
	public boolean filter(Map<Variable, String> affectation , Map<Variable, Set<String>> domaines);
	
}
