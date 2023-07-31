package org.soumyadev.crowdfundinnovativeworld.Service;

import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectDetailsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersProjectMappingEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.ProjectsRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersProjectMappingRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    ProjectsRepository projectsRepository;
    @Autowired
    UsersProjectMappingRepository usersProjectMappingRepository;
    public ProjectDetailsDTO fetchProjects(String userId) {
        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        projectDetailsDTO.setUserId(userId);
        projectDetailsDTO.setProjects(new ArrayList<ProjectsDTO>());
        projectDetailsDTO.setArchivedProjects(new ArrayList<ProjectsDTO>());
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

    public void addProjectToList(ProjectDetailsDTO projectDetailsDTO, UsersProjectMappingEntity usersProjectMappingEntity) {
        ProjectsEntity projectsEntity = usersProjectMappingEntity.getProjectsEntity();
        long fundsAcquired = getFundAcquired(projectsEntity);
        if(fundsAcquired<projectsEntity.getProjectTarget()){
            ProjectsDTO projectsDTO = getProjectsDTO(projectsEntity);
            projectDetailsDTO.getProjects().add(projectsDTO);
        }else{
            ProjectsDTO projectsDTO = getProjectsDTO(projectsEntity);
            projectDetailsDTO.getArchivedProjects().add(projectsDTO);
        }
    }

    public Long getFundAcquired(ProjectsEntity projectsEntity) {
        return projectsEntity.getFundingsEntityList().stream()
                .collect(Collectors.summingLong(FundingsEntity::getAmount));
    }

    public ProjectsDTO getProjectsDTO(ProjectsEntity projectsEntity) {
        ProjectsDTO projectsDTO = new ProjectsDTO();
        projectsDTO.setProjectId(projectsEntity.getProjectId());
        projectsDTO.setProjectName(projectsEntity.getProjectName());
        projectsDTO.setProjectDescription(projectsEntity.getProjectDescription());
        projectsDTO.setProjectArea(projectsEntity.getProjectArea());
        projectsDTO.setProjectTarget(projectsEntity.getProjectTarget());
        return projectsDTO;
    }

    @Transactional
    public void createProject(String userId, ProjectsDTO projectsDTO) {
        Optional<UsersEntity> usersEntity = usersRepository.findByUserId(userId);
        ProjectsEntity projectsEntity = new ProjectsEntity();
        projectsEntity.setProjectName(projectsDTO.getProjectName());
        projectsEntity.setProjectDescription(projectsDTO.getProjectDescription());
        projectsEntity.setProjectArea(projectsDTO.getProjectArea());
        projectsEntity.setProjectTarget(projectsDTO.getProjectTarget());
        projectsEntity = projectsRepository.save(projectsEntity);
        UsersProjectMappingEntity usersProjectMappingEntity = new UsersProjectMappingEntity();
        usersProjectMappingEntity.setUsersEntity(usersEntity.get());
        usersProjectMappingEntity.setProjectsEntity(projectsEntity);
        usersProjectMappingRepository.save(usersProjectMappingEntity);

    }

    public Optional<ProjectsEntity> getProjectEntity(Long projectId) {
        return projectsRepository.findById(projectId);
    }

    public List<ProjectsEntity> fetchAllProjects() {
        return projectsRepository.findAll();
    }
}
