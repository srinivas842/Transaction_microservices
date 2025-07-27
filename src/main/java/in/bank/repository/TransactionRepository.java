package in.bank.repository;

import in.bank.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Modifying
    @Query("update Transaction t set t.currBalance = t.currBalance + :amount where t.userAccountNumber = :userAccountNumber and t.beneAccountNumber = :beneAccountNumber")

    Transaction debitAccount(@Param("userAccountNumber") String userAccountNumber,
                             @Param("beneAccountNumber") String beneficiaryId, @Param("amount")
                             int amount);
    @Query("SELECT t FROM Transaction t WHERE t.transDate BETWEEN :startDate AND :endDate AND t.userAccountNumber = :userAccountNumber")
    List<Transaction> findTransactionsByTransDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userAccountNumber") String userAccountNumber);

    List<Transaction> findTransactionsByUserAccountNumber(String userAccountNumber);
}
