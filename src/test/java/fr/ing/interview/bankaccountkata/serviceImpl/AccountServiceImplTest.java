package fr.ing.interview.bankaccountkata.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import fr.ing.interview.bankaccountkata.entities.Account;
import fr.ing.interview.bankaccountkata.entities.Customer;
import fr.ing.interview.bankaccountkata.entities.Transaction;
import fr.ing.interview.bankaccountkata.enums.StatusType;
import fr.ing.interview.bankaccountkata.enums.TransactionType;
import fr.ing.interview.bankaccountkata.exceptionHandler.AccountNotFoundException;
import fr.ing.interview.bankaccountkata.exceptionHandler.TransactionException;
import fr.ing.interview.bankaccountkata.repository.AccountRepository;
import fr.ing.interview.bankaccountkata.repository.CustomerRepository;
import fr.ing.interview.bankaccountkata.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class AccountServiceImplTest {

	@InjectMocks
	private AccountServiceImpl accountService;

	@MockBean
	private AccountRepository accountRepo;

	@MockBean
	private CustomerRepository customerRepo;

	@MockBean
	private TransactionRepository txRepo;

	@Captor
	private ArgumentCaptor<Transaction> captor;

	@BeforeEach
	void setUp() throws Exception {
		Customer customer = new Customer("Loki");
		customer.setId(1L);
		Account account = new Account(0.0, customer);
		account.setId(1L);
		List<Account> list = new ArrayList<>();
		list.add(account);
		when(accountRepo.findById(1L)).thenReturn(Optional.of(account));
		when(accountRepo.findById(2L)).thenReturn(Optional.empty());
		when(accountRepo.findAccountDetailsByCustomerId(1L)).thenReturn(list);
	}

	@Test
	void test_deposit_validAmount() {
		double amount = 10.0;
		assertEquals(amount, accountService.deposit(1L, amount));
		verify(txRepo, times(1)).save(captor.capture());
		Transaction captured = captor.getValue();
		assertEquals(1L, captured.getAccount().getId());
		assertEquals(TransactionType.DEPOSIT, captured.getTxType());
		assertEquals(StatusType.SUCCEESS, captured.getStatusType());
		assertEquals(amount, captured.getAmount());
	}

	@Test
	void test_deposit_invalidAmount() {
		double amount = -1;
		TransactionException exception = assertThrows(TransactionException.class, () -> {
			accountService.deposit(1L, amount);
		});
		String expectedMessage = "Deposit amount should be superior to 0.01";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void test_withdraw_validAmount() {
		Customer customer = new Customer("Thor");
		customer.setId(3L);
		Account account = new Account(0.0, customer);
		account.setId(3L);
		double balance = 60;
		account.setCurrentBalance(balance);
		when(accountRepo.findById(3L)).thenReturn(Optional.of(account));
		double amount = 20;
		assertEquals(balance - amount, accountService.withdraw(3L, amount));
		verify(txRepo, times(1)).save(captor.capture());
		Transaction captured = captor.getValue();
		assertEquals(3L, captured.getAccount().getId());
		assertEquals(TransactionType.WITHDRAW, captured.getTxType());
		assertEquals(StatusType.SUCCEESS, captured.getStatusType());
		assertEquals(amount, captured.getAmount());
	}

	@Test
	void test_withdraw_invalidAmount_whenAmountIsNegative() {
		double amount = -1;
		TransactionException exception = assertThrows(TransactionException.class, () -> {
			accountService.withdraw(1L, amount);
		});
		String expectedMessage = "Withdraw amount should not be negative: " + amount;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void test_withdraw_invalidAmount_whenAmountIsLessThanBalance() {
		double amount = 1000000;
		TransactionException exception = assertThrows(TransactionException.class, () -> {
			accountService.withdraw(1L, amount);
		});
		String expectedMessage = "Withdraw amount : " + amount + " should not be greater than current balance";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	void test_getBalance_validAccountId() {
		assertThat(accountService.getCurrentBalanceByAccountId(1L)).isEqualTo(0.0);
	}

	@Test
	void test_getBalance_invalidAccountId() {
		AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
			accountService.getCurrentBalanceByAccountId(2L);
		});
		assertTrue(exception.getMessage().equals("Could not find account for given id: 2"));
	}

}
