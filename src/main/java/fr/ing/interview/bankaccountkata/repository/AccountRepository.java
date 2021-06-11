package fr.ing.interview.bankaccountkata.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.ing.interview.bankaccountkata.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	//@Query("SELECT a FROM Account a JOIN FETCH a.customer c WHERE c.id = :id")
	List<Account> findAccountDetailsByCustomerId(@Param("id")long id);

}
