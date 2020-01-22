package com.yodlee.agentpush.AgentMigration.restoperation;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import com.yodlee.agentpush.AgentMigration.adtutility.ADTRequest;
import com.yodlee.agentpush.AgentMigration.adtutility.ADTResponse;
import com.yodlee.agentpush.AgentMigration.oauthtoken.AuthenticationDeatils;
import com.yodlee.agentpush.AgentMigration.oauthtoken.LoginResponseEntity;

public class TokenGenerator {
	public static HttpHeaders httpHeaders=new HttpHeaders();
	public static RestTemplate restTemplate=new RestTemplate();
	public static String tokeValue=null;
	public static final String tokenAdder="Bearer ";
	public static AuthenticationDeatils authenticationDeatils=new AuthenticationDeatils();
	public static LoginResponseEntity loginResponseEntity=new LoginResponseEntity();
	public static RestOperation restOperation=new RestOperation();
	public static String getToken()
	{
		authenticationDeatils.setUsername("mrao2");
		authenticationDeatils.setPassword("N@vindivya2015");
		
		loginResponseEntity=restOperation.getPostResponseEntity(authenticationDeatils, loginResponseEntity, restTemplate);
		
		String token=loginResponseEntity.getToken();
		tokeValue=tokenAdder+token;
		return tokeValue;
	}
	public static ADTResponse ADTPushService(String url,ADTRequest adtRequest)
	{
		ADTResponse adtResponse=new ADTResponse();
		restOperation.setHeaders(httpHeaders, tokeValue);
		adtResponse=restOperation.getPostResponseExchange(url, httpHeaders, adtRequest, adtResponse, restTemplate);
		return adtResponse;
	}


}
