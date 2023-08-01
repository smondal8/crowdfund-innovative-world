package org.soumyadev.crowdfundinnovativeworld.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectDetailsDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.ProjectsFundingDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersProjectMappingEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.ProjectsRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersProjectMappingRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ProjectsRepository projectsRepository;

    @Mock
    private UsersProjectMappingRepository usersProjectMappingRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFetchProjects() {
        // Given
        String userId = "testUser";
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(userId);
        usersEntity.setUserName("Test User");

        List<UsersProjectMappingEntity> usersProjectMappingEntityList = new ArrayList<>();
        ProjectsEntity project1 = new ProjectsEntity();
        project1.setProjectId(1L);
        project1.setProjectName("Project 1");
        project1.setProjectDescription("Description 1");
        project1.setProjectArea("Area 1");
        project1.setProjectTarget(1000L);
        project1.setProjectArea("Kolkata");
        UsersProjectMappingEntity mapping1 = new UsersProjectMappingEntity();
        mapping1.setProjectsEntity(project1);

        ProjectsEntity project2 = new ProjectsEntity();
        project2.setProjectId(2L);
        project2.setProjectName("Project 2");
        project2.setProjectDescription("Description 2");
        project2.setProjectArea("Area 2");
        project2.setProjectTarget(2000L);
        project2.setProjectArea("Kolkata");
        UsersProjectMappingEntity mapping2 = new UsersProjectMappingEntity();
        mapping2.setProjectsEntity(project2);

        usersProjectMappingEntityList.add(mapping1);
        usersProjectMappingEntityList.add(mapping2);
        usersEntity.setUsersProjectMappingEntityList(usersProjectMappingEntityList);

        when(usersRepository.findByUserId(userId)).thenReturn(Optional.of(usersEntity));

        // When
        ProjectDetailsDTO projectDetailsDTO = projectService.fetchProjects(userId);

        // Then
        assertEquals(userId, projectDetailsDTO.getUserId());
        assertEquals(2, projectDetailsDTO.getProjects().size());
        assertEquals("Project 1", projectDetailsDTO.getProjects().get(0).getProjectName());
        assertEquals("Project 2", projectDetailsDTO.getProjects().get(1).getProjectName());
    }

    @Test
    void testAddProjectToList() {
        // Given
        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        ProjectsEntity projectsEntity = new ProjectsEntity();
        projectsEntity.setProjectId(1L);
        projectsEntity.setProjectName("Test Project");
        projectsEntity.setProjectDescription("Test Description");
        projectsEntity.setProjectArea("Test Area");
        projectsEntity.setProjectTarget(1000L);
        projectsEntity.setProjectArea("Kolkata");

        UsersProjectMappingEntity usersProjectMappingEntity = new UsersProjectMappingEntity();
        usersProjectMappingEntity.setProjectsEntity(projectsEntity);

        // When
        projectService.addProjectToList(projectDetailsDTO, usersProjectMappingEntity);

        // Then
        assertEquals(1, projectDetailsDTO.getProjects().size());
        assertEquals("Test Project", projectDetailsDTO.getProjects().get(0).getProjectName());
    }

    @Test
    void testGetFundAcquired() {
        // Given
        ProjectsEntity projectsEntity = new ProjectsEntity();
        projectsEntity.setProjectId(1L);

        List<FundingsEntity> fundingsEntityList = new ArrayList<>();
        FundingsEntity funding1 = new FundingsEntity();
        funding1.setAmount(100L);
        fundingsEntityList.add(funding1);
        FundingsEntity funding2 = new FundingsEntity();
        funding2.setAmount(200L);
        fundingsEntityList.add(funding2);
        projectsEntity.setFundingsEntityList(fundingsEntityList);

        // When
        long fundsAcquired = projectService.getFundAcquired(projectsEntity);

        // Then
        assertEquals(300L, fundsAcquired);
    }

    @Test
    void testGetProjectsDTO() {
        // Given
        ProjectsEntity projectsEntity = new ProjectsEntity();
        projectsEntity.setProjectName("Test Project");
        projectsEntity.setProjectDescription("Test Description");
        projectsEntity.setProjectArea("Test Area");
        projectsEntity.setProjectTarget(1000L);

        long fundsAcquired = 500L;

        // When
        ProjectsFundingDTO projectsDTO = projectService.getProjectsDTO(projectsEntity, fundsAcquired);

        // Then
        assertEquals("Test Project", projectsDTO.getProjectName());
        assertEquals("Test Description", projectsDTO.getProjectDescription());
        assertEquals("Test Area", projectsDTO.getProjectArea());
        assertEquals(1000L, projectsDTO.getProjectTarget());
        assertEquals(500L, projectsDTO.getProjectAcquired());
    }

    @Test
    void testCreateProject() {
        // Given
        String userId = "testUser";
        ProjectsFundingDTO projectsFundingDTO = new ProjectsFundingDTO();
        projectsFundingDTO.setProjectName("Test Project");
        projectsFundingDTO.setProjectDescription("Test Description");
        projectsFundingDTO.setProjectArea("Test Area");
        projectsFundingDTO.setProjectTarget(1000L);

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(userId);
        usersEntity.setUserName("Test User");

        when(usersRepository.findByUserId(userId)).thenReturn(Optional.of(usersEntity));

        ProjectsEntity savedProject = new ProjectsEntity();
        savedProject.setProjectId(1L);
        savedProject.setProjectName("Test Project");
        savedProject.setProjectDescription("Test Description");
        savedProject.setProjectArea("Test Area");
        savedProject.setProjectTarget(1000L);

        when(projectsRepository.save(any(ProjectsEntity.class))).thenReturn(savedProject);

        // When
        projectService.createProject(userId, projectsFundingDTO);

        // Then
        verify(usersRepository, times(1)).findByUserId(userId);
        verify(projectsRepository, times(1)).save(any(ProjectsEntity.class));
        verify(usersProjectMappingRepository, times(1)).save(any(UsersProjectMappingEntity.class));
    }

    @Test
    void testGetProjectEntity() {
        // Given
        Long projectId = 1L;
        ProjectsEntity projectEntity = new ProjectsEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setProjectName("Test Project");
        projectEntity.setProjectDescription("Test Description");
        projectEntity.setProjectArea("Test Area");
        projectEntity.setProjectTarget(1000L);

        when(projectsRepository.findById(projectId)).thenReturn(Optional.of(projectEntity));

        // When
        Optional<ProjectsEntity> result = projectService.getProjectEntity(projectId);

        // Then
        assertEquals(Optional.of(projectEntity), result);
    }

    @Test
    void testFetchAllProjects() {
        // Given
        List<ProjectsEntity> projectsList = new ArrayList<>();
        ProjectsEntity project1 = new ProjectsEntity();
        project1.setProjectId(1L);
        project1.setProjectName("Project 1");
        project1.setProjectDescription("Description 1");
        project1.setProjectArea("Area 1");
        project1.setProjectTarget(1000L);
        project1.setProjectArea("kolkata");
        projectsList.add(project1);

        ProjectsEntity project2 = new ProjectsEntity();
        project2.setProjectId(2L);
        project2.setProjectName("Project 2");
        project2.setProjectDescription("Description 2");
        project2.setProjectArea("Area 2");
        project2.setProjectTarget(2000L);
        project2.setProjectArea("kolkata");
        projectsList.add(project2);

        when(projectsRepository.findAll()).thenReturn(projectsList);

        // When
        List<ProjectsEntity> result = projectService.fetchAllProjects();

        // Then
        assertEquals(projectsList.size(), result.size());
        assertEquals("Project 1", result.get(0).getProjectName());
        assertEquals("Project 2", result.get(1).getProjectName());
    }

}
