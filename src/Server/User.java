/*******************************************************************************
 * Java RMI Chat application
 * Built for the Distributed Systems & Systems Integration Continuous Assigment
 * DT249/4 CMPU4022
 * By: Jonas Samaitis Student Id: D17124413
 ******************************************************************************/
package Server;

import Client.IChatClient;

public class User {
	
	String name;
	String dob;
	String country;
	IChatClient clientInfo;
	
	public User(String name, String dob, String country, IChatClient clientInfo) {
		this.name = name;
		this.dob = dob;
		this.country = country;
		this.clientInfo = clientInfo;
	}
	
	
	public String getName() {
		return name;
	}
	public String getDOB() {
		return dob;
	}
	public String getCountry() {
		return country;
	}
	public IChatClient getClientInfo() {
		return clientInfo;
	}
}
