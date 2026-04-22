package com.bank.banking_system.repository;

import com.bank.banking_system.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findAllByUsername(String username);

    Optional<Account> findByUsername(String username); // for hasAccount

    long countByUsernameAndAccountType(String username, String accountType);
}