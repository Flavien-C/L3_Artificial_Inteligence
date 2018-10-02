package representations;

import java.util.*;

public class Variable {
    
    private String name;
    private Set<String> domain;
    
    public Variable(String name, Set<String> domain) {
        this.name = name;
        this.domain = domain;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Set<String> getDomain() {
        return this.domain;
    }
    
    @Override
    public String toString() {
        return this.name + " : " + this.domain;
    }
}