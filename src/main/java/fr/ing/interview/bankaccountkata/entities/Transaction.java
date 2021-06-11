package fr.ing.interview.bankaccountkata.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.ing.interview.bankaccountkata.enums.StatusType;
import fr.ing.interview.bankaccountkata.enums.TransactionType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "transaction", schema = "bank_account_kata")
public class Transaction implements Serializable {
	

	private static final long serialVersionUID = -6425607025032676948L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
	@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", schema = "bank_account_kata", initialValue = 5)
	@JsonIgnore
    private long id;
	
	@NonNull
    private double amount;
	
	@NonNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime txDate;
	
	@NonNull
	private TransactionType txType;
	
	@NonNull
	@ManyToOne
	@JsonIgnore
	private Account account;
	
	@NonNull
	private StatusType statusType;

}
