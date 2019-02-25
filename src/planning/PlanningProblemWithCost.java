//////////////

// DIJKSTRA MARCHE PAS PU**** //

//////////////


///////////////////////////////////
/* 

Test Sur 5 listes de 5 états Finaux (Similaires pour les 3 Algorithmes, évidemment) : 

Dijkstra : 
Moyenne : 
* 
A* (Heuristique Simple) : 
Moyenne : 

A* (Heuristique Informée) : 
Moyenne : 

*/
///////////////////////////////////

package planning;

import java.util.*;
import representations.*;
import examples.AssemblyLine;

public class PlanningProblemWithCost extends PlanningProblem {
    
    public PlanningProblemWithCost (State etatInit, List<State> etatFinal, List<Action> listActions){
		super(etatInit,etatFinal,listActions);
	}		
		
	//Permet de Calculer le Cout d'une action en partant du principe suivant : 
	
	// Cout action de peinture pièce unique : 2
	// Cout action d'installation deux roues : 1
	// Cout action d'installation pièce unique : 2
	// Cout action de peinture large effet (pls pieces) : 1 
	
	public int getCost(Action action){
		if(action.getConclusion().size()==1)
		{
			return 2;
		}
		return 1;
	}
		
	// DIJKSTRA
	public Stack<Action> dijkstra(){
	
        Map<State, Integer> distance = new HashMap<State, Integer>();
        Map<State, State> father = new HashMap<State, State>();
        Map<State, Action> plan = new HashMap<State, Action>();
        ArrayList<State> goals = new ArrayList<State>();
		ArrayList<State> open = new ArrayList<State>();
		
		open.add(etatInit);
	 	State etatActuel = etatInit;
		State next = null;
		distance.put(etatActuel, 0);
		father.put(etatActuel, null);
		
		while(!open.isEmpty())
		{
			etatActuel = argmin(distance, open);
			open.remove(open.indexOf(etatActuel));
			
			if (satisfies(etatActuel)) 
			{ 
				goals.add(etatActuel);
			}
			
			for (Action act : listActions)
			{
				if(act.isApplicable(etatActuel)) 
				{
					next = act.apply(etatActuel);
					sonde ++;
					if(!distance.containsKey(next)) 
					{
						distance.put(next, Integer.MAX_VALUE);
					}
					if(distance.get(next) > distance.get(etatActuel) + getCost(act)) 
					{
						distance.put(next, distance.get(etatActuel)+ getCost(act));
						father.put(next, etatActuel);
						plan.put(next, act);
						open.add(next);
					}
				}
			}
		
		}
		return getDijkstraPlan(father, plan, goals, distance);
	
    }
    
    public Stack<Action> getDijkstraPlan(Map<State,State> father, Map<State, Action> plan2, ArrayList<State> goals, Map<State,Integer> distance){
		Stack<Action> plan = new Stack<Action>();
    	State goal = argmin(distance, goals);
    	while(goal != null) {
    		 plan.push(plan2.get(goal));
    		 goal = father.get(goal);
    	}
    	Stack<Action> reverse = new Stack<Action>();
    	while(plan.peek()!=null) {
    		 reverse.push(plan.pop());
    	}
    	return reverse;
    }
    
    // A*
    public Stack<Action> aStar(State etatBut, Heuristic heuristique){

        Map<State, Integer> distance = new HashMap<State, Integer>();
        Map<State, Integer> value = new HashMap<State, Integer>();
		Map<State, Action> plan = new HashMap<State, Action>();
        Map<State, State> father = new HashMap<State, State>();
		ArrayList<State> open = new ArrayList<State>();
		
		State next = null;
		State etatActuel = etatInit;
		
		open.add(etatInit);
		father.put(etatActuel, null);
		distance.put(etatActuel, 0);
		value.put(etatActuel, heuristique.getValue(etatActuel, etatBut));
		
		while(!open.isEmpty())
		{
			
			etatActuel = argmin(value, open);
			
			if (satisfies(etatActuel)) 
			{ 
				return getBfsPlan(father, plan, etatActuel);
			}
		
			open.remove(open.indexOf(etatActuel));
			for (Action act : listActions)
			{
				if(act.isApplicable(etatActuel)) 
				{
					next = act.apply(etatActuel);
					sonde ++;
					if(!distance.containsKey(next)) 
					{
						distance.put(next, Integer.MAX_VALUE);
					}
					if(distance.get(next) > (distance.get(etatActuel) + getCost(act) )) 
					{
						
						distance.put(next, distance.get(etatActuel)+ getCost(act));
						value.put(next, distance.get(next)+ heuristique.getValue(etatActuel, etatBut)); 
						father.put(next, etatActuel);
						plan.put(next, act);
						open.add(next);
					}
				}
			}
			
		}
		
		return null;
	}
    
    ////
 
    public State argmin(Map<State, Integer> distance, ArrayList<State> listState)
    {
    	State min = null;
    	for(State s : listState) {
    		if(min == null || distance.get(min) > distance.get(s)) {
    			min = s;
    		}
    	}
    	return min;
    }
   
	//////////////////
	//	   MAIN     //
	////////////////// 
    
    public static void main (String args[]){
		AssemblyLine al = new AssemblyLine(new ArrayList<Action>());

		State ei = al.genereEtatInitial(); //Etat Initial
		List<State> listEtatsFinaux = new ArrayList<State>(); //Etat Final
		int i;
		
		for(i=0;i<5;i++)
		{
			listEtatsFinaux.add(al.genererEtatButCoherent()); 
		}
		
		List<Action> lap1 = al.getListeActionPossible(); 
		PlanningProblemWithCost daddyShark = new PlanningProblemWithCost(ei, listEtatsFinaux, lap1);
		
		Heuristic simpleH = new SimpleHeuristic();
		Heuristic informedH = new InformedHeuristic();
		
		
		System.out.println("---------------------------- \n\n  A* (Heuristique Simple)\n\n---------------------------- \n\n");
		
		sonde = 0;
		
		for(State etatFinal : listEtatsFinaux)
		{
			System.out.println("Etat Final : " + etatFinal + "\n\n");
			System.out.println("Solution de A Star (Heuristique Simple): " + daddyShark.aStar(etatFinal,simpleH) + "\n \n");
		}
		
		System.out.println("Nombre de Noeuds Parcourus par A Star (Heuristique Simple) pour la résolution de " + listEtatsFinaux.size() + " problèmes : "+ sonde +"\n\n");
		
		System.out.println("---------------------------- \n\n  A* (Heuristique Informée)\n\n---------------------------- \n\n");
		
		sonde = 0;
		
		for(State etatFinal : listEtatsFinaux)
		{
			System.out.println("Etat Final : " + etatFinal + "\n\n");
			System.out.println("Solution de A Star (Heuristique Informée): " + daddyShark.aStar(etatFinal,informedH) + "\n \n");
			
		}
		
		System.out.println("Nombre de Noeuds Parcourus par A Star (Heuristique Informée) pour la résolution de " + listEtatsFinaux.size() + " problèmes : "+ sonde +"\n\n");
		
		System.out.println("---------------------------- \n\n  Dijkstra \n\n---------------------------- \n\n");
			
		sonde = 0;
		
		System.out.println("Solutions de Dijkstra : " + daddyShark.dijkstra() + "\n \n");
		
		System.out.println("Nombre de Noeuds Parcourus par Dijkstra (Enfin, par l'Algorithme de Dijkstra, pas par Dijkstra lui meme ..) pour la résolution de " + listEtatsFinaux.size() + " problèmes : "+sonde+ "\n\n");
	}
	
	
}
