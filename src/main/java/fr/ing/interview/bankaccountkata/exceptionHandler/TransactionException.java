package fr.ing.interview.bankaccountkata.exceptionHandler;


public class TransactionException  extends RuntimeException {
	
	private static final long serialVersionUID = -4328233516364564347L;

	public TransactionException(String reason) {
		super(reason);
	}

}
