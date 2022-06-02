package com.innowise.secret_santa.repository;

import com.innowise.secret_santa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByEmail (String email);


}
