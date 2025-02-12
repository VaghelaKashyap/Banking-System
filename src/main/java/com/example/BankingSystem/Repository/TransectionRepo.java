package com.example.BankingSystem.Repository;

import com.example.BankingSystem.Entity.Transection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransectionRepo extends JpaRepository<Transection, Long> {
}
