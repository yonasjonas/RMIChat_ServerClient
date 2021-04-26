/*******************************************************************************
 * Java RMI Chat application
 * Built for the Distributed Systems & Systems Integration Continuous Assigment
 * DT249/4 CMPU4022
 * By: Jonas Samaitis Student Id: D17124413
 ******************************************************************************/
package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatServer extends Remote {

	public void registerUser(String[] details)throws RemoteException;
	public void initiateRegister(String[] details) throws RemoteException;
	public void sendPrivate(int[] list, String message) throws RemoteException;
	void leaveChat(String name) throws RemoteException;
	public void massSend(String chatMessage, String name) throws RemoteException; 
	
}
