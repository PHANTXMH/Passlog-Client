package client.models;

import java.io.Serializable;

public class Credentials implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String password;
	private String description;
	private String application;
	private int user_id;
	
	public Credentials() {
		
	}

	public Credentials(String username, String password, String description, String application)
	{
		this.username = username;
		this.password = password;
		this.description = description;
		this.application = application;
	}

	public Credentials(int id, String username, String password, String description, String application)
	{
		this.id = id;
		this.username = username;
		this.password = password;
		this.description = description;
		this.application = application;
	}
	
	public Credentials(String username, String password, String description, String application, int user_id)
	{		
		this.username = username;
		this.password = password;
		this.description = description;
		this.application = application;
		this.user_id = user_id;
	}
	
	public Credentials(int id, String username, String password, String description, String application, int user_id)
	{		
		this.id = id;
		this.username = username;
		this.password = password;
		this.description = description;
		this.application = application;
		this.user_id = user_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "Credentials:\n{\nid= "+id+"\nusername= " + username + ", \npassword= " + password + ", \ndescription= " + description
				+ ",\napplication= " + application + ", \nuser_id= " + user_id + "\n}";
	}	
}
