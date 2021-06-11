package fr.ing.interview.bankaccountkata.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ing.interview.bankaccountkata.entities.Account;
import fr.ing.interview.bankaccountkata.entities.Transaction;
import fr.ing.interview.bankaccountkata.enums.StatusType;
import fr.ing.interview.bankaccountkata.enums.TransactionType;
import fr.ing.interview.bankaccountkata.exceptionHandler.AccountNotFoundException;
import fr.ing.interview.bankaccountkata.exceptionHandler.TransactionException;
import fr.ing.interview.bankaccountkata.repository.AccountRepository;
import fr.ing.interview.bankaccountkata.repository.CustomerRepository;
import fr.ing.interview.bankaccountkata.repository.TransactionRepository;
import fr.ing.interview.bankaccountkata.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private TransactionRepository txRepo;

	// User Story 1
	@Override
	public double deposit(long accountId, double amount) {
		synchronized (this) {
			Account account = findAccountDetailsByAccountId(accountId);
			if (amount <= 0.01) {
				LOGGER.error("Deposit amount should be superior to :  " + amount);
				saveTransaction(amount, LocalDateTime.now(), TransactionType.DEPOSIT, account, StatusType.FAILED);
				throw new TransactionException("Deposit amount should be superior to 0.01");
			}
			updateAccount(account, account.getCurrentBalance() + amount);
			saveTransaction(amount, LocalDateTime.now(), TransactionType.DEPOSIT, account, StatusType.SUCCEESS);
			return account.getCurrentBalance();
		}

	}

	// User Story 2
	@Override
	public double withdraw(long accountId, double amount) {
		synchronized (this) {
			Account account = findAccountDetailsByAccountId(accountId);
			if (amount <= 0) {
				LOGGER.error("Withdraw amount should not be negative: s " + amount);
				saveTransaction(amount, LocalDateTime.now(), TransactionType.WITHDRAW, account, StatusType.FAILED);
				throw new TransactionException("Withdraw amount should not be negative: " + amount);
			}
			if (amount > account.getCurrentBalance()) {
				LOGGER.error("Withdraw amount : " + amount + " should not be greater than current balance");
				saveTransaction(amount, LocalDateTime.now(), TransactionType.WITHDRAW, account, StatusType.FAILED);
				throw new TransactionException("Withdraw amount : " + amount +" should not be greater than current balance");
			}
			updateAccount(account, account.getCurrentBalance() - amount);
			saveTransaction(amount, LocalDateTime.now(), TransactionType.WITHDRAW, account, StatusType.SUCCEESS);
			return account.getCurrentBalance();
		}
	}

	// User Story 3
	@Override
	public double getCurrentBalanceByAccountId(long accountId) {
		return findAccountDetailsByAccountId(accountId).getCurrentBalance();
	}

	@Override
	public Account findAccountDetailsByAccountId(long accountId) {
		return accountRepo.findById(accountId).orElseThrow(() -> new AccountNotFoundException(accountId));
	}

	@Override
	public List<Account> findAccountDetailsByCustomerId(long customerId) {
		return accountRepo.findAccountDetailsByCustomerId(
				customerRepo.findById(customerId).orElseThrow(() -> new AccountNotFoundException(customerId)).getId());
	}

	private void updateAccount(Account account, double amount) {
		account.setCurrentBalance(amount);
		accountRepo.save(account);
	}

	private void saveTransaction(double amount, LocalDateTime now, TransactionType txType, Account account,
			StatusType statusType) {
		Transaction transaction = new Transaction(amount, LocalDateTime.now(), txType, account, statusType);
		txRepo.save(transaction);
	}

}
