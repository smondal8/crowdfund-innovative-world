package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.soumyadev.crowdfundinnovativeworld.DTO.GenericResponseDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectDetailsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsDTO;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.UserAlreadyExists;
import org.soumyadev.crowdfundinnovativeworld.Repository.ProjectsRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;
import org.soumyadev.crowdfundinnovativeworld.Service.ProjectService;
import org.soumyadev.crowdfundinnovativeworld.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
class ProjectController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
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

    @PostMapping("/createProject/{userId}")
    public ResponseEntity<?> createProject(@PathVariable String userId, @RequestBody ProjectsDTO projectsDTO) throws Exception {
        if(userService.checkUser(userId)){
            projectService.createProject(userId,projectsDTO);
            return ResponseEntity.ok("Project is created successfully!!");
        }
        else{
            throw new UsernameNotFoundException("User not found !!");
        }
    }
}
