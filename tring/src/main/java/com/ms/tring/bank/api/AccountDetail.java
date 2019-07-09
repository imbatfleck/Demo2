package com.ms.tring.bank.api;

import java.util.List;

public class AccountDetail {
	
	private int number;
	private Customer customer;
	private double balance;
	private List<Statements> history;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public List<Statements> getHistory() {
		return history;
	}
	public void setHistory(List<Statements> history) {
		this.history = history;
	}
	
	
	

}
