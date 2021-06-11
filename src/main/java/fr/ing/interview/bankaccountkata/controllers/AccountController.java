package fr.ing.interview.bankaccountkata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ing.interview.bankaccountkata.entities.Account;
import fr.ing.interview.bankaccountkata.service.AccountService;

@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	// User Story 1
	@PutMapping("/account/deposit/{accountId}")
	public ResponseEntity<Double> depositToAccount(@PathVariable long accountId, @RequestBody double amount) {
		Double currentBalance = accountService.deposit(accountId, amount);
		return ResponseEntity.accepted().body(currentBalance);
	}

	// User Story 2
	@PutMapping("/account/withdraw/{accountId}")
	public ResponseEntity<Double> withdrawFromAccount(@PathVariable long accountId, @RequestBody double amount) {
		Double currentBalance = accountService.withdraw(accountId, amount);
		return ResponseEntity.accepted().body(currentBalance);
	}

	// User Story 3
	@GetMapping("/account/balance/{accountId}")
	public ResponseEntity<Double> getCurrentBalanceByAccountId(@PathVariable long accountId) {
		Double currentBalance = accountService.getCurrentBalanceByAccountId(accountId);
		return ResponseEntity.accepted().body(currentBalance);
	}

	@GetMapping("/account/customer/{customerId}")
	public ResponseEntity<List<Account>> findAllAccountsByCustomerId(@PathVariable long customerId) {
		List<Account> accountDetailByCustomerId = accountService.findAccountDetailsByCustomerId(customerId);
		return ResponseEntity.accepted().body(accountDetailByCustomerId);

	}

	@GetMapping("/account/{accountId}")
	public ResponseEntity<Account> findAccountByAccountId(@PathVariable long accountId) {
		Account accountById = accountService.findAccountDetailsByAccountId(accountId);
		return ResponseEntity.accepted().body(accountById);
	}

}
