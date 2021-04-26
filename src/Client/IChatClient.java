/*******************************************************************************
 * Java RMI Chat application
 * Built for the Distributed Systems & Systems Integration Continuous Assigment
 * DT249/4 CMPU4022
 * By: Jonas Samaitis Student Id: D17124413
 ******************************************************************************/
package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.text.SimpleAttributeSet;

public interface IChatClient extends Remote {
	
	public void messageFromServer(String message, SimpleAttributeSet keyWord) throws RemoteException;
	public void openLogin(String newMessage, boolean b) throws RemoteException;
	public void showUserList(String[] users) throws RemoteException;	
}
