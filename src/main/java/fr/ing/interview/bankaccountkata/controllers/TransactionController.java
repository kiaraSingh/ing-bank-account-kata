package fr.ing.interview.bankaccountkata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.ing.interview.bankaccountkata.entities.Transaction;
import fr.ing.interview.bankaccountkata.service.TransactionService;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService txService;

	// User Story 4
	@GetMapping(path= "/transactions/{accountId}")
    public ResponseEntity<List<Transaction>> findTransactionHistoryByAccountId(@PathVariable long accountId) {
		List<Transaction> txList= txService.getTxHistoryByAccountId(accountId);
		return ResponseEntity.accepted().body(txList);
	}

	@GetMapping(path= "/transactions")
	public ResponseEntity<List<Transaction>> findAllTransactionHistory() {
		List<Transaction> allTxList = txService.getAllTx();
		return ResponseEntity.accepted().body(allTxList);
	}

}
