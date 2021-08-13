package client.Authentication;

import RestConsumer.RestClient;
import client.GUI.GUI;
import client.models.Users;
import org.springframework.web.client.ResourceAccessException;

import javax.swing.*;

public class Authentication 
{	
	private Users user;
	
	public Authentication()
	{
		user = new Users("<firstname>", "<lastname>", "<username>","<password>");
	}	
	
	public void authenticateCredentials(GUI gui,Users userLogin)	//ACCEPTS USER OBJECT CONTAINING USERNAME AND PASSWORD
	{
		try{
			if(userLogin != null)
			{
				user = gui.getRestClient().getUserLogin();
				if(user  == null){
					JOptionPane.showMessageDialog(null,
							"User does not exist!","Log In",
							JOptionPane.WARNING_MESSAGE);
				}else{
					gui.acceptActiveUser(user);
					gui.createDashboard();
					gui.populateCredentialTable();
				}
			}
		}catch (ResourceAccessException r){
			JOptionPane.showMessageDialog(null,
					"Unable to connect to the server!","Connection Failed",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public Users getUser()
	{
		return user;
	}
}
