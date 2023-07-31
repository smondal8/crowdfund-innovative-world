package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

@Data
public class ProjectsFundingDTO {
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private String projectArea;
    private long projectTarget;
    private long projectAcquired;
}
