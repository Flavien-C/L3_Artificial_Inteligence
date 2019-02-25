package examples;

import extraction.*;
import representations.*;
import java.util.*;


public class Extract {

    public static void main(String[] args) {
        List<Variable> listVariables = new ArrayList<Variable>();
        List<Map<Variable,String>> listTransactions = new ArrayList<Map<Variable,String>>();

        List<String> domaine = new ArrayList<String>();
        domaine.add("1");
        domaine.add("0");

        Variable v1 = new Variable("A", domaine);
        Variable v2 = new Variable("B", domaine);
        Variable v3 = new Variable("C", domaine);
        Variable v4 = new Variable("D", domaine);
        Variable v5 = new Variable("E", domaine);

        listVariables.add(v1);
        listVariables.add(v2);
        listVariables.add(v3);
        listVariables.add(v4);
        listVariables.add(v5);

        // Transactions
        Map<Variable,String> t1 = new HashMap<Variable,String>();
        t1.put(v1,"1");
        t1.put(v2,"1");
        t1.put(v3,"1");
        t1.put(v4,"1");
        t1.put(v5,"1");

        Map<Variable,String> t2 = new HashMap<Variable,String>();
        t2.put(v1,"1");
        t2.put(v2,"0");
        t2.put(v3,"1");
        t2.put(v4,"0");
        t2.put(v5,"0");

        Map<Variable,String> t3 = new HashMap<Variable,String>();
        t3.put(v1,"1");
        t3.put(v2,"1");
        t3.put(v3,"1");
        t3.put(v4,"1");
        t3.put(v5,"0");
        
        Map<Variable,String> t4 = new HashMap<Variable,String>();
        t4.put(v1,"0");
        t4.put(v2,"1");
        t4.put(v3,"1");
        t4.put(v4,"0");
        t4.put(v5,"0");

        Map<Variable,String> t5 = new HashMap<Variable,String>();
        t5.put(v1,"1");
        t5.put(v2,"1");
        t5.put(v3,"1");
        t5.put(v4,"0");
        t5.put(v5,"0");

        Map<Variable,String> t6 = new HashMap<Variable,String>();
        t6.put(v1,"0");
        t6.put(v2,"0");
        t6.put(v3,"0");
        t6.put(v4,"0");
        t6.put(v5,"1");

        listTransactions.add(t1);
        listTransactions.add(t2);
        listTransactions.add(t3);
        listTransactions.add(t4);
        listTransactions.add(t5);
        listTransactions.add(t6);

        BooleanDatabase table = new BooleanDatabase(listVariables, listTransactions);

        FrequentItemsetMiner fim = new FrequentItemsetMiner(table);
		long debut = System.currentTimeMillis();
        Map<Set<Variable>, Integer> res = fim.frequentItemsets(3, new HashSet<Variable>(), listTransactions, listVariables);
        System.out.println("temps : "+(System.currentTimeMillis()-debut));

        System.out.println(res);
    }
}

