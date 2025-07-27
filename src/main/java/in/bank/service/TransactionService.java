package in.bank.service;

import in.bank.domain.Beneficiary;
import in.bank.domain.CustomerAccount;
import in.bank.domain.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface TransactionService {

    Transaction save(Transaction transaction);

    Beneficiary addBeneficiary(Beneficiary beneficiary);

    Optional<Beneficiary> findByBeneAccountNumber(String beneAccountNumber);

    List<Beneficiary> findBeneficiariesByUserAccountNumber(String userAccountNumber);

    List<Transaction> findAllTransactions();

    Transaction debitAccount(String userAccountNumber,String beneficiaryId, int amount);

    Transaction creditAccount(String userAccountNumber,String beneAccountNumber, int Amount);

    CustomerAccount getUserByUserAccountNumber(String userAccountNumber);

    void updateAccountBalance(String userAccountNumber, int newUserBalance);

    List<Transaction> findTransactionsByTransDateBetween(LocalDate fromDate, LocalDate toDate, String userAccountNumber);

    List<Transaction> findTransactionsByUserAccountNumber(String userAccountNumber);

}

