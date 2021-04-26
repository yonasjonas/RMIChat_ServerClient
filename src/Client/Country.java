/*******************************************************************************
 * Java RMI Chat application
 * Built for the Distributed Systems & Systems Integration Continuous Assigment
 * DT249/4 CMPU4022
 * By: Jonas Samaitis Student Id: D17124413
 ******************************************************************************/
package Client;

// country class for the implementation in the country selection on the initial login

public class Country implements Comparable<Country> {
    private String code;
    private String name;
 
    // model of country
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Override
    public String toString() {
        return this.name;
    }
 
    @Override
    public int compareTo(Country anotherCountry) {
        return this.name.compareTo(anotherCountry.getName());
    }  
    
}
