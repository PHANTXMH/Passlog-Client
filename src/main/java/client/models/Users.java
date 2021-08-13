package client.models;

import java.awt.*;
import java.io.Serializable;
import java.util.Collection;

public class Users implements Serializable
{
	private static final long serialVersionUID = 1L;
	private int id;
	private String fname;
	private String lname;
	private String username;
	private String password;
	private int theme;
	private Collection<Role> roles;
	
	public Users(){
		this.id = 0;
		this.fname = "<FirstName>";
		this.lname = "<LastName";
		this.username = "<Username>";
		this.password = "<Password>";
		this.theme = 7;
	}

	public Users(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	public Users(String fname, String lname, String username, String password) {
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.password = password;
	}

	public Users(String fname, String lname, String username, String password, int theme){
		this.fname = fname;
		this.lname = lname;
		this.username = username;
		this.password = password;
		this.theme = theme;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
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

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", fname='" + fname + '\'' +
				", lname='" + lname + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", theme=" + theme +
				'}';
	}
}
