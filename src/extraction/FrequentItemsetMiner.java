package extraction;

import java.util.*;
import representations.*;

public class FrequentItemsetMiner {

    private BooleanDatabase tableBool;

    public FrequentItemsetMiner(BooleanDatabase tableBool) {
        this.tableBool = tableBool;
    }

		
	// !!! cas ou il n'y a pas de transaction 
    public Map<Set<Variable>, Integer> frequentItemsets(int minfr, Set<Variable> motif, List<Map<Variable,String>> transaction, List<Variable> variablesRestantes) {
        Map<Set<Variable>, Integer> res = new HashMap<Set<Variable>, Integer>();
        List<Map<Variable,String>> transTmp = new ArrayList<Map<Variable,String>>();
        List<Map<Variable,String>> transNoTmp = new ArrayList<Map<Variable,String>>(transaction);
        if (variablesRestantes.isEmpty()) {
            return res;
        }
        Variable tmp = variablesRestantes.remove(0);// on a le droit c'est une copie (cf l'appel recursif)
        for (Map<Variable,String> trans : transaction) {
            if (trans.containsKey(tmp) && trans.get(tmp).equals("1")) {
                transTmp.add(trans);
            }
        }
        if(!transTmp.isEmpty()){
			Set<Variable> motifTmp = new HashSet<Variable>(motif);
			motifTmp.add(tmp);
			if(transTmp.size()>=minfr){
				res.put(motifTmp,transTmp.size());
			}
			res.putAll(frequentItemsets(minfr, motifTmp, transTmp, new ArrayList<Variable>(variablesRestantes)));
		}
		
        transNoTmp.removeAll(transTmp);
        if(!transNoTmp.isEmpty()){
			res.putAll(frequentItemsets(minfr, new HashSet<Variable>(motif), transNoTmp, new ArrayList<Variable>(variablesRestantes)));
        }
        
			//System.out.println("IICI 2"+res);
        return res;
    }
}
