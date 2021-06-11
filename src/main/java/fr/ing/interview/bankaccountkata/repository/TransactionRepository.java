package fr.ing.interview.bankaccountkata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.ing.interview.bankaccountkata.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	//@Query("SELECT t FROM Transaction t JOIN FETCH t.account a WHERE a.accountId = :id")
	List<Transaction> findTxHistoryByAccountId(@Param("id")long id);
}