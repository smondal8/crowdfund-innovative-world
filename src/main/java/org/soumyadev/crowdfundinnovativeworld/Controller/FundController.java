package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.soumyadev.crowdfundinnovativeworld.DTO.FundDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.ProjectNotFound;
import org.soumyadev.crowdfundinnovativeworld.Service.FundsService;
import org.soumyadev.crowdfundinnovativeworld.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.nonNull;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
class FundController {
    @Autowired
    ProjectService projectService;

    @Autowired
    FundsService fundsService;

    @PostMapping("/makeFunds/{projectId}")
    public ResponseEntity<?> makeFunds(@PathVariable Long projectId, @RequestBody FundDTO fundDTO) throws Exception {
        Optional<ProjectsEntity> projectsEntityOptional = projectService.getProjectEntity(projectId);
        if(projectsEntityOptional.isPresent()){
            ProjectsEntity projectEntity = projectsEntityOptional.get();
            Long acquired = projectService.getFundAcquired(projectEntity);
            if(fundDTO.getAmount()<=0 || fundDTO.getAmount()>projectEntity.getProjectTarget()-acquired){
                return ResponseEntity.ok("Could not be funded since amount is less or equal to zero or" +
                        " more than remaining amount of fulfillment");
            }
            else{
                fundsService.makeFund(projectEntity,fundDTO.getAmount());
                return ResponseEntity.ok("Fund invested!!");
            }
        }
        else{
            throw new ProjectNotFound("Project not found !!");
        }
    }

    @GetMapping("/pendingAmount/{projectId}")
    public ResponseEntity<?> pendingAmount(@PathVariable Long projectId) throws Exception {
        Optional<ProjectsEntity> projectsEntityOptional = projectService.getProjectEntity(projectId);
        if(projectsEntityOptional.isPresent()){
            ProjectsEntity projectEntity = projectsEntityOptional.get();
            Long acquired = projectService.getFundAcquired(projectEntity);
            FundDTO fundDTO = new FundDTO();
            fundDTO.setAmount(projectEntity.getProjectTarget()-acquired);
            return ResponseEntity.ok(fundDTO);
        }
        else{
            throw new ProjectNotFound("Project not found !!");
        }
    }
}
