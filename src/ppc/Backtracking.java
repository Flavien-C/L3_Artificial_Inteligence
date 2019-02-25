package ppc;

import java.util.*;
import java.util.Map.Entry;

import representations.*;
import examples.*;

public class Backtracking{
	
	private HashMap<Variable,String> affectation;
	private List<Variable> listeVariables;
	private List<Constraint> listeContraintes;
	private LinkedList<Variable> variablesNonAssignees;
	private Map<Variable, Set<String>> domaines;
	
	public Backtracking(List<Variable> listeVariables, List<Constraint> listeContraintes){
		this.affectation = new HashMap<Variable,String>();
		this.listeVariables = listeVariables;
		this.listeContraintes = listeContraintes;
		this.variablesNonAssignees = new LinkedList<Variable>(listeVariables);
		this.domaines = new HashMap<Variable, Set<String>>();
		for(Variable v : listeVariables){
			domaines.put(v,new HashSet<String>(v.getDomaine()));
		}
	}
	
	public static HashMap<Variable, Set<String>> deepCopy(Map<Variable, Set<String>> original)
	{
		HashMap<Variable, Set<String>> copie = new HashMap<Variable, Set<String>>();
		for (Entry<Variable, Set<String>> entry : original.entrySet())
		{
			copie.put(entry.getKey(),new HashSet<String>(entry.getValue()));
		}
		return copie;
	}
	
	
	
	
	
	
	
	
	/*
	/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\
	/!\   																					/!\
	/!\      On envoie une afectation avec des variables en clé qui on des valeurs nulles	/!\
	/!\																						/!\
	/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\/!\
	*/
	
	
	public HashMap<Variable, String> solution(int methodeChoixVariable, int methodeChoixValeur){
		HashMap<Variable, Set<String>> copieDomaines = new HashMap<Variable,Set<String>>(domaines);
		
		//Variable tmp = variablesNonAssignees.get(0); //Suppression suite à l'utilisation d'Heuristique
		
		Variable tmp;
		
		
		//a voir si on implemente un pattern strategy ? , les switch dans des methodes?
		switch (methodeChoixVariable)
		{
			case 1 :
				tmp = choixVariableNbContrainte(true);
				break;
			case 2 :  
				tmp = choixVariableNbContrainte(false);
				break;
			case 3 :
				tmp = choixVariableTailleDomaine(true);
				break;
			default : 
				tmp = choixVariableTailleDomaine(false);
		}
		
		
		
		
		int i = 0;
		HashMap<Variable, String> resultat = null;
		
		//variablesNonAssignees.removeFirst();
		variablesNonAssignees.remove(tmp);
		
		if(affectation.containsKey(tmp))
		{
			System.out.println("PASS "+domaines);
			//i = tmp.getDomaine().indexOf(affectation.get(tmp)); //Suppression suite à la création de la Hashap domaines
			/*i = Arrays.asList(domaines.get(tmp).toArray()).indexOf(affectation.get(tmp));
			
			if(variablesNonAssignees.size() == 1)
			{
				i++;
			}
			Suppression suite à l'ajout des heuristiques*/
			domaines.get(tmp).remove(affectation.get(tmp));
			if(variablesNonAssignees.size() == 1){
				affectation.remove(tmp);
			}
			
		}
		// for(;i<domaines.get(tmp).size();i++) Suppression suite à l'ajout des heuristiques
		while(domaines.get(tmp).size()!=0)
		{
			if(!affectation.containsKey(tmp)){
				String val ="";
				switch (methodeChoixValeur)
				{
					case 1 :
						val = choixValeur(tmp, true);
						break;
					case 2 :  
						val = choixValeur(tmp, false);
						break;
					default : 
						val = choixValeurHasard(tmp);
				}
				domaines.get(tmp).remove(val);
				affectation.put(tmp,val);
			}
			
			//affectation.put(tmp, (String)domaines.get(tmp).toArray()[i]); Suppression suite à l'ajout des heuristiques
			
			boolean allConstraintOk = true; 
			
			//REDONDANCE > Dans une methode ? (voir generate and test)
			
			for(Constraint c : listeContraintes)
			{
				if(!c.isSatisfiedBy(affectation))
				{
					
					//System.out.println("TEST AFFECTATION : "+affectation);
					//System.out.println("PASS "+c);
					allConstraintOk = false;
				}
				
			}
			
			if(allConstraintOk)
			{
				if(variablesNonAssignees.isEmpty())
				{
					return affectation;
				}
				
				/*boolean continuer;
				do{
					continuer = false;
					for(Constraint c : listeContraintes){
						if(c.filter(affectation,domaines) && !continuer){
							continuer = true;
							System.out.println("TRUC "+c +" " +domaines);// VERIFIER SI AUCUN FILTRAGE POSSIBLE AVEC LES CONTRAINTES ACTUELLES
						}
					}
					
				}while(continuer);*/
				
				for(Variable v : variablesNonAssignees){
					Set<String> domaineTmp = domaines.get(v);
					if(domaineTmp.size() == 0){
						return null;
					}
					if(domaineTmp.size() == 1){
						affectation.put(v,(String)domaineTmp.toArray()[0]);
						variablesNonAssignees.remove(v);
						resultat = solution(methodeChoixVariable, methodeChoixValeur);
						if(resultat==null){
							affectation.remove(v);
							variablesNonAssignees.addFirst(v);
							affectation.remove(tmp);
							variablesNonAssignees.addFirst(tmp);
							return null;
						}
						variablesNonAssignees = new LinkedList<Variable>(listeVariables);
						return resultat;
					}
				}
				
				resultat = solution(methodeChoixVariable, methodeChoixValeur);
				
				
				if(resultat != null)
				{
					variablesNonAssignees = new LinkedList<Variable>(listeVariables);
					return resultat;
				}
				this.domaines = copieDomaines;//(resultat == null)
			}
			affectation.remove(tmp);
			
		}
		variablesNonAssignees.addFirst(tmp);
		
		return null;
				
	}
	
	
	public Variable choixVariableNbContrainte(boolean laPlus) {
		Variable v = null;
		int nb;
		if(laPlus)
			nb=-1;
		else
			nb=Integer.MAX_VALUE;
		int nbTmp=0;
		for(Variable tmp : variablesNonAssignees) {
			nbTmp=0;
			
			for(Constraint c : listeContraintes) {
				//Une Variable est contraignante si elle est présente dans une contrainte (pas de dinsctinctions prémisse/conclusion), d'où : 
				if(Arrays.asList(c.getScope()).contains(tmp)) {
					nbTmp++;
				}
			}
			if(nb == -1 || (laPlus && nbTmp >= nb) || (!laPlus && nbTmp <= nb)) {
				v = tmp;
				nb = nbTmp;
				
			}
		}
		
		return v;
	}
	
	public Variable choixVariableTailleDomaine(boolean lePlusGrd) {
		Variable res = null;
		int taille = -1;
		
		for(Variable v : variablesNonAssignees){
			if(taille == -1 || (lePlusGrd && v.getDomaine().size() > taille) || (!lePlusGrd && v.getDomaine().size() < taille)) {
				res = v;
				taille = v.getDomaine().size();
			}
		}
		return res;
	}
	
	
	public String choixValeur(Variable v ,boolean laPlus){
		if(laPlus) {
			return Collections.max(domaines.get(v));
		}
		return Collections.min(domaines.get(v));
	}
	
	public String choixValeurHasard(Variable v){
		Random r = new Random();
		String[] tabVal = (String[]) domaines.get(v).toArray();
		
		return tabVal[r.nextInt(tabVal.length)];
		 
	}
	
	
	public static void main(String[] args) {
	    Generator generateur = new Generator();
	    List<Variable> listeVar = generateur.getListVariable();
	    List<Constraint> listConstr = generateur.getListConstraint();
	    
	    
	    Backtracking bt = new Backtracking(listeVar, listConstr);
	    
	    HashMap<Variable,String> tmp = null;
	    do
	    {
			tmp = bt.solution(4,1);
			System.out.println(tmp);
		}while(tmp!=null);
	}
	
}
