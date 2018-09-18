package representations;

import java.util.*;

public interface Constraint {

	public Variable[] getScope();
	
	public boolean isSatisfiedBy(Map<Variable, String> affectation);
	
}
