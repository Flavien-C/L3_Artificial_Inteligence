package planning;

import java.util.*;
import java.util.Map.*;
import representations.*;

public class InformedHeuristic implements Heuristic {
		
	public int getValue(State etatActuel, State etatBut){
		
		int count = 0;
			
		for (Variable v : etatBut.getAffectation().keySet()) {
			if(!(etatBut.getAffectation().get(v).equals(etatActuel.getAffectation().get(v))))
			{
				count++;
			}			
		}
		
		return count;
		
	}
}
