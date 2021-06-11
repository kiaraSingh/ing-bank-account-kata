package fr.ing.interview.bankaccountkata.enums;


import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum TransactionType {

	DEPOSIT("deposit"), 
	WITHDRAW("withdraw");
	
	private final String name;

}
