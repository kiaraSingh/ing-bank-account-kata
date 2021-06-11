package fr.ing.interview.bankaccountkata.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.ing.interview.bankaccountkata.BankAccountKataApplication;
import fr.ing.interview.bankaccountkata.entities.Account;
import fr.ing.interview.bankaccountkata.entities.Customer;
import fr.ing.interview.bankaccountkata.entities.Transaction;
import fr.ing.interview.bankaccountkata.enums.StatusType;
import fr.ing.interview.bankaccountkata.enums.TransactionType;
import fr.ing.interview.bankaccountkata.service.TransactionService;

@SpringBootTest(classes = BankAccountKataApplication.class)
@AutoConfigureMockMvc
class TransactionControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	private TransactionController txController;
	
	@Mock
	private TransactionService txService;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(txController).build();
		Customer customer = new Customer("Neha");
		customer.setId(1L);
		Account account = new Account(100.0,customer);
		account.setId(1L);
		Transaction transaction =  new Transaction(200.0, LocalDateTime.now(), TransactionType.WITHDRAW, account, StatusType.FAILED);
		List<Transaction> txList = Stream.of(transaction).collect(Collectors.toList());
		when(txService.getTxHistoryByAccountId(1L)).thenReturn(txList);
		
	}
	
	
	@Test
	void  givenAccountIdAsInput_thenCheckOk() throws Exception {
		mockMvc.perform(get("/transactions/1")
                .content("{\"amount\": \"200\", \"txType\": \"WITHDRAW\",\"statusType\": \"FAILED\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
	}

}
