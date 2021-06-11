package fr.ing.interview.bankaccountkata.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ing.interview.bankaccountkata.exceptionHandler.AccountNotFoundException;
import fr.ing.interview.bankaccountkata.exceptionHandler.TransactionException;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ResponseBody
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<?> accountNotFoundExceptionHandler(AccountNotFoundException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ResponseBody
	@ExceptionHandler(TransactionException.class)
	public ResponseEntity<?> transactionExceptionHandler(TransactionException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}
}