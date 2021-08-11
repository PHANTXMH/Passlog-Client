package client.Driver;

import RestConsumer.RestClient;
import client.Authentication.Authentication;
import client.GUI.GUI;
import client.models.Credentials;
import client.models.Users;
import org.springframework.web.client.ResourceAccessException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Driver
{
	public static void main(String[] args0)
	{
		new GUI().createAuthenticationGUI();

//		RestClient restClient = new RestClient();

//		restClient.getUserLogin(new Users("jjj","kkk"));
//		restClient.addNewUser(new Users("John","Doe","Mis","Har4"));
//		restClient.addNewCredential(new Credentials("Jokercard", "sss", "meat","gggg", 4));
//		restClient.updateCredential(new Credentials(8,"Jokercard","gg","One of my many Bank logins","rrrr"));
//		restClient.viewCredentialById(8);
//		restClient.viewAllUserCredentialByUserId(8);
//		restClient.deleteCredentialById(9);
//		restClient.searchCredentialByUserId(new Credentials("","","mu",""),4);
	}	
}
