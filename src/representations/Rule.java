package representations;

import java.util.*;

public class Rule implements Constraint {
    
    protected Map<Variable, String> premisse;
    protected Map<Variable, String> conclusion;
    
    public Rule(Map<Variable, String> premisse , Map<Variable, String> conclusion){
		this.premisse = premisse;
		this.conclusion = conclusion;
	}
    
    public Variable[] getScope() {
        HashSet<Variable> tmp = new HashSet(premisse.keySet());
        tmp.addAll(conclusion.keySet());
        return tmp.toArray(new Variable[1]);
    }
    
    public boolean isSatisfiedBy(Map<Variable, String> affectation) {
        boolean p = this.premisseSatisfiedBy(affectation);
        boolean c = this.conclusionSatisfiedBy(affectation);
        return (!p)||c;
    }
    
    private boolean premisseSatisfiedBy(Map<Variable, String> affectation) {
        boolean resultat = true;
        boolean toutesVariablesAffecte=true;
        if (this.premisse == null || this.premisse.isEmpty()) {
            return true;
        }
        for (Variable var:this.premisse.keySet()) {
			
			if(!affectation.containsKey(var)){
				toutesVariablesAffecte=false;
			}
            if (affectation.containsKey(var) && this.premisse.get(var) != affectation.get(var)) {
                resultat = false;
            }
        }
        
        return toutesVariablesAffecte && resultat;
    }
    
    private boolean conclusionSatisfiedBy(Map<Variable, String> affectation) {
        // !!(z=c || t = d || ...)=> !(z!=c && t!=d && ...)
        boolean resultat = true;
        if (this.conclusion == null || this.conclusion.isEmpty()) {
            return false;
        }
        //Si aucune des variables de la conclusion n'est affectée alors => True
        for (Variable var:this.conclusion.keySet()) {
			if(!affectation.containsKey(var)){
				return true;
			}
            if (this.conclusion.get(var) == affectation.get(var)) {
                resultat = false;
            }
        }
        return !resultat;
    }
    
    //En fonction du type de contrainte et de la situation : elagage du domaine
    public boolean filter(Map<Variable, String> affectation, Map<Variable, Set<String>> domaines){
		if(premisseSatisfiedBy(affectation)){
			for(Variable v : conclusion.keySet()){//(a===y || b== z ||...)  !(a!=y && b!=z &&...)
				if(domaines.get(v).contains(conclusion.get(v))){
					return false;
				}
			}
			
			//Si la conclusion ne peux pas etre validée, alors on vide les domaines des variables concernées.
			for(Variable v : conclusion.keySet()){
				domaines.get(v).clear();
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		ArrayList<Variable> listVariable= new ArrayList<Variable>();
		Set listColor = new HashSet();
        listColor.add("noir");
        listColor.add("blanc");
        listColor.add("rouge");
        
        Set listColor2 = new HashSet();
        listColor2.add("noir");
        listColor2.add("rouge");
        
        Set listColor3 = new HashSet();
        listColor3.add("noir");
        
        Set listBool = new HashSet();
        listBool.add("true");
        listBool.add("false");
        
        Variable toit = new Variable("toitColor", listColor);
        Variable capot = new Variable("capotColor", listColor);
        Variable coffre = new Variable("coffreColor", listColor2);
        Variable gauche = new Variable("gaucheColor", listColor3);
        Variable droite = new Variable("droiteColor", listColor3);
        Variable sono = new Variable("sono", listBool);
        Variable ouvrant = new Variable("ouvrant", listBool);
        
        listVariable.add(toit);
        listVariable.add(capot);
        listVariable.add(coffre);
        listVariable.add(droite);
        listVariable.add(gauche);
        listVariable.add(sono);
        listVariable.add(ouvrant);
        
        
        Variable[] tabVarTmp = {toit,capot,coffre};
        String[] tabValTmp = {"blanc","noir"};
        Constraint c1 = new AllEqualConstraint(tabVarTmp, tabValTmp);
        
        HashMap<Variable,String> affectationTest = new HashMap<Variable,String>();
        HashMap<Variable, Set<String>> domainesTest = new HashMap<Variable, Set<String>>();
        for(Variable v : listVariable){
			domainesTest.put(v,new HashSet<String>(v.getDomaine()));
		}
		System.out.println("val domaines avant:\n"+domainesTest+"\n\n");
		c1.filter(affectationTest,domainesTest);
		System.out.println("val domaines apres:\n"+domainesTest+"\n\n");
        
        for(Variable v : listVariable){
			domainesTest.put(v,new HashSet<String>(v.getDomaine()));
		}
        
        Map<Variable,String> mc2cp = new HashMap();
        Map<Variable,String> mc2cc = new HashMap();
        mc2cp.put(toit, "rouge");
        mc2cc.put(gauche, "rouge");
        mc2cc.put(droite, "rouge");
        Constraint c2c = new Rule(mc2cp, mc2cc);
		
		affectationTest.put(toit,"rouge");
		System.out.println("val domaines avant:\n"+domainesTest+"\n\n");
		System.out.println("resultat :"+c2c.filter(affectationTest,domainesTest));
		System.out.println("val domaines apres:\n"+domainesTest+"\n\n");	
	}
	
	public String toString(){
		return "rule : "+premisse+" -> "+conclusion;
	}
}
