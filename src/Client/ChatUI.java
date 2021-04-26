package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

public class ChatUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	static String name, message, dob, country;
	private ChatClient chatClient;
	private JPanel textPanel;
	public JScrollPane scrollPane;
	private Integer usersCount = 0;
	static String SEND_ACTION = "loginAction";
	JTextField countUsersArea;
	protected JTextArea outputArea, userArea, inputArea, userList;
	protected JFrame frame;
	protected JButton sendButton, sendMsgButton;
	protected JPanel defaulUsersPanel, usersPanel, countPanel;
	protected JList<String> jList;
	protected DefaultListModel<String> defaultList;
	private Document document;
	StyledDocument doc;
	public Component textScrollPane;

	public static void main(String[] args) {

		// set the look
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (Exception e) {
		}
		// display login popup first
		RegisterLoginPopup.main(args);

	}
	// constructor of the UI
	public ChatUI() {

		// main frame
		frame = new JFrame("Welcome");
		
		// working area that goes into frame
		Container container = getContentPane();
		
		// setting JPanel to go into container with BroderLayout 
		JPanel mainPanel = new JPanel(new BorderLayout());

		// adding separate methods to the JPanel
		mainPanel.add(addOutputPanel(), BorderLayout.CENTER);
		mainPanel.add(addInputPanel(), BorderLayout.SOUTH);
		mainPanel.add(usersCount(usersCount), BorderLayout.NORTH);
		container.setLayout(new BorderLayout());
		container.add(mainPanel, BorderLayout.CENTER);
		container.add(addRightSide(), BorderLayout.EAST);
		usersPanel.add(countPanel, BorderLayout.SOUTH);

		
		frame.add(container);
		frame.setResizable(false);
		frame.setSize(800, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		
		// closes the chat client window. 
		// Sends messag to all to notify about leaving. 
		// Removes from chatClient vector. 
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {

				if (chatClient != null) {
					try {
						sendMessage("Bye all, I am leaving");
						chatClient.IServer.leaveChat(name);

					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
		Runtime.getRuntime().gc();
	}
	// function is triggered from RegisterLoginPopup to initialise reigstration
	public JTextArea loginPopUp() {

		try {
			getConnected(name, dob, country);
			// System.out.println("getConnected(name, dob, country);" +name+ dob+ country);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		// if getConnected was successfully set window title 
		frame.setTitle("Chat away " + name);

		return outputArea;
	}
	// text area for the input of the messages
	public JPanel addInputPanel() {
		
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputArea = new JTextArea();
		inputPanel.setBorder(new EmptyBorder(5, 5, 10, 10));
		//inputArea.setPreferredSize(new Dimension(540, 90));
		//inputPanel.setPreferredSize(new Dimension(600, 60));
		inputPanel.add(addButton(), BorderLayout.EAST);
		inputPanel.add(inputArea);
		return inputPanel;

	}
	
	// JTextPane for chat messages to be displayed
	public JPanel addOutputPanel() {

		JTextPane outputArea = new JTextPane();
		doc = outputArea.getStyledDocument();



		outputArea.setMargin(new Insets(10, 10, 10, 10));
		
		outputArea.setEditable(false);
		textScrollPane = new JScrollPane(outputArea);
		textScrollPane.setPreferredSize(new Dimension(650, 410));
		textPanel = new JPanel();
		textPanel.add(textScrollPane);

		return textPanel;

	}
	//  placeholder for list of users to go in
	public JPanel addRightSide() {
		
		usersPanel = new JPanel(new BorderLayout()); 
		usersPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // [top, left, bottom,right] 
		JLabel usersLabel = new JLabel("Select User to PM");
		usersLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
		usersLabel.setLayout(null);

		String[] noUsers = { "" };
		updateUsersPanel(noUsers);

		usersPanel.add(usersLabel, BorderLayout.NORTH);

		return usersPanel;

	}
	
	public JPanel updateUsersPanel(String[] list) {

		usersCount = 0;
		defaulUsersPanel = new JPanel();
		defaultList = new DefaultListModel<String>();

		// iterate the list of users from server to add them to the list that is used to show users in the right side panel
		for (String u : list) {
			defaultList.addElement("User: " + u);
			usersCount++;
			System.out.print("Count: " + usersCount);
		}
		
		// initialise list and set options of the ListSelectionModel
		jList = new JList<String>(defaultList);
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		jList.setVisibleRowCount(30);
		
		scrollPane = new JScrollPane(jList);
		usersPanel.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setPreferredSize(new Dimension(120, 450));
		//usersPanel.add(defaulUsersPanel, BorderLayout.CENTER);
		scrollPane.repaint();
		scrollPane.revalidate();
		
		// update JTextfield that displays number of users logged in
		countUsersArea.setText("Users:" + usersCount);
		countUsersArea.repaint();
		countUsersArea.revalidate();
		
		
		
		return usersPanel;
	}
	// update JTextfield that displays number of users logged in
	public JPanel usersCount(int usersCount) {

		countPanel = new JPanel();
		countUsersArea = new JTextField();
		
		countUsersArea.setMinimumSize(getMinimumSize());
		countUsersArea.setMargin(new Insets(10, 10, 10, 10));
		countUsersArea.setEditable(false);
		countPanel.add(countUsersArea, BorderLayout.CENTER);
		
		return countPanel;
	}
	
	// Same button for all purposes where SEND_ACTION command defined dynamically. 
	// It works in actionPerformed() to change the value if users selected.
	public JButton addButton() {

		sendMsgButton = new JButton("Send");
		//sendMsgButton.setPreferredSize(new Dimension(100, 220));
		
		// SEND_ACTION depends on privatelist in actionPerformed()
		sendMsgButton.setActionCommand(SEND_ACTION);
		sendMsgButton.addActionListener(this);
		inputArea.setFocusable(true);
		inputArea.setFont(inputArea.getFont().deriveFont(40f));
		
		// get text from jTextField input area
		message = inputArea.getText();		
		// initialy button is disabled 
		sendMsgButton.setEnabled(false);		
		// gets the state of the button
		document = inputArea.getDocument();		
		// implement a class to change the estate of the button to disabled if it is empty
		document.addDocumentListener(new JButtonStateController(sendMsgButton));

		return sendMsgButton;
	}
    // action listener
	public void actionPerformed(ActionEvent e) {

		// set message text from inputArea to be sent
		message = inputArea.getText();
		
		// idea was to select multiple user but then went single item selection.
		// Todo change to getSelectedIndex() 
		int[] privateList = jList.getSelectedIndices();
		System.out.print("getSelectionIndex():" + privateList);

		// if user is selected in the usersArea set button action to private message
		if (privateList.length > 0) {
			SEND_ACTION = "pmAction";
			// else send to all
		} else {
			SEND_ACTION = "sendAction";
		}
		
		// invokes this if user has selected user to send PM
		if (SEND_ACTION == "pmAction") {

			inputArea.setText("");
			try {
				// need to change this to int instead
				sendPrivateMessage(privateList);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// sends message to all users
		if (SEND_ACTION == "sendAction") {

			inputArea.setText("");
			try {
				sendMessage(message);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	// rmi methods goes here

	// initiates to send message to the users in the list. Need to change to int instead.
	private void sendPrivateMessage(int[] list) throws RemoteException {
		String pm = name + " just sent you private a message: \n" + message + "\n";
		chatClient.IServer.sendPrivate(list, pm);
	}

	// takes user submitted details and sends to the server for name check and addition to the chatClients Vector.
	void getConnected(String name, String dob, String country) throws RemoteException {
		// if user used special characters this will remove it. 
		name = name.replaceAll("\\s+", "_");
		name = name.replaceAll("\\W+", "_");
		try {
			// create new chatClient instance
			chatClient = new ChatClient(this, name, dob, country);
			// invokes constructor in chatClient class for the connection and communication.
			chatClient.startClient();

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	// sends chatMessage to the server for distribution to all connected clients
	private void sendMessage(String chatMessage) throws RemoteException {
		chatClient.IServer.massSend(name + ": " + chatMessage, name);
	}

}
