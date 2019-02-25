package extraction;

import java.util.*;
import representations.*;

public class AssociationRuleMiner {
    
    private Map<Set<Variable>, Integer> itemsets;
    
    public AssociationRuleMiner(Map<Set<Variable>, Integer> itemsets) {
        this.itemsets = itemsets;
    }
    
    public Map<Set<Variable>, Integer> getItemsets() {
        return this.itemsets;
    }
    
    public Set<AssociationRule> ruleMiner(int minfr, int minconf) {
        Set<AssociationRule> listRule = new HashSet<AssociationRule>();
        // decomposer les itemsets
        
        /*
        freq = nombre de fois que le scope de regle apparait dans la table
        conf = freq/ freq d'un motif dans freqItemset
        */
        AssociationRule tmp = null;
        int fr;
        int conf;
        for(Set<Variable> z : itemsets.keySet()){
			if(itemsets.get(z) >=minfr){
				listRule.addAll(ruleExtraction(z,new HashSet(), new HashSet(), minconf,itemsets.get(z)));
			}
		}
        
        return listRule;
    }
    
    private Set<AssociationRule> ruleExtraction(Set<Variable> variableRestante ,Set<Variable> premisseCourante,Set<Variable> conclusionCourante, int minConf, int freqSource){
		Set<AssociationRule> listRule = new HashSet<AssociationRule>();
		
		
		if(variableRestante.isEmpty()){
			
			if(!conclusionCourante.isEmpty() && !premisseCourante.isEmpty() && (freqSource/itemsets.get(premisseCourante))>=minConf){
				listRule.add(new AssociationRule(premisseCourante, conclusionCourante));
			}
			return listRule;
		}
		
		
		Set<Variable> copiePremisse = null;
		Set<Variable> copieConclusion = null;
		Set<Variable> copieVariableRestante = new HashSet(variableRestante);
		
		Variable tmp = (Variable) variableRestante.toArray()[0];
		copieVariableRestante.remove(tmp);
		
		copiePremisse = new HashSet(premisseCourante);
		copiePremisse.add(tmp);
		listRule.addAll(ruleExtraction(copieVariableRestante,copiePremisse,new HashSet(conclusionCourante),minConf, freqSource));
		
		copieConclusion = new HashSet(conclusionCourante);
		copieConclusion.add(tmp);
		listRule.addAll(ruleExtraction(copieVariableRestante,new HashSet(premisseCourante),copieConclusion,minConf, freqSource));
		return listRule;
	}
}
