package in.bank.service;
import in.bank.domain.*;
import in.bank.enumop.*;
import in.bank.repository.*;


import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;


@Service
public class TransactionServiceImpl implements TransactionService {

    private String url = "http://localhost:8080/api/customers/user-data/{userAccountNumber}";

    private ResponseEntity<CustomerAccount> accountRE;

    private BeneficiaryRepository beneficiaryRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    RestTemplate restTemplate = new RestTemplate();

    public TransactionServiceImpl(BeneficiaryRepository beneficiaryRepository) {
        this.beneficiaryRepository = beneficiaryRepository;
    }

    @Override
    public Beneficiary addBeneficiary(Beneficiary beneficiary) {

        return beneficiaryRepository.save(beneficiary);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Beneficiary> findByBeneAccountNumber(String beneAccountNumber) {
        return beneficiaryRepository.findByBeneAccountNumber(beneAccountNumber);
    }

    @Override
    public List<Beneficiary> findBeneficiariesByUserAccountNumber(String userAccountNumber) {
        return beneficiaryRepository.findBeneficiariesByUserAccountNumber(userAccountNumber);
    }


    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public List<Transaction> findTransactionsByUserAccountNumber(String userAccountNumber) {
        return transactionRepository.findTransactionsByUserAccountNumber(userAccountNumber);
    }

    @Override
    @Transactional
    public List<Transaction> findTransactionsByTransDateBetween(LocalDate fromDate, LocalDate toDate, String userAccountNumber) {
        return transactionRepository.findTransactionsByTransDateBetween(fromDate,toDate,userAccountNumber);
    }


    @Override
    public CustomerAccount getUserByUserAccountNumber(@PathVariable("userAccountNumber") String userAccountNumber) {
        accountRE = restTemplate.getForEntity(url, CustomerAccount.class, userAccountNumber);
        if (accountRE.getStatusCode().is2xxSuccessful()) {
            CustomerAccount user = accountRE.getBody();
            return user;
        }
        return null;
    }

    public void updateAccountBalance(String userAccountNumber, int amount) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/api/customers/user-data/" + userAccountNumber;
        CustomerAccount account = getUserByUserAccountNumber(userAccountNumber);
        account.setBalance(amount);
        restTemplate.put(url, account);
    }

    @Override
    @Transactional
    public Transaction debitAccount(String userAccountNumber, String beneAccountNumber, int amount) {
        Beneficiary beneficiary = beneficiaryRepository.findByBeneAccountNumberAndUserAccountNumber(beneAccountNumber, userAccountNumber);
        if (beneficiary == null) {
            throw new IllegalArgumentException("Invalid beneficiary or user account number");
        }
        CustomerAccount user = getUserByUserAccountNumber(userAccountNumber);
        int userBalance = user.getBalance();
        if(userBalance > amount) {
            int previousBalance = user.getBalance();
            int newUserBalance = previousBalance - amount;
            updateAccountBalance(userAccountNumber, newUserBalance);
            int newBalance = beneficiary.getBalance() + amount;
            beneficiary.setBalance(newBalance);
            beneficiaryRepository.save(beneficiary);
            Transaction transaction = new Transaction();
            transaction.setTransType(TransactionType.DEBIT);
            transaction.setTransStatus(TransactionStatus.SUCCESS);
            transaction.setTransAmount(amount);
            transaction.setBeneficiaryName(beneficiary.getName());
            transaction.setPrevBalance(previousBalance);
            transaction.setCurrBalance(newUserBalance);
            transaction.setBeneAccountNumber(beneAccountNumber);
            transaction.setUserAccountNumber(userAccountNumber);
            transaction.setTransDate(LocalDate.now());
            return transactionRepository.save(transaction);
        }
        else {
            throw new IllegalArgumentException("Insufficient balance in user account number " + userAccountNumber + " to credit " + amount + " to beneficiary account number " + beneAccountNumber);
        }
    }

    @Override
    @Transactional
    public Transaction creditAccount(String userAccountNumber, String beneAccountNumber, int amount) {
        Beneficiary beneficiary = beneficiaryRepository.findByBeneAccountNumberAndUserAccountNumber(beneAccountNumber, userAccountNumber);
        CustomerAccount user = getUserByUserAccountNumber(userAccountNumber);
        if(user != null) {
            int previousBalance = user.getBalance();
            int newUserBalance = previousBalance + amount;
            updateAccountBalance(userAccountNumber, newUserBalance);
            Transaction transaction = new Transaction();
            transaction.setTransType(TransactionType.CREDIT);
            transaction.setTransStatus(TransactionStatus.SUCCESS);
            transaction.setTransAmount(amount);
            transaction.setPrevBalance(previousBalance);
            transaction.setCurrBalance(newUserBalance);
            transaction.setUserAccountNumber(userAccountNumber);
            transaction.setBeneAccountNumber(beneAccountNumber);
            transaction.setBeneficiaryName(beneficiary.getName());
            transaction.setTransDate(LocalDate.now());
            return transactionRepository.save(transaction);
        }
        return null;
    }
}
