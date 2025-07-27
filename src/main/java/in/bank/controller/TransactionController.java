package in.bank.controller;

import in.bank.domain.Beneficiary;
import in.bank.domain.Transaction;
import in.bank.dto.request.BeneficiaryDto;
import in.bank.dto.request.TransactionDto;
import in.bank.exception.TransactionDoesNotExistException;
import in.bank.service.TransactionService;
import in.bank.exception.BeneficiaryAlreadyExistException;
import in.bank.exception.BeneficiaryDoesNotExistException;
import in.bank.exception.TransactionFailedException;

import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/api/transactions")
@Slf4j
public class TransactionController {

    private TransactionService transactionService;

    private ModelMapper modelMapper;

    public TransactionController(TransactionService transactionService, ModelMapper modelMapper) {
        this.transactionService = transactionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/details/{userAccountNumber}")
    public ResponseEntity<?> getBeneficiariesByUserAccountNumber(
            @PathVariable("userAccountNumber") String userAccountNumber) {
        List<Beneficiary> beneficiaries = transactionService.findBeneficiariesByUserAccountNumber(userAccountNumber);
        if (beneficiaries.isEmpty()) {
            throw new BeneficiaryDoesNotExistException(
                    "No beneficiaries found for account number: " + userAccountNumber);
        }
        return ResponseEntity.ok(beneficiaries);
    }

    @GetMapping("/all-transactions")
    public ResponseEntity<?> getAllTransactions() {
        List<Transaction> transaction = transactionService.findAllTransactions();
        if (transaction.isEmpty()) {
            throw new TransactionDoesNotExistException("No Transactions Found");
        }
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/beneficiary")
    public ResponseEntity<?> addBeneficiary(@Valid @RequestBody BeneficiaryDto beneficiaryDto) {
        Optional<Beneficiary> beneficiaryExists = transactionService
                .findByBeneAccountNumber(beneficiaryDto.getBeneAccountNumber());
        if (beneficiaryExists.isPresent()) {
            log.error("Exception in adding beneficiary: {}", beneficiaryDto);
            throw new BeneficiaryAlreadyExistException("Beneficiary already exists");
        }
        Beneficiary beneficiary = modelMapper.map(beneficiaryDto, Beneficiary.class);
        var response = transactionService.addBeneficiary(beneficiary);
        var responseDto = modelMapper.map(response, BeneficiaryDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/debit")
    public ResponseEntity<?> deditAccount(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.debitAccount(transactionDto.getUserAccountNumber(),
                transactionDto.getBeneAccountNumber(), transactionDto.getTransAmount());
        if (transaction != null) {
            transactionService.save(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        }
        throw new TransactionFailedException("Transaction Failed!!!");
    }

    @PostMapping("/credit")
    public ResponseEntity<?> creditAccount(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.creditAccount(transactionDto.getUserAccountNumber(),
                transactionDto.getBeneAccountNumber(), transactionDto.getTransAmount());
        if (transaction != null) {
            transactionService.save(transaction);
            return ResponseEntity.status(HttpStatus.OK).body(transaction);
        }
        throw new TransactionFailedException("Payment not credited!!!");
    }

    @GetMapping("/viewTransactions/{userAccountNumber}")
    public ResponseEntity<?> getAlLTransactions(@PathVariable("userAccountNumber") String userAccountNumber) {
        List<Transaction> transactions = transactionService.findTransactionsByUserAccountNumber(userAccountNumber);
        if (transactions.isEmpty()) {
            throw new TransactionFailedException("No Transactions");
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/date-range/{startDate}/{endDate}/{userAccountNumber}")
    public List<Transaction> getTransactionsByTransDate(
            @PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @PathVariable("userAccountNumber") String userAccountNumber) {

        List<Transaction> transactions = transactionService.findTransactionsByTransDateBetween(startDate, endDate,
                userAccountNumber);
        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getTransDate();
            if (transactionDate.isEqual(startDate) || transactionDate.isEqual(endDate)
                    || (transactionDate.isAfter(startDate) && transactionDate.isBefore(endDate))) {
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;
    }

}
