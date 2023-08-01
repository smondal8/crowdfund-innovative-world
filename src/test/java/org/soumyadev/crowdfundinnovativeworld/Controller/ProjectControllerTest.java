package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectDetailsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsFundingDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Service.FundsService;
import org.soumyadev.crowdfundinnovativeworld.Service.ProjectService;
import org.soumyadev.crowdfundinnovativeworld.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Mock
    private FundsService fundsService;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProject_ExistingUser() throws Exception {
        // Mocking
        String userId = "testUserId";
        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        when(userService.checkUser(userId)).thenReturn(true);
        when(projectService.fetchProjects(userId)).thenReturn(projectDetailsDTO);

        // Test
        ResponseEntity<?> response = projectController.getProject(userId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectDetailsDTO, response.getBody());
        verify(userService, times(1)).checkUser(userId);
        verify(projectService, times(1)).fetchProjects(userId);
    }

    @Test
    void testGetProject_NonExistingUser() throws Exception {
        // Mocking
        String userId = "testUserId";
        when(userService.checkUser(userId)).thenReturn(false);

        // Test & Verify
        UsernameNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> projectController.getProject(userId)
        );
        assertEquals("User not found !!", exception.getMessage());
        verify(userService, times(1)).checkUser(userId);
        verify(projectService, never()).fetchProjects(userId);
    }

    @Test
    void testGetAllProject_NoProjectsAvailable() throws Exception {
        // Mocking
        List<ProjectsEntity> projectsEntityList = new ArrayList<>();
        when(projectService.fetchAllProjects()).thenReturn(projectsEntityList);

        // Test
        ResponseEntity<?> response = projectController.getAllProject();

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, ((List<?>) response.getBody()).size());
        verify(projectService, times(1)).fetchAllProjects();
    }

    @Test
    void testGetAllProject_ProjectsAvailable() throws Exception {
        // Mocking
        List<ProjectsEntity> projectsEntityList = new ArrayList<>();
        ProjectsEntity projectEntity = new ProjectsEntity();
        projectEntity.setProjectId(1L);
        projectEntity.setProjectName("Project1");
        projectEntity.setProjectDescription("Description1");
        projectEntity.setProjectArea("Area1");
        projectEntity.setProjectTarget(1000);
        FundingsEntity funding = new FundingsEntity();
        funding.setAmount(500);
        projectEntity.setFundingsEntityList(List.of(funding));
        projectsEntityList.add(projectEntity);
        when(projectService.fetchAllProjects()).thenReturn(projectsEntityList);

        // Test
        ResponseEntity<?> response = projectController.getAllProject();

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ProjectsFundingDTO> projectsDTOList = (List<ProjectsFundingDTO>) response.getBody();
        assertEquals(1, projectsDTOList.size());
        ProjectsFundingDTO projectsDTO = projectsDTOList.get(0);
        assertEquals(1L, projectsDTO.getProjectId());
        assertEquals("Project1", projectsDTO.getProjectName());
        assertEquals("Description1", projectsDTO.getProjectDescription());
        assertEquals("Area1", projectsDTO.getProjectArea());
        assertEquals(1000, projectsDTO.getProjectTarget());
        assertEquals(500, projectsDTO.getProjectAcquired());
        verify(projectService, times(1)).fetchAllProjects();
    }

    @Test
    void testCreateProject_ExistingUser() throws Exception {
        // Mocking
        String userId = "testUserId";
        ProjectsFundingDTO projectsDTO = new ProjectsFundingDTO();
        when(userService.checkUser(userId)).thenReturn(true);

        // Test
        ResponseEntity<?> response = projectController.createProject(userId, projectsDTO);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectsDTO, response.getBody());
        verify(userService, times(1)).checkUser(userId);
        verify(projectService, times(1)).createProject(userId, projectsDTO);
    }

    @Test
    void testCreateProject_NonExistingUser() throws Exception {
        // Mocking
        String userId = "testUserId";
        ProjectsFundingDTO projectsDTO = new ProjectsFundingDTO();
        when(userService.checkUser(userId)).thenReturn(false);

        // Test & Verify
        UsernameNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> projectController.createProject(userId, projectsDTO)
        );
        assertEquals("User not found !!", exception.getMessage());
        verify(userService, times(1)).checkUser(userId);
        verify(projectService, never()).createProject(userId, projectsDTO);
    }
}
