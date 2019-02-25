package representations;

import java.util.*;

public class Variable {

	private String nom;
	private ArrayList<String> domaine;
	
	public Variable(String nom, Collection<String> domaine){
		this.nom = nom;
		this.domaine = new ArrayList<String>(domaine);
	}
	
	public Variable(String name, String[] domaine) {
	    this(name,Arrays.asList(domaine));
	}
	
	public ArrayList<String> getDomaine(){
		return this.domaine;
	}
	
	public boolean equals(Object o){
		if(o instanceof Variable){
			Variable tmp = (Variable) o;
			return nom.equals(tmp.toString());
		}
		return false;
	}
	
	
	public int hashCode(){
		return nom.hashCode() + domaine.hashCode();
	}
	
	public String toString(){
		return nom;
	}
}
