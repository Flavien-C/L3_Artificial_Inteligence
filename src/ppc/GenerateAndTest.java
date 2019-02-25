package ppc;
import java.util.*;
import representations.*;
import examples.*;

public class GenerateAndTest{
	
	private HashMap<Variable,String> affectation;
	private List<Variable> listeVariables;
	private List<Constraint> listeContraintes;
	
	public GenerateAndTest(List<Variable> listeVariables, List<Constraint> listeContraintes){
		this.affectation = new HashMap();
		this.listeVariables = listeVariables;
		this.listeContraintes = listeContraintes;
	}
	
	private void genNTest(int index){
		if(index == listeVariables.size())
		{
			for(Constraint c : listeContraintes)
			{
				if(!c.isSatisfiedBy(affectation))
				{
					return;
				}
			}
			System.out.println("Solution : \n" + affectation);
		}
		else 
		{
			for(String valeurPossible : listeVariables.get(index).getDomaine())
			{
				affectation.put(listeVariables.get(index), valeurPossible);
				genNTest(index+1);
			}
		}
	}
	
	public void genNTest(){
		genNTest(0);
	}	
	
	public static void main(String[] args){
		Generator generateur = new Generator();
		List<Variable> listeVar = generateur.getListVariable();
		List<Constraint> listeContr = generateur.getListConstraint();
		GenerateAndTest gen = new GenerateAndTest(listeVar, listeContr);
		gen.genNTest();
	}

}
