package in.bank.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import in.bank.enumop.AccountType;
import in.bank.enumop.Gender;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String userFirstName;

    private String userLastName;

    private LocalDate userDOB;

    private String userPhoneNo;

    private String userAltPhoneNo;

    private String userAddress;

    @Column(unique = true)
    private String userAadharNo;

    @Column(unique = true)
    private String userPAN;

    @Enumerated(EnumType.STRING)
    private Gender userGender;

    private String userNationality;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private AccountType userAccType;

    private String userBranchName;

    private String userIFSC;

    private String userAccountNumber;

    private LocalDate createAt;

    private int balance;

}
