package fr.ing.interview.bankaccountkata.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum StatusType {

	SUCCEESS("success"), 
	FAILED("failed");
	
	private final String name;

}
