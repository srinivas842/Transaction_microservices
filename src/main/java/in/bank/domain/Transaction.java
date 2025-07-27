package in.bank.domain;

import javax.persistence.*;
import in.bank.enumop.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "transactions")
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private TransactionType transType;
    private TransactionStatus transStatus;
    private int transAmount;
    private int currBalance;
    private int prevBalance;
    private String beneAccountNumber;
    private String userAccountNumber;
    private LocalDate transDate;

    private String beneficiaryName;
}
