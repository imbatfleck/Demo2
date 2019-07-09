package com.ms.tring.bank.api;
import java.util.List;

public interface BankingServices {

	public AccountDetail createAccount(Customer customer) throws InvalidCustomerException, BankException;

	public Statements deposit(int accountNumber, double amount) throws AccountNotFoundException, InavlidRequestException;

	public Statements withdrawl(int accountNumber, double amount)
			throws AccountNotFoundException, InavlidRequestException, InsuffecientBalanceException, BankException;

	public List<Statements> getStatementDetails(int accountNumber) throws AccountNotFoundException, BankException;

	public AccountDetail getAccountDetail(int accountNumber) throws AccountNotFoundException, BankException;

}
