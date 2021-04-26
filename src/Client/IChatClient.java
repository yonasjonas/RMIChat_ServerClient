package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.text.SimpleAttributeSet;

public interface IChatClient extends Remote {
	
	public void messageFromServer(String message, SimpleAttributeSet keyWord) throws RemoteException;
	public void openLogin(String newMessage, boolean b) throws RemoteException;
	public void showUserList(String[] users) throws RemoteException;	
}
