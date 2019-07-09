package com.ms.tring.bank.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ms.tring.bank.api.AccountDetail;
import com.ms.tring.bank.api.AccountNotFoundException;
import com.ms.tring.bank.api.BankException;
import com.ms.tring.bank.api.BankingServices;
import com.ms.tring.bank.api.Customer;
import com.ms.tring.bank.api.InavlidRequestException;
import com.ms.tring.bank.api.InsuffecientBalanceException;
import com.ms.tring.bank.api.InvalidCustomerException;
import com.ms.tring.bank.api.Statements;

@Service
public class SimpleBank implements BankingServices{
	
	private static List<AccountDetail> customersList=new ArrayList<>();
	private static List<HashMap<AccountDetail, Statements>> statementList=new ArrayList<>();
	private static int accountNumber=1000;
	private static int transID=2000;

	@Override
	public AccountDetail createAccount(Customer customer) throws InvalidCustomerException, BankException {
		AccountDetail accountDetail=new AccountDetail();
		accountDetail.setCustomer(customer);
		accountDetail.setNumber(++accountNumber);
		customersList.add(accountDetail);
		return accountDetail;
		
	}

	@Override
	public Statements deposit(int accountNumber, double amount) throws AccountNotFoundException, InavlidRequestException {
		// TODO Auto-generated method stub
		AccountDetail accDet=null;
		HashMap<AccountDetail,Statements> statementMap=new HashMap<>();
		for(AccountDetail accountDetail:customersList)
		{
			if(accountDetail.getNumber()==accountNumber)
			{
				accountDetail.setBalance(accountDetail.getBalance()+amount);
				accDet=accountDetail;
				break;
			}
		}
		Statements statements=new Statements();
		statements.setId(++transID);
		statements.setAmount(amount);
		statements.setType("deposit");
		statementMap.put(accDet, statements);
		statementList.add(statementMap);
		return statements;
	}

	@Override
	public Statements withdrawl(int accountNumber, double amount)
			throws AccountNotFoundException, InavlidRequestException, InsuffecientBalanceException, BankException {
		// TODO Auto-generated method stub
		AccountDetail accDet=null;
		HashMap<AccountDetail,Statements> statementMap=new HashMap<>();
		for(AccountDetail accountDetail:customersList)
		{
			if(accountDetail.getNumber()==accountNumber)
			{
				accountDetail.setBalance(accountDetail.getBalance()-amount);
				accDet=accountDetail;
				break;
			}
		}
		Statements statements=new Statements();
		statements.setId(++transID);
		statements.setAmount(amount);
		statements.setType("withdrawl");
		statementMap.put(accDet, statements);
		statementList.add(statementMap);
		return statements;
		
	}

	@Override
	public List<Statements> getStatementDetails(int accountNumber) throws AccountNotFoundException, BankException {
		// TODO Auto-generated method stub
		AccountDetail accDet=null;
		List<Statements> statementListComp=new ArrayList<>();
		for(AccountDetail accountDetail:customersList)
		{
			if(accountDetail.getNumber()==accountNumber)
			{
				accDet=accountDetail;
				break;
			}
		}
		if(accDet!=null)
		{
			for(HashMap<AccountDetail, Statements> statemet: statementList)
			{
				if(statemet.get(accDet)!=null)
				{
					statementListComp.add(statemet.get(accDet));
				}
			}
		}
		return statementListComp;
	}

	@Override
	public AccountDetail getAccountDetail(int accountNumber) throws AccountNotFoundException, BankException {
		// TODO Auto-generated method stub
		AccountDetail accDet=null;
		for(AccountDetail accountDetail:customersList)
		{
			if(accountDetail.getNumber()==accountNumber)
			{
				accDet=accountDetail;
				break;
			}
		}
		return accDet;
	}

}
