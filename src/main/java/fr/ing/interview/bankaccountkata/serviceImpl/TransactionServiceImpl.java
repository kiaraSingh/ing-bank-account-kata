package fr.ing.interview.bankaccountkata.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ing.interview.bankaccountkata.entities.Transaction;
import fr.ing.interview.bankaccountkata.exceptionHandler.TransactionException;
import fr.ing.interview.bankaccountkata.repository.TransactionRepository;
import fr.ing.interview.bankaccountkata.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository txRepo;

	// User Story 4
	@Override
	public List<Transaction> getTxHistoryByAccountId(long accountId) {
		List<Transaction> txList = txRepo.findTxHistoryByAccountId(accountId);
		if (txList.isEmpty())
			throw new TransactionException("No Transaction History for accountId: " + accountId);
		return txList;
	}

	@Override
	public List<Transaction> getAllTx() {
		List<Transaction> allTxList = txRepo.findAll();
		if (allTxList.isEmpty())
			throw new TransactionException("No Transaction History");
		return allTxList;
	}

}
