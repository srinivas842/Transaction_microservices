package in.bank.dto.request;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link in.bank.domain.Beneficiary} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class BeneficiaryDto implements Serializable {
    private int beneficiaryId;

    @NotBlank(message = "Name must not be empty")
    @NotNull
    private String name;

    private String userAccountNumber;
    @NotBlank(message = "Beneficiary Account Number must not be empty")
    @NotNull
    private String beneAccountNumber;
    @NotBlank(message = "IFSC Code must not be empty")
    @NotNull
    private String ifscCode;
    @NotBlank(message = "Bank Name must not be empty")
    @NotNull
    private String bankName;

    @NotBlank(message = "Branch Name must not be empty")
    @NotNull
    private String branchName;

    @NotBlank(message = "Account Type must not be empty")
    @NotNull
    private String accountType;

    @NotNull
    private int balance;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
