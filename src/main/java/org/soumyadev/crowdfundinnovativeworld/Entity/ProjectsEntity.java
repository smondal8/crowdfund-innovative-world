package org.soumyadev.crowdfundinnovativeworld.Entity;

import lombok.Data;
import org.soumyadev.crowdfundinnovativeworld.Utils.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="projects")
public class ProjectsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "project_id",unique = true, nullable = false)
    @SequenceGenerator(name = "PROJECT_ID_GENERATOR", sequenceName = "project_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJECT_ID_GENERATOR")
    private Long projectId;
    @Column(name = "project_name", nullable = false)
    private String projectName;
    @Column(name = "project_description")
    private String projectDescription;
    @Column(name = "project_area", nullable = false)
    private String projectArea;
    @Column(name = "project_target")
    private long projectTarget;
    @OneToOne(mappedBy = "projectsEntity", cascade= CascadeType.ALL)
    private UsersProjectMappingEntity usersProjectMappingEntity;
    @OneToMany(mappedBy = "projectsEntity", cascade= CascadeType.ALL)
    private List<FundingsEntity> fundingsEntityList;

}


