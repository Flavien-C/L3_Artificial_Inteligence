//////////////

// RIEN NE MARCHE PU**** //

//////////////

///////////////////////////////////
/* 

Test Sur 5 listes de 5 états Finaux (Similaires pour les 3 Algorithmes, évidemment) : 

DFS : 
Moyenne : 
* 
DFS Itératif : 
Moyenne : 

BFS : 
Moyenne : 

*/
///////////////////////////////////

package planning;

import java.util.*;
import java.util.Map.Entry;

import representations.*;
import examples.AssemblyLine;

public class PlanningProblem {
    
    protected static int sonde;
    protected State etatInit;
    protected List<State> etatFinal;
    protected List<Action> listActions;
    
    public PlanningProblem(State etatInit, List<State> etatFinal, List<Action> listActions) {
        this.etatInit = etatInit;
        this.etatFinal = etatFinal;
        this.listActions = listActions;
    }
    
    public State getEtatInit() {
        return this.etatInit;
    }
    
    public List<State> getEtatFinal() {
        return this.etatFinal;
    }
    
    public List<Action> getListActions() {
        return this.listActions;
    }
    
    public boolean satisfies(State etat) {
        for (State e : etatFinal) {
			
            if (etat.equals(e)) {
				
                return true;
            }
        }
        return false;
    }
    
    // RECHERCHE EN PROFONDEUR
    
    public Stack<Action> dfs(State etat, Stack<Action> plan, List<State> closed) {
        if (this.satisfies(etat)) {
            return plan;
        }
        if (plan.size() > 100) {
			return null;
		}
        else {
            for (Action act : this.listActions) {
                if (act.isApplicable(etat)) {
                    State next = act.apply(etat);
                    sonde ++;
                    if (! closed.contains(next)) {
                        plan.push(act);
                        closed.add(next);
                        Stack<Action> subplan = this.dfs(next,plan,closed);
                        if (subplan != null) {
                            return subplan;
                        }
                        else {
                            plan.pop();
                        }
                    }
                }
                
            }
            return null;
        }
    }
    
    // RECHERCHE EN PROFONDEUR ITERATIVE
    
    public Stack<Action> dfsIterative(State etat, Stack<Action> plan, List<State> closed, int depth) {
        if (this.satisfies(etat) || depth == 0) {
            return plan;
        }
        else {
            for (Action act : this.listActions) {
                if (act.isApplicable(etat)) {
                    State next = act.apply(etat);
                    sonde ++;
                    if (! closed.contains(next)) {
                        plan.push(act);
                        closed.add(next);
                        Stack<Action> subplan = this.dfsIterative(next,plan,closed, depth-1);
                        if (subplan != null) {
                            return subplan;
                        }
                        else {
                            plan.pop();
                        }
                    }
                }
            }
            
            return null;
        }
    }
    
    // RECHERCHE EN LARGEUR
    
    public Stack<Action> bfs() {
		State state = null;
		State next = null;
        Map<State,State> father = new HashMap<State, State>();
        Map<State, Action> plan = new HashMap<State, Action>();
        List<State> closed = new ArrayList<State>();
        ArrayDeque<State> open = new ArrayDeque<State>();
        open.add(etatInit);
        
        father.put(etatInit,null);
        while(open.peek() != null){
			
			state = open.pop();
			closed.add(state);
			for(Action a : listActions){
				if(a.isApplicable(state)){
					next= a.apply(state);
					sonde ++;
					if(!closed.contains(next) && !open.contains(next)){
						father.put(next,state);
						plan.put(next,a);
						if(satisfies(next)){
							return getBfsPlan(father, plan,next);
						}
						else{
							open.add(next);
						}
						
					}
				}
			}
			
		}
		return null;
    }
    
    
    public Stack<Action> getBfsPlan (Map<State,State> father, Map<State, Action> action, State goal){
		State tmp = goal;
		Stack<Action> plan = new Stack<Action>();
		while(goal != null){
			plan.push(action.get(goal));
			goal = father.get(goal);
		}
		Collections.reverse(plan);
		return plan;
	}    
    
    //////////////////
	//	   MAIN     //
	//////////////////
    
    public static void main(String[] args) {
		
		AssemblyLine al = new AssemblyLine(new ArrayList<Action>());
		
		State ei = al.genereEtatInitial(); //Etat Initial
		List<State> listEtatsFinaux = new ArrayList<State>(); //Etat Final
		
		int i;
		
		for(i=0;i<5;i++)
		{
			listEtatsFinaux.add(al.genererEtatButCoherent()); 
		}
		
		List<Action> lap1 = al.getListeActionPossible(); //Liste Actions Possibles
		
		PlanningProblem mommyShark = new PlanningProblem(ei, listEtatsFinaux, lap1);
		
		List<State> closed = new ArrayList<State>();
		Stack<Action> plan = new Stack<Action>();
		
		System.out.println("---------------------------- \n\n  DFS\n\n---------------------------- \n\n");
		
		sonde = 0;
		
		for(State etatFinal : listEtatsFinaux)
		{
			System.out.println("Etat Final : " + etatFinal + "\n\n");
			System.out.println("Solution de DFS" + mommyShark.dfs(etatFinal, plan, closed)+ "\n \n");
		}
		
		System.out.println("Nombre de Noeuds Parcourus par le DFS pour la résolution de " + listEtatsFinaux.size() + " problèmes : "+ sonde +"\n\n");

		System.out.println("---------------------------- \n\n  DFS Itératif\n\n---------------------------- \n\n");
		
		sonde = 0;
		
		closed = new ArrayList<State>();
		plan = new Stack<Action>();
		
		for(State etatFinal : listEtatsFinaux)
		{
			System.out.println("Etat Final : " + etatFinal + "\n\n");
			System.out.println("Solution de DFS" + mommyShark.dfsIterative(etatFinal, plan, closed,0)+ "\n \n");
		}
		
		System.out.println("Nombre de Noeuds Parcourus par le DFS (Iteratif) pour la résolution de " + listEtatsFinaux.size() + " problèmes : "+ sonde +"\n\n");

		System.out.println("---------------------------- \n\n  BFS\n\n---------------------------- \n\n");
		
		sonde = 0;
		
		closed = new ArrayList<State>();
		plan = new Stack<Action>();
		
		for(State etatFinal : listEtatsFinaux)
		{
			System.out.println("Etat Final : " + etatFinal + "\n\n");
			System.out.println("Solution de BFS" + mommyShark.bfs()+ "\n \n");
		}
		
		System.out.println("Nombre de Noeuds Parcourus par le BFS pour la résolution de " + listEtatsFinaux.size() + " problèmes : "+ sonde +"\n\n");
		
		
	}
    
}
    
    


