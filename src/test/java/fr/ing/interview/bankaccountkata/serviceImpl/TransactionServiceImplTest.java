package fr.ing.interview.bankaccountkata.serviceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import fr.ing.interview.bankaccountkata.entities.Account;
import fr.ing.interview.bankaccountkata.entities.Customer;
import fr.ing.interview.bankaccountkata.entities.Transaction;
import fr.ing.interview.bankaccountkata.enums.StatusType;
import fr.ing.interview.bankaccountkata.enums.TransactionType;
import fr.ing.interview.bankaccountkata.exceptionHandler.TransactionException;
import fr.ing.interview.bankaccountkata.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class TransactionServiceImplTest {

	@InjectMocks
	private TransactionServiceImpl txService;

	@Mock
	private TransactionRepository txRepo;

	Transaction depositTx, withdrawTx;

	@BeforeEach
	void setUp() throws Exception {
		Customer customer1 = new Customer("Loki");
		customer1.setId(1L);
		Account account1 = new Account(0.0, customer1);
		account1.setId(1L);
		Customer customer2 = new Customer("Thor");
		customer1.setId(1L);
		Account account2 = new Account(0.0, customer2);
		account2.setId(1L);
		depositTx = new Transaction(800.0, LocalDateTime.now(), TransactionType.DEPOSIT, account1, StatusType.SUCCEESS);
		withdrawTx = new Transaction(200.0, LocalDateTime.now(), TransactionType.WITHDRAW, account2, StatusType.FAILED);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(depositTx);
		transactions.add(withdrawTx);
		// when(accountService.isAccountExist(1L)).thenReturn(true);
		when(txRepo.findTxHistoryByAccountId(1L)).thenReturn(transactions);
	}

	@Test
	void test_findTransactions_validAccountId() {
		assertThat(txService.getTxHistoryByAccountId(1L)).hasSize(2).containsExactly(depositTx, withdrawTx);
	}
	
	@Test
	void test_findTransactions_invalidAccountId() {
		long accountId = -20L;
		TransactionException exception = assertThrows(TransactionException.class, () -> {
			txService.getTxHistoryByAccountId(accountId);
		});
		String expectedMessage = "No Transaction History for accountId: " + accountId;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

}
