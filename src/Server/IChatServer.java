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