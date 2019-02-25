package representations;

import java.util.*;

public class AllEqualConstraint implements Constraint {
	
	private Variable[] ensembleVariables;
	
	//On suppose qu'on peut imposer une liste de valeurs
	private String[] valeursAttendues;  
	
	public AllEqualConstraint(Variable[] ensembleVariables, String[] valeursAttendues){
		this.ensembleVariables = ensembleVariables;
		this.valeursAttendues = valeursAttendues;
	}
	
	public AllEqualConstraint(Variable[] ensembleVariables){
		this.ensembleVariables = ensembleVariables;
		this.valeursAttendues = null;
	}
	
	public Variable[] getScope() {
		return ensembleVariables;
	}
	
	public boolean isSatisfiedBy(Map<Variable, String> affectation) {
				
		// Après avoir verifié que l'ensemble des Variables est non null (Aucune condition, donc Vrai),
		// Parcours et comparaison des valeurs : 
		// Si valRef est nulle, elle prend la valeur de la variable v d'affectation 
		// Si valRef est differente de v (mais non NULL), retourne faux
		
		if(ensembleVariables == null)
		{
			return true;
		}
		
		String valRef = null; 
		
		for(Variable v : ensembleVariables)
		{
			if(valRef == null)
			{
				
				valRef = affectation.get(v);
			}
			
			else if(affectation.containsKey(v) && !affectation.get(v).equals(valRef))
			{
				
				return false;
			}
		} 
		
		// Permet de prendre en compte les situations sans liste de variables imposées
		
		if (valRef !=null && valeursAttendues != null)
		{
			return Arrays.asList(valeursAttendues).contains(valRef);
		}
		
		return true;
		
	}
	
	public boolean filter(Map<Variable, String> affectation, Map<Variable, Set<String>> domaines){
		
		HashSet<String> ensembleValeursASupprimer = new HashSet<String>();
		
		Variable tmp = null;
		for(int i = 0;i<ensembleVariables.length;i++){
			tmp = ensembleVariables[i];
			for(String s : domaines.get(tmp)){
				if(valeursAttendues != null && !Arrays.asList(valeursAttendues).contains(s)){
					ensembleValeursASupprimer.add(s);
				}
				
				else if(!ensembleValeursASupprimer.contains(s)){
					for(int j = i+1;j<ensembleVariables.length;j++){
						if(!domaines.get(ensembleVariables[j]).contains(s)){
							ensembleValeursASupprimer.add(s);
							break;
						}
					}
				}
			}
		}
		
		if(ensembleValeursASupprimer.isEmpty()){
			return false;
		}
		
		for(Variable v : ensembleVariables){
			domaines.get(v).removeAll(ensembleValeursASupprimer);
		}
		return true;
	}
	
	public String toString(){
		String ch="All equals : ";
		for(Variable v : ensembleVariables)
			ch+= " "+v+" ";
		ch+= " val attendu : ";
		for(String s : valeursAttendues)
			ch+= " "+valeursAttendues + " ";
		return ch;
	}
	
}
