package fr.ing.interview.bankaccountkata.exceptionHandler;


public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -4426706882494821387L;

	public AccountNotFoundException(Long id) {
		super("Could not find account for given id: " + id);
	}
}
