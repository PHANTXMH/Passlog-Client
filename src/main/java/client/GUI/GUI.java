package client.GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import RestConsumer.RestClient;
import client.Authentication.Authentication;
import client.models.Credentials;
import client.models.Token;
import client.models.Users;

public class GUI
{
	private GUI guiClass = this;
	private Authentication login = new Authentication();
	private JFrame frame;
	
	private JPanel themeLabel = new JPanel();
	
	private JLabel searchLabel = new JLabel("Find an application: ");
	private JTextField searchTextField = new JTextField(10);
	private JButton searchButton = new JButton("Search");
	private Color theme;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu credentials = new JMenu("Credentials");
	
	private JMenuItem addCredential = new JMenuItem("Add credential");
	private JMenuItem editCredential = new JMenuItem("Edit credential");
	private JMenuItem deleteCredential = new JMenuItem("Delete credential");
	
	private JMenu settings = new JMenu("Settings");
	
	private JMenuItem changeColour = new JMenuItem("Change theme colour");
	private JMenuItem resetPassword = new JMenuItem("Reset password");
	private JMenuItem logout = new JMenuItem("Log out");

	private JFrame secondFrame = new JFrame();
	private JPanel instructionPanel = new JPanel();
	private JPanel applicationPanel = new JPanel();
	private JPanel usernamePanel = new JPanel();
	private JPanel passwordPanel = new JPanel();
	private JPanel descriptionPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();
	private JPanel firstNamePanel = new JPanel();
	private JPanel lastNamePanel = new JPanel();
	private JPanel addUserPanel = new JPanel();
	private JLabel instructionLabel = new JLabel();
	private JLabel applicationLabel = new JLabel("Application: ");
	private JLabel usernameLabel = new JLabel("Username: ");
	private JLabel passwordLabel = new JLabel("Password: ");
	private JLabel descriptionLabel = new JLabel("Description: ");
	private JLabel firstNameLabel = new JLabel("First Name: ");
	private JLabel lastNameLabel = new JLabel("Last Name: ");
	private JLabel addUserLabel = new JLabel("Create an account.");
	private JTextField applicationTextField = new JTextField(20);
	private JTextField usernameTextField = new JTextField(40);
	private JTextField passwordTextField = new JTextField(40);
	private JPasswordField loginPasswordTextField = new JPasswordField(20);
	private JTextField descriptionTextField = new JTextField(40);
	private JTextField firstNameTextField = new JTextField(20);
	private JTextField lastNameTextField = new JTextField(20);
	private JButton addCredentialButton = new JButton();
	private JButton editCredentialButton = new JButton();
	private JButton searchCredentialButton = new JButton();
	private JButton deleteCredentialButton = new JButton();
	private JButton searchEditCredentialButton = new JButton("Search");
	private JButton searchDeleteCredentialButton = new JButton("Search");
	private JButton addUserButton = new JButton("Create Account");
	public JButton loginButton;
	private static JLabel copyrightLabel = new JLabel("Copyright and Powered by MH Productions © 2021. All Rights Reserved.");
	private JPanel copyrightPanel = new JPanel();
	private final RestClient restClient = new RestClient();
	private  Users activeUser;
	int tpi = 0;	//TABLE POPULATION INCREMENTER
	int records;
	String[] tableHeaders = {"APPLICATION","USERNAME","PASSWORD","DESCRIPTION"};
	String[][] tableCells;
	JTable table = new JTable();
	JScrollPane scrollPane = new JScrollPane();
	List<Credentials> activeUserCredentials;
	List<Credentials> searchCredentials;
	boolean userExist = false;

	public RestClient getRestClient(){
		return restClient;
	}

	ActionListener editCredentialAction = new ActionListener(){
		public void actionPerformed(ActionEvent editCredentialListener)
		{
			if(applicationTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,
						"Atleast the application name and password should be present!",
						"Edit Credenential", JOptionPane.WARNING_MESSAGE);
			}else
			{
				restClient.updateCredential(
						new Credentials(searchCredentials.get(0).getId(),usernameTextField.getText(),
								passwordTextField.getText(),descriptionTextField.getText(),
								applicationTextField.getText()));
				refreshTable();
				populateCredentialTable();
				secondFrame.dispose();
				JOptionPane.showMessageDialog(null,
						"Credential successfully edited",
						"Edit Credenential", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	};

	ActionListener searchEditCredentialAction = new ActionListener(){
		public void actionPerformed(ActionEvent searchEditListener)
		{
			if(applicationTextField.getText().isEmpty() && usernameTextField.getText().isEmpty() &&
					passwordTextField.getText().isEmpty() && descriptionTextField.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Atleast one field should be present!",
						"Edit Credenential", JOptionPane.WARNING_MESSAGE);
			}else
			{
				searchCredentials = restClient.searchCredentialByUserId(
						new Credentials(usernameTextField.getText(),passwordTextField.getText(),
								descriptionTextField.getText(),applicationTextField.getText().toUpperCase()),activeUser.getId());
				if(searchCredentials.size() > 1){
					JOptionPane.showMessageDialog(
							null, "More than one credentials matched your search ("+
									searchCredentials.size()+"), please be more specific.",
							"Edit Credenential", JOptionPane.WARNING_MESSAGE);
				}else
				if(searchCredentials.size() == 1){
					secondFrame.dispose();
					secondFrame = new JFrame("Edit Credential");
					secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

					secondFrame.setVisible(true);
					secondFrame.setSize(new Dimension(600,400));
					secondFrame.setLayout(new GridLayout(6,1,0,0));
					secondFrame.setLocationRelativeTo(null);

					instructionLabel.setText("-FOUND!... Edit the desired fields-");
					editCredentialButton.setText("Save");
					applicationTextField.setText(searchCredentials.get(0).getApplication());
					usernameTextField.setText(searchCredentials.get(0).getUsername());
					passwordTextField.setText(searchCredentials.get(0).getPassword());
					descriptionTextField.setText(searchCredentials.get(0).getDescription());
					instructionPanel.add(instructionLabel);
					applicationPanel.add(applicationLabel);
					applicationPanel.add(applicationTextField);
					usernamePanel.add(usernameLabel);
					usernamePanel.add(usernameTextField);
					passwordPanel.add(passwordLabel);
					passwordPanel.add(passwordTextField);
					descriptionPanel.add(descriptionLabel);
					descriptionPanel.add(descriptionTextField);
					buttonPanel.removeAll();
					buttonPanel.add(editCredentialButton);
					secondFrame.add(instructionPanel);
					secondFrame.add(applicationPanel);
					secondFrame.add(usernamePanel);
					secondFrame.add(passwordPanel);
					secondFrame.add(descriptionPanel);
					secondFrame.add(buttonPanel);

					editCredentialButton.removeActionListener(editCredentialAction);
					editCredentialButton.addActionListener(editCredentialAction);
				}else
				{
					JOptionPane.showMessageDialog(null, "No credentials found!",
							"Edit Credenential", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	};

	ActionListener editCredentialJMenuAction = new ActionListener(){
		public void actionPerformed(ActionEvent editCredentialListener)
		{
			secondFrame = new JFrame("Edit Credential");
			secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

			secondFrame.setSize(new Dimension(600,400));
			secondFrame.setLayout(new GridLayout(6,1,0,0));
			secondFrame.setLocationRelativeTo(null);
			resetCredentialFields();

			instructionLabel.setText("-Enter any field to search for the credential to be EDITED-");
			applicationTextField.setText("");
			usernameTextField.setText("");
			passwordTextField.setText("");
			descriptionTextField.setText("");
			buttonPanel.removeAll();
			passwordPanel.removeAll();

			instructionPanel.add(instructionLabel);
			applicationPanel.add(applicationLabel);
			applicationPanel.add(applicationTextField);
			usernamePanel.add(usernameLabel);
			usernamePanel.add(usernameTextField);
			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordTextField);
			descriptionPanel.add(descriptionLabel);
			descriptionPanel.add(descriptionTextField);
			buttonPanel.add(searchEditCredentialButton);
			secondFrame.add(instructionPanel);
			secondFrame.add(applicationPanel);
			secondFrame.add(usernamePanel);
			secondFrame.add(passwordPanel);
			secondFrame.add(descriptionPanel);
			secondFrame.add(buttonPanel);
			secondFrame.setVisible(true);

			searchEditCredentialButton.removeActionListener(searchEditCredentialAction);
			searchEditCredentialButton.addActionListener(searchEditCredentialAction);
		}
	};

	ActionListener loginAction = new ActionListener()
	{
		public void actionPerformed(ActionEvent loginListener)
		{
			restClient.setUserToken(getLoginCredentials());	//GETS TOKEN FROM SERVER
			login.authenticateCredentials(guiClass, getLoginCredentials());
		}
	};

	ActionListener addUserAction = new ActionListener(){
		public void actionPerformed(ActionEvent addUserListener) {
			if(usernameTextField.getText().length() < 4 || passwordTextField.getText().length() < 4){
				JOptionPane.showMessageDialog(null,
						"Your new username and password should be atleast 4 character!",
						"Add User", JOptionPane.WARNING_MESSAGE);
			}else
			{
//				usernameTextField.setText(usernameTextField.getText().toLowerCase());
				userExist = restClient.addNewUser(new Users(firstNameTextField.getText(),
						lastNameTextField.getText(),usernameTextField.getText().toLowerCase(),
						passwordTextField.getText(),7));
				if(userExist){
					JOptionPane.showMessageDialog(null,
							"I'm afraid this username is already taken. Please try again.",
							"Add User", JOptionPane.WARNING_MESSAGE);
				}else
				{
					secondFrame.dispose();
					JOptionPane.showMessageDialog(null,
							"Account created, thank you for choosing Passlog!",
							"Add User", JOptionPane.INFORMATION_MESSAGE);
					new Authentication().authenticateCredentials(guiClass,
							new Users(usernameTextField.getText(), passwordTextField.getText()));
				}
			}
		}
	};

	ActionListener addCredentialButtonAction = new ActionListener(){
		public void actionPerformed(ActionEvent addCredentialListener)
		{
			if(applicationTextField.getText().isEmpty() || passwordTextField.getText().isEmpty()){
				JOptionPane.showMessageDialog(null,
						"Atleast the application name and password should be added!",
						"Add Credenential", JOptionPane.WARNING_MESSAGE);
			}else
			{
				restClient.addNewCredential(new Credentials(
						usernameTextField.getText(),passwordTextField.getText(),
						descriptionTextField.getText(),applicationTextField.getText(),
						activeUser.getId()));
				secondFrame.dispose();
				refreshTable();
				populateCredentialTable();
				JOptionPane.showMessageDialog(null, "Credential added!",
						"Add Credenential", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	};

	ActionListener addCredentialJMenuAction = new ActionListener(){
		public void actionPerformed(ActionEvent addCredentialListener)
		{
			System.out.println("ADD CREDENTIAL GUI CREATED");
			secondFrame = new JFrame("Add Credential");
			secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

			secondFrame.setSize(new Dimension(600,400));
			secondFrame.setLayout(new GridLayout(5,1,0,0));
			secondFrame.setLocationRelativeTo(null);
			resetCredentialFields();

			applicationTextField.setText("");
			usernameTextField.setText("");
			passwordTextField.setText("");
			descriptionTextField.setText("");
			addCredentialButton.setText("Add");
			buttonPanel.removeAll();
			passwordPanel.removeAll();

			applicationPanel.add(applicationLabel);
			applicationPanel.add(applicationTextField);

			usernamePanel.add(usernameLabel);
			usernamePanel.add(usernameTextField);

			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordTextField);

			descriptionPanel.add(descriptionLabel);
			descriptionPanel.add(descriptionTextField);

			buttonPanel.add(addCredentialButton);

			secondFrame.add(applicationPanel);
			secondFrame.add(usernamePanel);
			secondFrame.add(passwordPanel);
			secondFrame.add(descriptionPanel);
			secondFrame.add(buttonPanel);
			secondFrame.setVisible(true);

			addCredentialButton.removeActionListener(addCredentialButtonAction);
			addCredentialButton.addActionListener(addCredentialButtonAction);
		}
	};

	ActionListener deleteCredentialButtonAction = new ActionListener() {
		public void actionPerformed(ActionEvent deleteCredentialListener) {
			int dialogButton = JOptionPane.showConfirmDialog (null,
					"Are you sure you want this credential deleted?",
					"Delete Credential", JOptionPane.YES_NO_OPTION);

			if(dialogButton == JOptionPane.YES_OPTION)
			{
				restClient.deleteCredentialById(searchCredentials.get(0).getId());
				refreshTable();
				populateCredentialTable();
				secondFrame.dispose();
				JOptionPane.showMessageDialog(null, "Credential successfully deleted",
						"Delete Credenential", JOptionPane.INFORMATION_MESSAGE);
			}else
				secondFrame.dispose();
		}
	};

	ActionListener searchDeleteCredentialAction = new ActionListener() {
		public void actionPerformed(ActionEvent searchDeleteListener) {
			if (applicationTextField.getText().isEmpty() && usernameTextField.getText().isEmpty() &&
					passwordTextField.getText().isEmpty() && descriptionTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Atleast one field should be present!",
						"Delete Credenential", JOptionPane.WARNING_MESSAGE);
			}else
			{
				searchCredentials = restClient.searchCredentialByUserId(
						new Credentials(usernameTextField.getText(), passwordTextField.getText(),
								descriptionTextField.getText(), applicationTextField.getText().toUpperCase()), activeUser.getId());

				if(searchCredentials.size() > 1){
					JOptionPane.showMessageDialog(
							null, "More than one credentials matched your search ("+
									searchCredentials.size()+"), please be more specific.",
							"Delete Credential", JOptionPane.WARNING_MESSAGE);
				}else
				if(searchCredentials.size() == 1){
					secondFrame.dispose();
					secondFrame = new JFrame("Delete Credential");
					secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

					secondFrame.setVisible(true);
					secondFrame.setSize(new Dimension(600,400));
					secondFrame.setLayout(new GridLayout(6,1,0,0));
					secondFrame.setLocationRelativeTo(null);

					instructionLabel.setText("-FOUND!-");
					deleteCredentialButton.setText("Delete");
					applicationTextField.setText(searchCredentials.get(0).getApplication());
					usernameTextField.setText(searchCredentials.get(0).getUsername());
					passwordTextField.setText(searchCredentials.get(0).getPassword());
					descriptionTextField.setText(searchCredentials.get(0).getDescription());
					applicationTextField.setEditable(false);
					usernameTextField.setEditable(false);
					passwordTextField.setEditable(false);
					descriptionTextField.setEditable(false);
					applicationTextField.setBackground(Color.RED);
					usernameTextField.setBackground(Color.RED);
					passwordTextField.setBackground(Color.RED);
					descriptionTextField.setBackground(Color.RED);
					instructionPanel.add(instructionLabel);
					applicationPanel.add(applicationLabel);
					applicationPanel.add(applicationTextField);
					usernamePanel.add(usernameLabel);
					usernamePanel.add(usernameTextField);
					passwordPanel.add(passwordLabel);
					passwordPanel.add(passwordTextField);
					descriptionPanel.add(descriptionLabel);
					descriptionPanel.add(descriptionTextField);
					buttonPanel.removeAll();
					buttonPanel.add(deleteCredentialButton);
					secondFrame.add(instructionPanel);
					secondFrame.add(applicationPanel);
					secondFrame.add(usernamePanel);
					secondFrame.add(passwordPanel);
					secondFrame.add(descriptionPanel);
					secondFrame.add(buttonPanel);

					deleteCredentialButton.removeActionListener(deleteCredentialButtonAction);
					deleteCredentialButton.addActionListener(deleteCredentialButtonAction);
				}else
				{
					JOptionPane.showMessageDialog(null, "No credential matched your search",
							"Delete Credential", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	};

	ActionListener deleteCredentialAction = new ActionListener(){
		public void actionPerformed(ActionEvent deleteCredentialListener)
		{
			secondFrame = new JFrame("Delete Credential");
			secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

			secondFrame.setVisible(true);
			secondFrame.setSize(new Dimension(600,400));
			secondFrame.setLayout(new GridLayout(6,1,0,0));
			secondFrame.setLocationRelativeTo(null);
			resetCredentialFields();

			instructionLabel.setText("-Enter any field to search for the credential to be DELETED-");
			applicationTextField.setText("");
			usernameTextField.setText("");
			passwordTextField.setText("");
			descriptionTextField.setText("");
			searchCredentialButton.setText("Search");
			buttonPanel.removeAll();
			passwordPanel.removeAll();

			instructionPanel.add(instructionLabel);
			applicationPanel.add(applicationLabel);
			applicationPanel.add(applicationTextField);
			usernamePanel.add(usernameLabel);
			usernamePanel.add(usernameTextField);
			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordTextField);
			descriptionPanel.add(descriptionLabel);
			descriptionPanel.add(descriptionTextField);
			buttonPanel.add(searchDeleteCredentialButton);
			secondFrame.add(instructionPanel);
			secondFrame.add(applicationPanel);
			secondFrame.add(usernamePanel);
			secondFrame.add(passwordPanel);
			secondFrame.add(descriptionPanel);
			secondFrame.add(buttonPanel);

			searchDeleteCredentialButton.removeActionListener(searchDeleteCredentialAction);
			searchDeleteCredentialButton.addActionListener(searchDeleteCredentialAction);
		}
	};

	ActionListener deleteCredentialJMenuAction = new ActionListener(){
		public void actionPerformed(ActionEvent deleteCredentialListener)
		{
			secondFrame = new JFrame("Delete Credential");
			secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

			secondFrame.setSize(new Dimension(600,400));
			secondFrame.setLayout(new GridLayout(6,1,0,0));
			secondFrame.setLocationRelativeTo(null);
			resetCredentialFields();

			instructionLabel.setText("-Enter any field to search for the credential to be DELETED-");
			applicationTextField.setText("");
			usernameTextField.setText("");
			passwordTextField.setText("");
			descriptionTextField.setText("");
			searchCredentialButton.setText("Search");
			buttonPanel.removeAll();
			passwordPanel.removeAll();

			instructionPanel.add(instructionLabel);
			applicationPanel.add(applicationLabel);
			applicationPanel.add(applicationTextField);
			usernamePanel.add(usernameLabel);
			usernamePanel.add(usernameTextField);
			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordTextField);
			descriptionPanel.add(descriptionLabel);
			descriptionPanel.add(descriptionTextField);
			buttonPanel.add(searchDeleteCredentialButton);
			secondFrame.add(instructionPanel);
			secondFrame.add(applicationPanel);
			secondFrame.add(usernamePanel);
			secondFrame.add(passwordPanel);
			secondFrame.add(descriptionPanel);
			secondFrame.add(buttonPanel);
			secondFrame.setVisible(true);

			searchDeleteCredentialButton.removeActionListener(searchDeleteCredentialAction);
			searchDeleteCredentialButton.addActionListener(searchDeleteCredentialAction);
		}
	};

	ActionListener logoutAction = new ActionListener(){
		public void actionPerformed(ActionEvent logoutListener)
		{
			int dialogButton = JOptionPane.showConfirmDialog (null,
					"Are you sure?","Log Out", JOptionPane.YES_NO_OPTION);

			if(dialogButton == JOptionPane.YES_OPTION)
			{
				frame.dispose();
				activeUser = null;
				createAuthenticationGUI();
			}
		}
	};

	ActionListener changeColorAction = new ActionListener() {
		public void actionPerformed(ActionEvent changeColourListener) {
			secondFrame.dispose();
			secondFrame = new JFrame("Change Theme");
			secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

			secondFrame.setVisible(true);
			secondFrame.setSize(new Dimension(400,400));
			secondFrame.setLayout(new GridLayout(8,1,0,0));
			secondFrame.setLocationRelativeTo(null);

			instructionPanel.removeAll();
			JPanel redPanel = new JPanel();
			JPanel bluePanel = new JPanel();
			JPanel pinkPanel = new JPanel();
			JPanel greenPanel = new JPanel();
			JPanel greyPanel = new JPanel();
			JPanel yellowPanel = new JPanel();
			JPanel orangePanel = new JPanel();

			instructionLabel.setText("-Select your preferred theme color-");
			JLabel redLabel = new JLabel("RED");
			JLabel blueLabel = new JLabel("BLUE");
			JLabel pinkLabel = new JLabel("PINK");
			JLabel greenLabel = new JLabel("GREEN");
			JLabel greyLabel = new JLabel("GREY");
			JLabel yellowLabel = new JLabel("YELLOW");
			JLabel orangeLabel = new JLabel("ORANGE");

			redLabel.setForeground(Color.RED);
			blueLabel.setForeground(Color.BLUE);
			pinkLabel.setForeground(Color.PINK);
			greenLabel.setForeground(Color.GREEN);
			greyLabel.setForeground(Color.DARK_GRAY);
			yellowLabel.setForeground(Color.YELLOW);
			orangeLabel.setForeground(Color.ORANGE);

			instructionPanel.add(instructionLabel);
			redPanel.add(redLabel);
			bluePanel.add(blueLabel);
			pinkPanel.add(pinkLabel);
			greenPanel.add(greenLabel);
			greyPanel.add(greyLabel);
			yellowPanel.add(yellowLabel);
			orangePanel.add(orangeLabel);

			secondFrame.add(instructionPanel);
			secondFrame.add(redPanel);
			secondFrame.add(bluePanel);
			secondFrame.add(pinkPanel);
			secondFrame.add(greenPanel);
			secondFrame.add(greyPanel);
			secondFrame.add(yellowPanel);
			secondFrame.add(orangePanel);

			redLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(1);

					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			blueLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(3);
					copyrightLabel.setForeground(Color.BLUE);
					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			pinkLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(6);
					copyrightLabel.setForeground(Color.PINK);
					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			greenLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(2);
					copyrightLabel.setForeground(Color.GREEN);
					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			greyLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(7);
					copyrightLabel.setForeground(Color.DARK_GRAY);
					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			yellowLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(4);
					copyrightLabel.setForeground(Color.YELLOW);
					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});

			orangeLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent me) {
					setTheme(5);
					copyrightLabel.setForeground(Color.ORANGE);
					JOptionPane.showMessageDialog(null, "Theme updated successfully!",
							"Change Theme", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	};

	MouseAdapter createUserAction = new MouseAdapter() {
		public void mouseClicked(MouseEvent me) {
			frame.dispose();
			secondFrame = new JFrame("Add User");
			secondFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));

			secondFrame.setVisible(true);
			secondFrame.setSize(new Dimension(600, 400));
			secondFrame.setLayout(new GridLayout(6, 1, 0, 0));
			secondFrame.setLocationRelativeTo(null);
			secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			instructionLabel.setText("-Please enter your details to create your account-");
			firstNameTextField.setText("");
			lastNameTextField.setText("");
			usernameTextField.setText("");
			passwordTextField.setText("");
			usernameTextField.setColumns(20);
			passwordTextField.setColumns(20);
			usernamePanel.removeAll();
			passwordPanel.removeAll();
			instructionPanel.removeAll();
			usernamePanel.removeAll();
			passwordPanel.removeAll();
			buttonPanel.removeAll();

			instructionPanel.add(instructionLabel);
			firstNamePanel.add(firstNameLabel);
			firstNamePanel.add(firstNameTextField);
			lastNamePanel.add(lastNameLabel);
			lastNamePanel.add(lastNameTextField);
			usernamePanel.add(usernameLabel);
			usernamePanel.add(usernameTextField);
			passwordPanel.add(passwordLabel);
			passwordPanel.add(passwordTextField);
			buttonPanel.add(addUserButton);

			secondFrame.add(instructionPanel);
			secondFrame.add(firstNamePanel);
			secondFrame.add(lastNamePanel);
			secondFrame.add(usernamePanel);
			secondFrame.add(passwordPanel);
			secondFrame.add(buttonPanel);

			addUserButton.removeActionListener(addUserAction);
			addUserButton.addActionListener(addUserAction);
		}
	};

	public Users getLoginCredentials(){
		if(usernameTextField.getText().isEmpty() || loginPasswordTextField.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Both fields must be entered!",
					"Log In", JOptionPane.WARNING_MESSAGE);
			throw new IllegalStateException("Both fields must be entered!");
		}else
		return new Users(usernameTextField.getText().toLowerCase(),loginPasswordTextField.getText());
	}

	public void acceptActiveUser(Users user){
		this.activeUser = user;
	}

	public void populateCredentialTable()
	{
		activeUserCredentials = restClient.getAllUserCredentialByUserId(activeUser.getId());	//GETS ROWS OF CREDENTIALS

		if(activeUserCredentials.isEmpty()){
			JLabel emptyCredentialLabel = new JLabel("CREDENTIAL LIST IS EMPTY", SwingConstants.CENTER);
			emptyCredentialLabel.setFont(new Font("serif",Font.ITALIC,30));

			scrollPane = new JScrollPane(emptyCredentialLabel);

			frame.add(scrollPane,BorderLayout.CENTER);
			frame.revalidate();
			System.out.println("CREDENTIAL TABLE POPULATED");
		}else{
			tableCells = new String[activeUserCredentials.size()][4];
			tpi = 0;

			activeUserCredentials.forEach(c ->{
				tableCells[tpi][0] = c.getApplication();
				tableCells[tpi][1] = c.getUsername();
				tableCells[tpi][2] = c.getPassword();
				tableCells[tpi][3] = c.getDescription();
				tpi++;
			});
			System.out.println("Cells populated with "+tpi+" rows.");

			table = new JTable(tableCells, tableHeaders);
			scrollPane = new JScrollPane(table);

			frame.add(scrollPane,BorderLayout.CENTER);
			frame.revalidate();
			System.out.println("CREDENTIAL TABLE POPULATED");
		}
	}

	private void refreshTable(){
		scrollPane.remove(table);
		frame.remove(scrollPane);
	}

	private void resetCredentialFields(){
		applicationTextField.setEditable(true);
		usernameTextField.setEditable(true);
		passwordTextField.setEditable(true);
		descriptionTextField.setEditable(true);
		applicationTextField.setBackground(Color.WHITE);
		usernameTextField.setBackground(Color.WHITE);
		passwordTextField.setBackground(Color.WHITE);
		descriptionTextField.setBackground(Color.WHITE);
	}

	private void setTheme(int userTheme){
		if(userTheme == 1){
			theme = Color.RED;
			activeUser.setTheme(1);
		}else
		if(userTheme == 2){
			theme = Color.GREEN;
			activeUser.setTheme(2);
		}else
		if(userTheme == 3){
			theme = Color.BLUE;
			activeUser.setTheme(3);
		}else
		if(userTheme == 4){
			theme = Color.YELLOW;
			activeUser.setTheme(4);
		}else
		if(userTheme == 5)
		{
			theme = Color.ORANGE;
			activeUser.setTheme(5);
		}else
		if(userTheme == 6){
			theme = Color.PINK;
			activeUser.setTheme(6);
		}else
		if(userTheme == 7)	//DEFAULT
		{
			theme = Color.DARK_GRAY;
			activeUser.setTheme(7);
		}else
		{
			theme = Color.DARK_GRAY;
		}
		themeLabel.setBackground(theme);
		copyrightLabel.setForeground(theme);
		restClient.updateActiveUserTheme(activeUser);
		secondFrame.dispose();
	}
	
	public void createAuthenticationGUI()
	{		
		frame = new JFrame("Password Logger");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));
		usernamePanel = new JPanel();
		passwordPanel = new JPanel();
		buttonPanel = new JPanel();
		usernameTextField.setEditable(true);
		usernameTextField.setText("");
		usernameTextField.setBackground(Color.WHITE);
		loginPasswordTextField.setText("");
		loginButton = new JButton("Log In");

		usernameTextField.setColumns(20);
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(new Dimension(400,200));
		frame.setLayout(new GridLayout(4,1,0,0));
		
		usernamePanel.add(usernameLabel);
		usernamePanel.add(usernameTextField);
		
		passwordPanel.add(passwordLabel);
		passwordPanel.add(loginPasswordTextField);
		
		buttonPanel.add(loginButton);

		addUserLabel.setFont(new Font("Italic", Font.ITALIC, 12));
		addUserLabel.setForeground(Color.BLUE);
		addUserPanel.add(addUserLabel);
		
		frame.add(usernamePanel);
		frame.add(passwordPanel);
		frame.add(buttonPanel);
		frame.add(addUserPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		loginButton.removeActionListener(loginAction);
		loginButton.addActionListener(loginAction);

		addUserLabel.removeMouseListener(createUserAction);
		addUserLabel.addMouseListener(createUserAction);
	}
	
	public void createDashboard()
	{
		frame.dispose();
		frame = new JFrame("Dashboard");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));
		themeLabel = new JPanel();
		
		searchLabel = new JLabel("Find an application: ");
		searchTextField = new JTextField(10);
		searchButton = new JButton("Search");
		
		setTheme(activeUser.getTheme());
		
		frame.setVisible(true);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		themeLabel.setBackground(theme);
		frame.add(themeLabel,BorderLayout.NORTH);
		copyrightPanel.add(copyrightLabel);
		frame.add(copyrightPanel,BorderLayout.SOUTH);
		
		menuBar.add(credentials);
		
		credentials.add(addCredential);
		credentials.add(editCredential);
		credentials.add(deleteCredential);
		
		menuBar.add(settings);
		
		settings.add(changeColour);
		settings.add(logout);		

		addCredential.removeActionListener(addCredentialJMenuAction);
		addCredential.addActionListener(addCredentialJMenuAction);

		editCredential.removeActionListener(editCredentialJMenuAction);
		editCredential.addActionListener(editCredentialJMenuAction);

		deleteCredential.removeActionListener(deleteCredentialJMenuAction);
		deleteCredential.addActionListener(deleteCredentialJMenuAction);

		logout.removeActionListener(logoutAction);
		logout.addActionListener(logoutAction);

		changeColour.removeActionListener(changeColorAction);
		changeColour.addActionListener(changeColorAction);
	}
}
