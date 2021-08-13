package RestConsumer;

import java.io.DataOutputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.util.*;

import client.models.Token;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import client.models.Credentials;
import client.models.Users;

import javax.swing.*;
import javax.validation.constraints.NotNull;

public class RestClient {
	
	private static final String  URL = "http://localhost:8080";

	private static final String GET_USER_TOKEN = URL+"/api/login";
	private static final String GET_USER_LOGIN = URL+"/api/authenticate";
	private static final String ADD_USER = URL+"/api/createaccount";
	private static final String ADD_CREDENTIAL = URL+"/api/addcredential";
	private static final String EDIT_CREDENTIAL = URL+"/api/editcredential";
	private static final String VIEW_CREDENTIAL_BY_ID = URL+"/api/viewcredential/{credentialId}";
	private static final String VIEW_ALL_USER_CREDENTIAL_BY_USER_ID = URL+"/api/viewcredential/all/{userId}";
	private static final String SEARCH_CREDENTIAL_BY_ID = URL+"/api/searchcredential/{userId}";
	private static final String DELETE_CREDENTIAL_BY_ID = URL+"/api/delete/credential/{credentialId}";
	private static final String UPDATE_USER_THEME = URL+"/api/updateusertheme";
	private static RestTemplate restTemplate = new RestTemplate();
	private Token token;
	
	public RestClient(){

	}

	public void setUserToken(Users user){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		String body = "username="+user.getUsername()+"&password="+user.getPassword();
		HttpEntity entity = new HttpEntity(body,headers);
		try{
			ResponseEntity<String[]> response = restTemplate.postForEntity(GET_USER_TOKEN,entity, String[].class);
			String[] userToken = response.getBody();
			if(userToken != null){
				this.token = new Token(userToken[0], userToken[1]);
			}
		}catch (Exception e){
			JOptionPane.showMessageDialog(null,
					"Unable to connect to the server!","Connection Failed",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public Users getUserLogin() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token.getAccessToken());
		HttpEntity entity = new HttpEntity(headers);
		try{
			ResponseEntity<Users> response = restTemplate.postForEntity(GET_USER_LOGIN, entity, Users.class);
			return response.getBody();
		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
			return null;
		}
	}
	
	public boolean addNewUser(Users user) {
		user.setUsername(user.getUsername().toLowerCase());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		ResponseEntity<Boolean> created = restTemplate.postForEntity(ADD_USER, entity,Boolean.class);

		return created.getBody();
	}
	
	public void addNewCredential(Credentials credential) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token.getAccessToken());
		HttpEntity entity = new HttpEntity(credential,headers);
		if(credential.getPassword().length() == 0 || credential.getApplication().length() == 0){
			if(credential.getPassword().length() == 0)
				System.out.println("The password field is empty!");
			if(credential.getApplication().length() == 0)
				System.out.println("The application field is empty");
		}else
			try{
				restTemplate.postForEntity(ADD_CREDENTIAL, entity, Credentials.class);
			}catch (HttpServerErrorException e){
				System.out.println("User which created the credential does not exist!");
			}
	}
	
	public void updateCredential(Credentials credential) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token.getAccessToken());
		HttpEntity entity = new HttpEntity(credential,headers);
		if(credential.getPassword().length() == 0 || credential.getApplication().length() == 0) {
			if (credential.getPassword().length() == 0)
				System.out.println("The password field is empty!");
			if (credential.getApplication().length() == 0)
				System.out.println("The application field is empty");
		}else
		try{
			restTemplate.put(EDIT_CREDENTIAL, entity);
		}catch (HttpServerErrorException e){
			System.out.println("Credential to be updated could not be found!");
		}
	}
	
	public void getCredentialById(int credentialid) {
		String body = "credentialId="+credentialid;
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token.getAccessToken());
		HttpEntity entity = new HttpEntity(body,headers);
//		Map<String, Integer> param = new HashMap<>();
//		param.put("credentialId", credentialid);
		try{
			Credentials credential = restTemplate.getForObject(VIEW_CREDENTIAL_BY_ID,Credentials.class,entity);
		}catch (HttpServerErrorException e){
			System.out.println("Credential does not exist!");
		}
	}	
	
	public List<Credentials> getAllUserCredentialByUserId(int userid) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token.getAccessToken());
		HttpEntity entity = new HttpEntity(headers);
		Map<String, Integer> param = new HashMap<>();
		param.put("userId", userid);
		try{
			HttpEntity<Credentials[]> credential =
			restTemplate.exchange(VIEW_ALL_USER_CREDENTIAL_BY_USER_ID,HttpMethod.GET,entity,Credentials[].class,param);
			return Arrays.asList(credential.getBody());
		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
			return null;
		}
	}

	public void deleteCredentialById(int credentialid) {
		HashMap<String, Integer> param = new HashMap();
		param.put("credentialId",credentialid);
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token.getAccessToken());
		HttpEntity entity = new HttpEntity(headers);
		try{
//			restTemplate.delete(DELETE_CREDENTIAL_BY_ID,entity);
			restTemplate.exchange(DELETE_CREDENTIAL_BY_ID,HttpMethod.DELETE,entity,(Class<Object>) null,param);
		}catch(HttpServerErrorException e){
			System.out.println("Credential does not exist!");
		}
	}

	public List<Credentials> searchCredentialByUserId(Credentials credential,int userid){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+this.token.getAccessToken());
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Credentials> entity = new HttpEntity<>(credential,headers);
		Map<String, Integer> param = new HashMap<>();
		param.put("userId", userid);
		try{
			ResponseEntity<Credentials[]> credentials =
					restTemplate.exchange(SEARCH_CREDENTIAL_BY_ID,HttpMethod.POST,entity,Credentials[].class,param);
			return Arrays.asList(credentials.getBody());
		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
			return null;
		}
	}

	public void updateActiveUserTheme(Users user){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+this.token.getAccessToken());
		HttpEntity entity = new HttpEntity(user,headers);
		try{
			restTemplate.put(UPDATE_USER_THEME, entity);
		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
		}
	}

	public Token getToken(){
		return token;
	}

	public void setToken(Token token){
		this.token = token;
	}
}
