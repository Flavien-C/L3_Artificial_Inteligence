package examples;

import java.util.*;
import representations.*;

public class Generator {
    
    private ArrayList<Variable> listVariable;
    private ArrayList<Constraint> listConstraint;
    
    public Generator() {
        this.listVariable = new ArrayList();
        this.listConstraint = new ArrayList();
        
        Set listColor = new HashSet();
        listColor.add("noir");
        listColor.add("blanc");
        listColor.add("rouge");
        listColor.add("dark_salmon");
        
        Set listBool = new HashSet();
        listBool.add("true");
        listBool.add("false");
        
        Variable toit = new Variable("toitColor", listColor);
        Variable capot = new Variable("capotColor", listColor);
        Variable coffre = new Variable("coffreColor", listColor);
        Variable gauche = new Variable("gaucheColor", listColor);
        Variable droite = new Variable("droiteColor", listColor);
        Variable sono = new Variable("sono", listBool);
        Variable ouvrant = new Variable("ouvrant", listBool);
        
        listVariable.add(toit);
        listVariable.add(capot);
        listVariable.add(coffre);
        listVariable.add(droite);
        listVariable.add(gauche);
        listVariable.add(sono);
        listVariable.add(ouvrant);
        
        
        // Constraint
        
        //CouleurToit = CouleurCapot = CouleurCoffre
        Variable[] tabVarTmp = {toit,capot,coffre};
        String[] tabValTmp = {"rouge","blanc","noir","dark_salmon"};
        Constraint c1 = new AllEqualConstraint(tabVarTmp, tabValTmp);
        
        //CouleurToit = CouleurDroite Ou CouleurGauche
        Map<Variable,String> mc2ap = new HashMap();
        Map<Variable,String> mc2ac = new HashMap();
        mc2ap.put(toit, "noir");
        mc2ac.put(gauche, "noir");
        mc2ac.put(droite, "noir");
        Constraint c2a = new Rule(mc2ap, mc2ac);
        
        Map<Variable,String> mc2bp = new HashMap();
        Map<Variable,String> mc2bc = new HashMap();
        mc2bp.put(toit, "blanc");
        mc2bc.put(gauche, "blanc");
        mc2bc.put(droite, "blanc");
        Constraint c2b = new Rule(mc2bp, mc2bc);
        
        Map<Variable,String> mc2cp = new HashMap();
        Map<Variable,String> mc2cc = new HashMap();
        mc2cp.put(toit, "rouge");
        mc2cc.put(gauche, "rouge");
        mc2cc.put(droite, "rouge");
        Constraint c2c = new Rule(mc2cp, mc2cc);
        
        Map<Variable,String> mc2dp = new HashMap();
        Map<Variable,String> mc2dc = new HashMap();
        mc2dp.put(toit, "dark_salmon");
        mc2dc.put(gauche, "dark_salmon");
        mc2dc.put(droite, "dark_salmon");
        Constraint c2d = new Rule(mc2dp, mc2dc);

		// !(CouleurGauche == Noire && CouleurDroite == Noire)
        Map<Variable,String> mc3 = new HashMap();
        mc3.put(gauche, "noir");
        mc3.put(droite, "noir");
        Constraint c3 = new IncompatibilityConstraint(mc3);
        
        Map<Variable,String> mc4 = new HashMap();
        mc4.put(sono, "true");
        mc4.put(ouvrant, "true");
        Constraint c4 = new IncompatibilityConstraint(mc4);
        
        listConstraint.add(c1);
        listConstraint.add(c2a);
        listConstraint.add(c2b);
        listConstraint.add(c2c);
        listConstraint.add(c3);
        listConstraint.add(c4);
    }
    
    public ArrayList<Variable> getListVariable() {
        return this.listVariable;
    }
    
    public ArrayList<Constraint> getListConstraint() {
        return this.listConstraint;
    }
}
