package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

import javax.persistence.Column;

@Data
public class ProjectsDTO {
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private String projectArea;
    private long projectTarget;
}
