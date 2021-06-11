package fr.ing.interview.bankaccountkata.service;

import java.util.List;

import fr.ing.interview.bankaccountkata.entities.Transaction;

public interface TransactionService {
	
	public List<Transaction> getAllTx();
	
	public List<Transaction> getTxHistoryByAccountId(long accountId);

}
