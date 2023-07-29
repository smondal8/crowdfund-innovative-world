package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetailsDTO {
    private String userId;
    List<ProjectsDTO> projects;
    List<ProjectsDTO> archivedProjects;
}
