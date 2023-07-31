package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectDetailsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsFundingDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Service.FundsService;
import org.soumyadev.crowdfundinnovativeworld.Service.ProjectService;
import org.soumyadev.crowdfundinnovativeworld.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
class ProjectController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private FundsService fundsService;
    //getting all the type of projects normal and archived for a fund-raiser
    @GetMapping("/getProject/{userId}")
    public ResponseEntity<?> getProject(@PathVariable String userId) throws Exception {
        if(userService.checkUser(userId)){
            ProjectDetailsDTO projectDetailsDTO = projectService.fetchProjects(userId);
            return ResponseEntity.ok(projectDetailsDTO);
        }
        else{
            throw new UsernameNotFoundException("User not found !!");
        }
    }

    @GetMapping("/getAllProject")
    public ResponseEntity<?> getAllProject() throws Exception {
        List<ProjectsFundingDTO> projectsFundingDTOList = new ArrayList<>();
        List<ProjectsEntity> projectsEntityList = projectService.fetchAllProjects();
        if(projectsEntityList.size() == 0){
            return ResponseEntity.ok(Collections.EMPTY_LIST);
        }
        else{
            projectsEntityList.stream().forEach(projectsEntity -> {
                long acquired = projectsEntity.getFundingsEntityList().stream().collect(Collectors.summingLong(FundingsEntity::getAmount));
                if(acquired<projectsEntity.getProjectTarget()){
                    ProjectsFundingDTO projectsDTO = new ProjectsFundingDTO();
                    projectsDTO.setProjectId(projectsEntity.getProjectId());
                    projectsDTO.setProjectName(projectsEntity.getProjectName());
                    projectsDTO.setProjectDescription(projectsEntity.getProjectDescription());
                    projectsDTO.setProjectArea(projectsEntity.getProjectArea());
                    projectsDTO.setProjectTarget(projectsEntity.getProjectTarget());
                    projectsDTO.setProjectAcquired(acquired);
                    projectsFundingDTOList.add(projectsDTO);
                }
            });
        }
        return ResponseEntity.ok(projectsFundingDTOList);
    }

    @PostMapping("/createProject/{userId}")
    public ResponseEntity<?> createProject(@PathVariable String userId, @RequestBody ProjectsDTO projectsDTO) throws Exception {
        if(userService.checkUser(userId)){
            projectService.createProject(userId,projectsDTO);
            return ResponseEntity.ok(projectsDTO);
        }
        else{
            throw new UsernameNotFoundException("User not found !!");
        }
    }
}
