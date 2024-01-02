package com.picpay.simple.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay.simple.domain.transaction.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
         Optional<Transaction>findById(Long id);
}
