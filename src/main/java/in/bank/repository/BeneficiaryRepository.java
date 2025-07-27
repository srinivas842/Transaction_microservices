package in.bank.repository;

import in.bank.domain.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary , Integer> {

    Optional<Beneficiary> findByBeneAccountNumber(String beneAccountNumber); //Beneficiary account number

    List<Beneficiary> findBeneficiariesByUserAccountNumber(String userAccountNumber);

    Beneficiary findByBeneAccountNumberAndUserAccountNumber(String beneAccountNumber, String userAccountNumber);
}

