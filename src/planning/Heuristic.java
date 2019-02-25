package planning;

import java.util.*;
import java.util.Map.*;
import representations.*;

public interface Heuristic {
		
	public int getValue(State etatActuel, State etatBut);
	
	
}
