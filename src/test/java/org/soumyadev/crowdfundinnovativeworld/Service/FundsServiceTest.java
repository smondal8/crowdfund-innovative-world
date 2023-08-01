package org.soumyadev.crowdfundinnovativeworld.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.Entity.FundingsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.ProjectsEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.FundsRepository;

import static org.mockito.Mockito.*;

class FundsServiceTest {

    @Mock
    private FundsRepository fundsRepository;

    @InjectMocks
    private FundsService fundsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakeFund() {
        // Given
        ProjectsEntity projectEntity = new ProjectsEntity();
        projectEntity.setProjectId(1L);
        projectEntity.setProjectName("Test Project");
        projectEntity.setProjectDescription("Test Description");
        projectEntity.setProjectTarget(1000L);
        projectEntity.setProjectArea("kolkata");

        Long amountToFund = 100L;

        // When
        fundsService.makeFund(projectEntity, amountToFund);

        // Then
        verify(fundsRepository, times(1)).save(any(FundingsEntity.class));
    }
}
