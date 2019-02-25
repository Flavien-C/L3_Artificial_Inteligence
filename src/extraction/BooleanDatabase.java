package extraction;

import java.util.*;
import representations.*;

/*
v1,   v2,   v3
------------------
v1=0, v2=1, v3=0
v1=1, v2=1, v3=1
*/


public class BooleanDatabase {

    private List<Variable> listVariables;
    private List<Map<Variable,String>> listTransactions;

    public BooleanDatabase(List<Variable> listVariables, List<Map<Variable,String>> listTransactions) {
        this.listVariables = listVariables;
        this.listTransactions = listTransactions;
    }
    
    public List<Variable> getListVariables() {
        return this.listVariables;
    }
    
    public List<Map<Variable,String>> getListTransactions() {
        return this.listTransactions;
    }

}
