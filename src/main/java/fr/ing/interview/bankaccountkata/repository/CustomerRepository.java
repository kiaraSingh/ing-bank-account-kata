package fr.ing.interview.bankaccountkata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.ing.interview.bankaccountkata.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
