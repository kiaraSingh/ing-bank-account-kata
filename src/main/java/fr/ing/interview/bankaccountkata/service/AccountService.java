package fr.ing.interview.bankaccountkata.service;

import java.util.List;

import fr.ing.interview.bankaccountkata.entities.Account;

public interface AccountService {

	public double getCurrentBalanceByAccountId(long accountId);

	public Account findAccountDetailsByAccountId(long accountId);

	public List<Account> findAccountDetailsByCustomerId(long customerId);

	public double deposit(long accountId, double amount);

	public double withdraw(long accountId, double amount);

}
