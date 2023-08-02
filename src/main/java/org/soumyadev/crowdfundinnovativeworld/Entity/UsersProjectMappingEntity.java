package org.soumyadev.crowdfundinnovativeworld.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="users_projects_mapping")
public class UsersProjectMappingEntity implements Serializable {
    public static final long serialVersionID = 1L;
    @Id
    @Column(name = "mapping_id",unique = true, nullable = false)
    @SequenceGenerator(name = "MAPPING_ID_GENERATOR", sequenceName = "users_projects_mapping_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MAPPING_ID_GENERATOR")
    private Long mappingId;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id",nullable = false)
    private UsersEntity usersEntity;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id" ,referencedColumnName = "project_id",nullable = false)
    private ProjectsEntity projectsEntity;

}
