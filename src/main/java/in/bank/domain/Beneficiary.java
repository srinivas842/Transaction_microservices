package in.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "beneficiary_details")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor

public class Beneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int beneficiaryId;

    private String name;

    private String userAccountNumber;

    @Column(unique = true)
    private String beneAccountNumber;

    private String ifscCode;

    private String bankName;

    private String branchName;

    private String accountType;

    private int balance;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}