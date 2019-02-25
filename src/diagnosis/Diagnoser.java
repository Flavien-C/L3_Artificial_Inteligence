
package diagnosis;

import planning.*;
import java.util.*;
import representations.*;
import examples.Generator;

public class Diagnoser {

	private List<Variable> listVar; 
	private List<Constraint> listCons; 
	private HashMap<Variable, String> affectation;
	
	public Diagnoser(List<Variable> listVar, List<Constraint> listCons){
		this.listVar = listVar;
		this.listCons = listCons;
		this.affectation = new HashMap<Variable, String>();
	}
	
	//Ajout un couple variable/valeur dans affectation
	public void ajout(Variable var, String val){
		affectation.put(var, val);
	}
	
	//Retire un couple variable/valeur d'affectation
	public void retrait(Variable var){
		affectation.remove(var);
	}
	
	//Verifie si l'instanciation est incompatible avec le couple (et donc, si l'Instanciation est une Explication du couple)
	public boolean estIncompatible(Variable var, String val, HashMap<Variable, String> affectation){
		HashMap<Variable, String> tmp = new HashMap<Variable, String>(affectation);
		tmp.put(var, val);
		for(Constraint c : listCons) 
		{
			if(!c.isSatisfiedBy(tmp))
				{
					return true;
				}
		}
		return false;
	}
	
	public boolean estIncompatible(Variable var, String val){
		return estIncompatible(var,val,affectation);
	}
	
	public boolean estIncompatible(Variable var, String val, Variable varAff, String valAff){
		HashMap<Variable, String> tmp = new HashMap<Variable, String>();
		tmp.put(varAff,valAff);
		return estIncompatible(var, val, tmp);
	}
	
	//Retourne un diagnostic minimal (au sens de l'inclusion) d'une incompatibilité 
	public HashMap<Variable, String> diagMinimalInclusion(Variable var, String val){
		
		HashMap<Variable, String> listExplications = new HashMap<Variable, String>(affectation); //Choix Courants
		HashMap<Variable, String> listTmpExplications = new HashMap<Variable, String>(); //Copie de Choix Courants où seront testées les modifications
		
		List<Variable> listChoixNonExplores = new ArrayList<Variable>(affectation.keySet()); //Choix non explorés
									
			for(Variable v : listChoixNonExplores)
			{				
				listTmpExplications.clear();
				listTmpExplications.putAll(listExplications);
				listTmpExplications.remove(v);
				
				if(estIncompatible(var, val, listTmpExplications))
				{
					//Ce if sert à éviter de supprimer une valeur incompatible :
					//En effet, si la valeur courante est incompatible, mais que, sans elle, l'affectation l'est toujours, alors la valeur courante va simplement etre oubliée.
					//Par exemple: Si on veut peindre notre toit en Rouge, et que nous disposons actuellement d'un capot Dark_Salmon et d'un Hayon Dark_Salmon, 
					//La methode va tester l'affectation sans le capot Dark_Salmon, et constater qu'elle est toujours invalide.
					if(!estIncompatible(var, val, v, listExplications.get(v)))
					{
						listExplications.remove(v);
					}
				}	
			}
			
		return listExplications;
	}
	
	//Retourne un diagnostic minimal (au sens de la cardinalité) d'une imcompatilité
	//public HashMap<Variable, String> diagMinimalCardinalite(Variable var, String val){;}
	
	//////////////////
	//	   MAIN     //
	//////////////////

	public static void main (String args[]){
		
		Generator generateur = new Generator();
		
	    List<Variable> listVar = generateur.getListVariable();
	    List<Constraint> listCons = generateur.getListConstraint();
	    
	    Diagnoser babyShark = new Diagnoser(listVar, listCons);
	    
		//Ajout d'un Capot Rouge
	    System.out.println("---------------------------------");
	    System.out.println("Le capot est Rouge");
	    babyShark.ajout(generateur.getListVariable().get(1), "rouge");
	    
	    //Ajout d'une porte droite Noire
	    System.out.println("---------------------------------");
	    System.out.println("Peut-on ajouter une porte droite de couleur noire ?");
		if(babyShark.estIncompatible(generateur.getListVariable().get(3), "noir")){
			System.out.println("Non. Que dois-je supprimer ?");
			System.out.println(babyShark.diagMinimalInclusion(generateur.getListVariable().get(3), "noir"));
		}
		else
		{
			System.out.println("C'est okay ! On l'ajoute");
			babyShark.ajout(generateur.getListVariable().get(3), "noir");
		}
		
		//Ajout d'un coffre Rouge
		System.out.println("---------------------------------");
		System.out.println("Peut-on ajouter un coffre de couleur rouge ?");
		if(babyShark.estIncompatible(generateur.getListVariable().get(2), "rouge")){
			System.out.println("Non. Que dois-je supprimer ?");
			System.out.println(babyShark.diagMinimalInclusion(generateur.getListVariable().get(2), "rouge"));
		}
		else
		{
			System.out.println("C'est okay ! On l'ajoute");
			babyShark.ajout(generateur.getListVariable().get(2), "rouge");
		}
		
		//Ajout d'une porte Dark_Salmon		
	    System.out.println("---------------------------------");
	    System.out.println("Peut-on ajouter une porte gauche de couleur dark_salmon ?");
		if(babyShark.estIncompatible(generateur.getListVariable().get(4), "dark_salmon")){
			System.out.println("Non. Que dois-je supprimer ?");
			System.out.println(babyShark.diagMinimalInclusion(generateur.getListVariable().get(4), "dark_salmon"));
		}
		else
		{
			System.out.println("C'est okay ! On l'ajoute");
			babyShark.ajout(generateur.getListVariable().get(4), "dark_salmon");
		}
		
		//Ajout d'une Sono
		System.out.println("---------------------------------");
		System.out.println("Ajout Sono");
		babyShark.ajout(generateur.getListVariable().get(5),"true");
		
		//Ajout d'un toit ouvrant : Permet de tester l'incompatibilité avec la sono
		System.out.println("---------------------------------");
		System.out.println("Peut-on ajouter un toit ouvrant ?");
		if(babyShark.estIncompatible(generateur.getListVariable().get(6), "true")){
			System.out.println("Non. Que dois-je supprimer ?");
			System.out.println(babyShark.diagMinimalInclusion(generateur.getListVariable().get(6), "true"));
		}
		else
		{
			System.out.println("C'est okay ! On l'ajoute");
			babyShark.ajout(generateur.getListVariable().get(6), "true");
		}
		
		//Peinture du toit en blanc : Permet de tester 'incompatibilité avec le Hayon et le Coffre
		System.out.println("---------------------------------");
		System.out.println("Peut-on peindre le toit en blanc ?");
		if(babyShark.estIncompatible(generateur.getListVariable().get(0), "blanc")){
			System.out.println("Non. Que dois-je supprimer ?");
			System.out.println(babyShark.diagMinimalInclusion(generateur.getListVariable().get(0), "blanc"));
		}
		else
		{
			System.out.println("C'est okay ! On l'ajoute");
			babyShark.ajout(generateur.getListVariable().get(0), "blanc");
		}
		
		//Peinture du toit en rouge : Permet de tester l'incompatibilité avec les portes
		System.out.println("---------------------------------");
		System.out.println("Peut-on peindre le toit en rouge ?");
		if(babyShark.estIncompatible(generateur.getListVariable().get(0), "rouge")){
			System.out.println("Non. Que dois-je supprimer ?");
			System.out.println(babyShark.diagMinimalInclusion(generateur.getListVariable().get(0), "rouge"));
		}
		else
		{
			System.out.println("C'est okay ! On l'ajoute");
			babyShark.ajout(generateur.getListVariable().get(0), "rouge");
		}
		
	}
	
}
