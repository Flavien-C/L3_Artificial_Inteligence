package planning;

import java.util.*;
import java.util.Map.*;
import representations.*;

public class SimpleHeuristic implements Heuristic {
		
	public int getValue(State etatActuel, State etatBut){
		
		int count = 0;
			
		for (Variable v : etatBut.getAffectation().keySet()) {
			if(v.toString().contains("COLOR") && !(etatBut.getAffectation().get(v).equals(etatActuel.getAffectation().get(v))))
			{
				count++;
			}			
		}
		
		
		return count;
		
	}
}
