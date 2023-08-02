package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.DTO.FundDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.InvalidFundException;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.ProjectNotFound;
import org.soumyadev.crowdfundinnovativeworld.Service.FundsService;
import org.soumyadev.crowdfundinnovativeworld.Service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FundControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private FundsService fundsService;

    @InjectMocks
    private FundController fundController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakeFunds_ProjectFound_SufficientFunds() throws Exception {
        // Mocking
        Long projectId = 1L;
        FundDTO fundDTO = new FundDTO();
        fundDTO.setAmount(500L);
        ProjectsEntity projectEntity = new ProjectsEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setProjectTarget(1000L);
        when(projectService.getProjectEntity(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectService.getFundAcquired(projectEntity)).thenReturn(200L);

        // Test
        ResponseEntity<?> response = fundController.makeFunds(projectId, fundDTO);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(fundDTO, response.getBody());
        verify(fundsService, times(1)).makeFund(projectEntity, 500L);
    }

    @Test
    void testMakeFunds_ProjectFound_InsufficientFunds() throws Exception {
        // Mocking
        Long projectId = 1L;
        FundDTO fundDTO = new FundDTO();
        fundDTO.setAmount(1000L);
        ProjectsEntity projectEntity = new ProjectsEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setProjectTarget(1000);
        when(projectService.getProjectEntity(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectService.getFundAcquired(projectEntity)).thenReturn(800L);

        // Verify
        InvalidFundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                InvalidFundException.class,
                () -> fundController.makeFunds(projectId, fundDTO)
        );
        //assertEquals("Could not be funded since amount is less or equal to zero or more than remaining amount of fulfillment", response.getBody());
        verify(fundsService, never()).makeFund(projectEntity, 1000L);
        assertEquals("Could not be funded since amount is less or equal to zero or more than remaining amount of fulfillment", exception.getMessage());
    }

    @Test
    void testMakeFunds_ProjectNotFound() throws Exception {
        // Mocking
        Long projectId = 1L;
        FundDTO fundDTO = new FundDTO();
        when(projectService.getProjectEntity(projectId)).thenReturn(Optional.empty());

        // Test & Verify
        ProjectNotFound exception = org.junit.jupiter.api.Assertions.assertThrows(
                ProjectNotFound.class,
                () -> fundController.makeFunds(projectId, fundDTO)
        );
        assertEquals("Project not found !!", exception.getMessage());
        verify(fundsService, never()).makeFund(any(), anyLong());
    }

    @Test
    void testPendingAmount_ProjectFound() throws Exception {
        // Mocking
        Long projectId = 1L;
        ProjectsEntity projectEntity = new ProjectsEntity();
        projectEntity.setProjectId(projectId);
        projectEntity.setProjectTarget(1000);
        when(projectService.getProjectEntity(projectId)).thenReturn(Optional.of(projectEntity));
        when(projectService.getFundAcquired(projectEntity)).thenReturn(500L);

        // Test
        ResponseEntity<?> response = fundController.pendingAmount(projectId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        FundDTO fundDTO = (FundDTO) response.getBody();
        assertEquals(500L, fundDTO.getAmount());
    }

    @Test
    void testPendingAmount_ProjectNotFound() throws Exception {
        // Mocking
        Long projectId = 1L;
        when(projectService.getProjectEntity(projectId)).thenReturn(Optional.empty());

        // Test & Verify
        ProjectNotFound exception = org.junit.jupiter.api.Assertions.assertThrows(
                ProjectNotFound.class,
                () -> fundController.pendingAmount(projectId)
        );
        assertEquals("Project not found !!", exception.getMessage());
    }
}
