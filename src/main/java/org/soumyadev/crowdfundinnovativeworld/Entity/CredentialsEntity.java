package org.soumyadev.crowdfundinnovativeworld.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="credentials")
public class CredentialsEntity implements Serializable {
    public static final long serialVersionID = 1L;
    @Id
    @Column(name = "cred_id",unique = true, nullable = false)
    @SequenceGenerator(name = "CRED_ID_GENERATOR", sequenceName = "credentials_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CRED_ID_GENERATOR")
    private Long credId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id",nullable = false)
    private UsersEntity usersEntity;
    @Column(name = "encrypted_password", nullable = false)
    private String encryptedPassword;
    @Column(name = "role", nullable = false)
    private String role;

}
