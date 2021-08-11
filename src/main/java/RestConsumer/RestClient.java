package RestConsumer;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import client.models.Credentials;
import client.models.Users;

import javax.swing.*;

public class RestClient {
	
	private static final String  URL = "http://localhost:8080";
	
	private static final String GET_USER_LOGIN = URL+"/api/login";
	private static final String ADD_USER = URL+"/api/createaccount";
	private static final String ADD_CREDENTIAL = URL+"/api/addcredential";
	private static final String EDIT_CREDENTIAL = URL+"/api/editcredential";
	private static final String VIEW_CREDENTIAL_BY_ID = URL+"/api/viewcredential/{credentialId}";
	private static final String VIEW_ALL_USER_CREDENTIAL_BY_USER_ID = URL+"/api/viewcredential/all/{userId}";
	private static final String SEARCH_CREDENTIAL_BY_ID = URL+"/api/searchcredential/{userId}";
	private static final String DELETE_CREDENTIAL_BY_ID = URL+"/api/deletecredential/{credentialId}";
	private static final String UPDATE_USER_THEME = URL+"/api/updateusertheme";
	
	static RestTemplate restTemplate = new RestTemplate();
	
	public Users getUserLogin(Users user) {
		user.setUsername(user.getUsername().toLowerCase());
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Users> entity = new HttpEntity<Users>(user, headers);
		try{
			ResponseEntity<Users> userLogin = restTemplate.postForEntity(GET_USER_LOGIN, entity, Users.class);
			return userLogin.getBody();
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
		if(credential.getPassword().length() == 0 || credential.getApplication().length() == 0){
			if(credential.getPassword().length() == 0)
				System.out.println("The password field is empty!");
			if(credential.getApplication().length() == 0)
				System.out.println("The application field is empty");
		}else
			try{
				restTemplate.postForEntity(ADD_CREDENTIAL, credential, Credentials.class);
			}catch (HttpServerErrorException e){
				System.out.println("User which created the credential does not exist!");
			}
	}
	
	public void updateCredential(Credentials credential) {
		if(credential.getPassword().length() == 0 || credential.getApplication().length() == 0) {
			if (credential.getPassword().length() == 0)
				System.out.println("The password field is empty!");
			if (credential.getApplication().length() == 0)
				System.out.println("The application field is empty");
		}else
		try{
			restTemplate.put(EDIT_CREDENTIAL, credential);
		}catch (HttpServerErrorException e){
			System.out.println("Credential to be updated could not be found!");
		}
	}
	
	public void getCredentialById(int credentialid) {
		System.out.println("Credential Id accepted: "+credentialid);
		Map<String, Integer> param = new HashMap<>();
		param.put("credentialId", credentialid);
		try{
			Credentials credential = restTemplate.getForObject(VIEW_CREDENTIAL_BY_ID,Credentials.class,param);
			System.out.println(credential.toString());
		}catch (HttpServerErrorException e){
			System.out.println("Credential does not exist!");
		}
	}	
	
	public List<Credentials> getAllUserCredentialByUserId(int userid) {
		Map<String, Integer> param = new HashMap<>();
		param.put("userId", userid);
		try{
			HttpEntity<Credentials[]> credential =
					restTemplate.getForEntity(VIEW_ALL_USER_CREDENTIAL_BY_USER_ID, Credentials[].class, param);

			return Arrays.asList(credential.getBody());
		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
			return null;
		}
	}

	public void deleteCredentialById(int credentialid) {
		Map<String, Integer> param = new HashMap<>();
		param.put("credentialId", credentialid);
		try{
			restTemplate.delete(DELETE_CREDENTIAL_BY_ID, param);
		}catch (HttpServerErrorException e){
			System.out.println("Credential does not exist");
		}
	}

	public List<Credentials> searchCredentialByUserId(Credentials credential,int userid){
		Map<String, Integer> param = new HashMap<>();
		param.put("userId", userid);
		try{
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<Credentials> entity = new HttpEntity<>(credential,headers);

			ResponseEntity<Credentials[]> credentials =
					restTemplate.exchange(SEARCH_CREDENTIAL_BY_ID,HttpMethod.POST,entity,Credentials[].class,param);

			return Arrays.asList(credentials.getBody());

		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
			return null;
		}
	}

	public void updateActiveUserTheme(Users user){
		try{
			restTemplate.put(UPDATE_USER_THEME, user);
		}catch (HttpServerErrorException e){
			System.out.println("User does not exist!");
		}
	}
}
