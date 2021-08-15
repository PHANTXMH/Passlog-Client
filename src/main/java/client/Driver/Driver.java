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
	}	
}
