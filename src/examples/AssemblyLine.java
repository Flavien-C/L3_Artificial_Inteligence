package examples;

import planning.*;
import java.util.*;
import representations.*;

public class AssemblyLine {
	
	private static final String[] listeNomsVariablesBool = {"HAS_CHASSIS","HAS_FRONT_LEFT_WHEEL","HAS_FRONT_RIGHT_WHEEL","HAS_REAR_LEFT_WHEEL","HAS_REAR_RIGHT_WHEEL","HAS_BODY"};
	private static final String[] listeNomsVariablesCoul = {"FRONT_COLOR","ROOF_COLOR","LEFT_COLOR","REAR_COLOR","RIGHT_COLOR"};//,"FRONT_RIGHT_WHEEL_COLOR","FRONT_LEFT_WHEEL_COLOR","REAR_LEFT_WHEEL_COLOR","REAR_RIGHT_WHEEL_COLOR"};
	private static final String[] domaineVariablesBool = {"T","F"};
	private static final String[] domaineVariablesCoul = {"GRAY","DARK_SALMON"};//,"RED","BLACK","BLUE","ORANGE","YELLOW","WHITE"};
	private ArrayList<Action> listeActionsPossibles;
	private Map<String,Variable> assocNomVariable;
	
	
	public AssemblyLine(ArrayList<Action> listeActionsPossibles){
		this.listeActionsPossibles = listeActionsPossibles;
		
		this.assocNomVariable = new HashMap<String,Variable>();
		for(String s : listeNomsVariablesBool) {
            assocNomVariable.put(s,new Variable(s,domaineVariablesBool));
		}

		for(String s : listeNomsVariablesCoul) {
		    assocNomVariable.put(s,new Variable(s,domaineVariablesCoul));
		}
		
	
		// PUT_BODY
		HashMap<Variable, String> premisse = new HashMap<Variable, String>();
		HashMap<Variable, String> conclusion = new HashMap<Variable, String>();
		premisse.put(assocNomVariable.get("HAS_BODY"),"F");
		conclusion.put(assocNomVariable.get("HAS_BODY"),"T");
		listeActionsPossibles.add(new Action(premisse, conclusion, "PUT_BODY"));
		
		
		// PUT_CHASSIS
	    premisse = new HashMap<Variable, String>();
		conclusion = new HashMap<Variable, String>();
		premisse.put(assocNomVariable.get("HAS_CHASSIS"),"F");
		conclusion.put(assocNomVariable.get("HAS_CHASSIS"),"T");
		listeActionsPossibles.add(new Action(premisse, conclusion, "PUT_CHASSIS"));

		/*
		// PUT_RIGHT_WHEELS
		premisse = new HashMap<Variable, String>();
		conclusion = new HashMap<Variable, String>();
		premisse.put(assocNomVariable.get("HAS_CHASSIS"),"T");
		premisse.put(assocNomVariable.get("HAS_FRONT_RIGHT_WHEEL"),"F");
		premisse.put(assocNomVariable.get("HAS_REAR_RIGHT_WHEEL"),"F");
		conclusion.put(assocNomVariable.get("HAS_REAR_RIGHT_WHEEL"),"T");
		conclusion.put(assocNomVariable.get("HAS_FRONT_RIGHT_WHEEL"),"T");
		listeActionsPossibles.add(new Action(premisse, conclusion, "PUT_RIGHT_WHEELS"));

		// PUT_LEFT_WHEELS
		premisse = new HashMap<Variable, String>();
		conclusion = new HashMap<Variable, String>();
		premisse.put(assocNomVariable.get("HAS_CHASSIS"),"T");
		premisse.put(assocNomVariable.get("HAS_FRONT_LEFT_WHEEL"),"F");
		premisse.put(assocNomVariable.get("HAS_REAR_LEFT_WHEEL"),"F");
		conclusion.put(assocNomVariable.get("HAS_REAR_LEFT_WHEEL"),"T");
		conclusion.put(assocNomVariable.get("HAS_FRONT_LEFT_WHEEL"),"T");
		listeActionsPossibles.add(new Action(premisse, conclusion, "PUT_LEFT_WHEELS"));
		*/
				
		// PUT_REAR_WHEELS
		premisse = new HashMap<Variable, String>();
		conclusion = new HashMap<Variable, String>();
		premisse.put(assocNomVariable.get("HAS_CHASSIS"),"T");
		premisse.put(assocNomVariable.get("HAS_REAR_LEFT_WHEEL"),"F");
		premisse.put(assocNomVariable.get("HAS_REAR_RIGHT_WHEEL"),"F");
		conclusion.put(assocNomVariable.get("HAS_REAR_LEFT_WHEEL"),"T");
		conclusion.put(assocNomVariable.get("HAS_REAR_RIGHT_WHEEL"),"T");
		listeActionsPossibles.add(new Action(premisse, conclusion, "PUT_REAR_WHEELS"));
		
		// PUT_FRONT_WHEELS
		premisse = new HashMap<Variable, String>();
		conclusion = new HashMap<Variable, String>();
		premisse.put(assocNomVariable.get("HAS_CHASSIS"),"T");
		premisse.put(assocNomVariable.get("HAS_FRONT_LEFT_WHEEL"),"F");
		premisse.put(assocNomVariable.get("HAS_FRONT_RIGHT_WHEEL"),"F");
		conclusion.put(assocNomVariable.get("HAS_FRONT_LEFT_WHEEL"),"T");
		conclusion.put(assocNomVariable.get("HAS_FRONT_RIGHT_WHEEL"),"T");
		listeActionsPossibles.add(new Action(premisse, conclusion, "PUT_FRONT_WHEELS"));
		
		//Peinture
		for(String s1 : listeNomsVariablesCoul)
		{
			for(String s2 : domaineVariablesCoul)
			{
				premisse = new HashMap<Variable, String>();
				conclusion = new HashMap<Variable, String>();
				if(s1.contains("WHEEL"))
				{
					premisse.put(assocNomVariable.get("HAS_"+s1.substring(0,s1.length()-6)),"T");
				}
				else 
				{
					premisse.put(assocNomVariable.get("HAS_BODY"),"T");
				}
				conclusion.put(assocNomVariable.get(s1), s2);
				listeActionsPossibles.add(new Action(premisse,conclusion,"PAINT_"+s1.substring(0,s1.length()-5)+s2));
			}
		}

	}	
	
	//Retourne la liste des Actions Possibles
	public ArrayList<Action> getListeActionPossible()
	{
		return this.listeActionsPossibles;
	}

	//Genere un état But Cohérent
	public State genererEtatButCoherent(){
		HashMap<Variable, String> tmp = new HashMap<Variable, String>();
		
		Random r = new Random();

		for(String s : listeNomsVariablesBool)
		{
			tmp.put(assocNomVariable.get(s),"T");
		}

		for(String s : listeNomsVariablesCoul)
		{
			tmp.put(assocNomVariable.get(s), domaineVariablesCoul[r.nextInt(domaineVariablesCoul.length)]);
		}

		return new State(tmp);

	}
	
	//Genere l'état Initial : Aucune pièce n'est en place, et les pièces sont grises
	public State genereEtatInitial()
	{
		HashMap<Variable, String> tmp = new HashMap<Variable, String>();
		
		for(String s : listeNomsVariablesBool)
		{
			tmp.put(assocNomVariable.get(s),"F");
		}

		for(String s : listeNomsVariablesCoul)
		{
			tmp.put(assocNomVariable.get(s),"GRAY");
		}

		return new State(tmp);
	}
    
}
