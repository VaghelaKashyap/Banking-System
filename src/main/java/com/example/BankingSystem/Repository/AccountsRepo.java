package com.example.BankingSystem.Repository;

import com.example.BankingSystem.Entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepo extends JpaRepository<Accounts, Long> {

    Accounts findByMobileNumber(Long mobileNumber);
}
