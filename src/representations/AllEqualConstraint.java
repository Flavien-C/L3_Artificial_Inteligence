package representations;

public class AllEqualConstraint implements Constraint {
	
	private Variable[] ensembleVariables;
	
	private String[] valeursAttendues; // On suppose qu'on peut imposer une liste de valeurs 
	
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
			
			else if(!affectation.get(v).equals(valRef))
			{
				return false;
			}
		} 
		
		// Permet de prendre en compte les situations sans liste de variables imposées
		
		if (valeursAttendues != null)
		{
			return Arrays.asList(valeursAttendues).contains(valRef);
		}
		
		return true;
		
	}
	
}
