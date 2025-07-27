package in.bank.dto.request;


import in.bank.enumop.*;

import lombok.*;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class TransactionDto {
    
    private int id;

    private TransactionType transType;

    private TransactionStatus transStatus;

    @NotNull
    private int transAmount;

    @NotNull
    @NotBlank
    private int prevBalance;

    @NotNull
    @NotBlank
    private int currBalance;
    @NotNull
    @NotBlank
    private String beneAccountNumber;

    @NotNull
    @NotBlank
    private String userAccountNumber;

    private String beneficiaryName;

    private LocalDateTime transTime;
}