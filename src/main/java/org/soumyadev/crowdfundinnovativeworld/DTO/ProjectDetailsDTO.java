package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectDetailsDTO {
    private String userId;
    List<ProjectsFundingDTO> projects;
    List<ProjectsFundingDTO> archivedProjects;
}
