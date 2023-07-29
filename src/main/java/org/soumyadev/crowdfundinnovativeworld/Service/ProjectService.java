package org.soumyadev.crowdfundinnovativeworld.Service;

import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectDetailsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersProjectMappingEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.ProjectsRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ProjectsRepository projectsRepository;
    public ProjectDetailsDTO fetchProjects(String userId) {
        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        projectDetailsDTO.setUserId(userId);
        projectDetailsDTO.setProjects(Collections.EMPTY_LIST);
        projectDetailsDTO.setArchivedProjects(Collections.EMPTY_LIST);
        Optional<UsersEntity> userEntity = usersRepository.findByUserId(userId);
        if(userEntity.isPresent()){
            List<UsersProjectMappingEntity> listmapping = userEntity.get().getUsersProjectMappingEntityList();
            for(UsersProjectMappingEntity usersProjectMappingEntity:listmapping){
                addProjectToList(projectDetailsDTO,usersProjectMappingEntity);
            }
            return projectDetailsDTO;
        }
        else{
            return projectDetailsDTO;
        }
    }

    private void addProjectToList(ProjectDetailsDTO projectDetailsDTO, UsersProjectMappingEntity usersProjectMappingEntity) {
        ProjectsEntity projectsEntity = usersProjectMappingEntity.getProjectsEntity();
        long fundsAcquired = projectsEntity.getFundingsEntityList().stream()
                .collect(Collectors.summingLong(FundingsEntity::getAmount));
        if(fundsAcquired<projectsEntity.getProjectTarget()){
            ProjectsDTO projectsDTO = getProjectsDTO(projectsEntity);
            projectDetailsDTO.getProjects().add(projectsDTO);
        }else{
            ProjectsDTO projectsDTO = getProjectsDTO(projectsEntity);
            projectDetailsDTO.getArchivedProjects().add(projectsDTO);
        }
    }

    private ProjectsDTO getProjectsDTO(ProjectsEntity projectsEntity) {
        ProjectsDTO projectsDTO = new ProjectsDTO();
        projectsDTO.setProjectId(projectsEntity.getProjectId());
        projectsDTO.setProjectName(projectsEntity.getProjectName());
        projectsDTO.setProjectDescription(projectsEntity.getProjectDescription());
        projectsDTO.setProjectArea(projectsEntity.getProjectArea());
        projectsDTO.setProjectTarget(projectsEntity.getProjectTarget());
        return projectsDTO;
    }
}
