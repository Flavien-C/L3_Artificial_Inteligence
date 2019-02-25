package extraction;

import java.util.*;
import representations.*;

public class AssociationRule{
	private Set<Variable> premisse;
	private Set<Variable> conclusion;
	
	public AssociationRule(Set<Variable> premisse, Set<Variable> conclusion){
		this.premisse = premisse;
		this.conclusion = conclusion;
	}
	
	public Set<Variable> getPremisse(){return this.premisse;}
	public Set<Variable> getConclusion(){return this.conclusion;}
	
	public boolean equals(Object o){
		if(o instanceof AssociationRule){
			AssociationRule tmp = (AssociationRule) o;
			return tmp.getPremisse().equals(this.premisse) && tmp.getConclusion().equals(this.conclusion);
		}
		
		return false;
	}
	
	public int hashCode(){
		return premisse.hashCode() + conclusion.hashCode();
	}
}
