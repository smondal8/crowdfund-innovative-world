package org.soumyadev.crowdfundinnovativeworld.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="users")
public class UsersEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id",unique = true, nullable = false)
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String userName;
    @Column(name = "about_me")
    private String aboutMe;
    @Column(name = "city", nullable = false)
    private String city;
    @Column(name = "phone")
    private String phone;
    @Column(name = "user_type")
    private String userType;
    @OneToMany(mappedBy = "usersEntity", cascade= CascadeType.ALL)
    private List<UsersProjectMappingEntity> usersProjectMappingEntityList;

}

