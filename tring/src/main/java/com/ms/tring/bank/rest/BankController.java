package com.ms.tring.bank.rest;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.ms.tring.bank.api.AccountDetail;
import com.ms.tring.bank.api.BankingServices;
import com.ms.tring.bank.api.Customer;
import com.ms.tring.bank.api.Statements;

@RestController
@RequestMapping(value="/yb")
public class BankController {
	
	@Autowired
	private BankingServices bankingServices;
	
	private static int customerId=0;
	
	@PostMapping("/account")
	public ResponseEntity<AccountDetail> open(@RequestBody 
			Customer customer)
	{
		customer.setId(++customerId);
		AccountDetail ac=bankingServices.createAccount(customer);
		/*URI uri=ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(customer.getId()).toUri();*/
		if(ac!=null)
		{
			return new ResponseEntity<AccountDetail>(ac,HttpStatus.OK);
		}
		return ResponseEntity.notFound().build();
	}
	
	public ResponseEntity<Statements> transact(@PathVariable("number") int number,@RequestParam("type") String type,
			@RequestParam("amount") double amount,UriComponentsBuilder builder)
	{
		Statements st=null;
		if(type.equalsIgnoreCase("deposit"))
		{
			st=bankingServices.deposit(number, amount);
			
		}
		else if(type.equalsIgnoreCase("withdraw"))
		{
			st=bankingServices.withdrawl(number, amount);
		}
		HttpHeaders headers=new HttpHeaders();
		headers.setLocation(
				builder.path("/account/{number}/transaction/{id}").buildAndExpand(number,st.getId()).toUri());
		return new ResponseEntity<Statements>(st,headers,HttpStatus.CREATED);
	}
	

}
