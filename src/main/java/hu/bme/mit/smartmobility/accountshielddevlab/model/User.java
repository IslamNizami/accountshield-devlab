package hu.bme.mit.smartmobility.accountshielddevlab.model;

import hu.bme.mit.smartmobility.accountshielddevlab.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    //For email verification
    @Column(name = "verified",nullable = false)
    private Boolean verified = false;

    //Account Lockout
    private int failedAttemptCount = 0;
    private LocalDateTime lockTime;

    @Column(name = "account_locked",nullable = false)
    private Boolean accountLocked = false;


}
