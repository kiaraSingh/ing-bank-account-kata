package fr.ing.interview.bankaccountkata.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.ing.interview.bankaccountkata.BankAccountKataApplication;
import fr.ing.interview.bankaccountkata.entities.Account;
import fr.ing.interview.bankaccountkata.entities.Customer;
import fr.ing.interview.bankaccountkata.service.AccountService;

@SpringBootTest(classes = BankAccountKataApplication.class)
@AutoConfigureMockMvc
class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	private AccountController accountController;

	@Mock
	private AccountService accountService;

	@BeforeEach
	void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
		Customer customer = new Customer("Neha");
		customer.setId(1L);
		Account account = new Account(100.0, customer);
		account.setId(1L);
		when(accountService.getCurrentBalanceByAccountId(1L)).thenReturn(account.getCurrentBalance());
	}

	@Test
	void givenAccountIdAsInput_toDeposit_thenCheckOk() throws Exception {
		mockMvc.perform(get("/account/deposit/1").content("{\"id\": \"1L\",\"amount\": \"100.0\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	void givenAccountIdAsInput_toWithdraw_thenCheckOk() throws Exception {
		mockMvc.perform(get("/account/withdraw/1").content("{\"id\": \"1L\",\"amount\": \"100.0\"}")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isAccepted());
	}

	@Test
	void givenAccountIdAsInput_toGetBalance_thenCheckOk() throws Exception {
		mockMvc.perform(get("/account/balance/1").content("{\"id\": \"1L\"")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isAccepted());
	}

}
