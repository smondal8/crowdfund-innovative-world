package org.soumyadev.crowdfundinnovativeworld.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Model.CustomCredDetails;
import org.soumyadev.crowdfundinnovativeworld.Repository.CredentialRepository;
import org.soumyadev.crowdfundinnovativeworld.Utils.PasswordEncrypter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserAuthenticationServiceTest {

    @Mock
    private CredentialRepository credentialRepository;

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Given
        String userId = "testUser";
        String rawPassword = "testPassword";

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(userId);
        usersEntity.setUserName("Test User");

        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsersEntity(usersEntity);
        credentialsEntity.setEncryptedPassword(PasswordEncrypter.hashPassword(rawPassword));
        credentialsEntity.setRole("ROLE_USER");

        List<CredentialsEntity> credentialsList = new ArrayList<>();
        credentialsList.add(credentialsEntity);

        when(credentialRepository.findAll()).thenReturn(credentialsList);

        // When
        CustomCredDetails result = userAuthenticationService.loadUserByUsername(userId, rawPassword);

        // Then
        assertEquals(userId, result.getUserId());
        assertEquals("ROLE_USER", result.getRole());
        assertEquals("Test User", result.getUserName());
    }

    @Test
    void testLoadUserByUsernameInvalidUser() {
        // Given
        String userId = "testUser";
        String rawPassword = "testPassword";

        when(credentialRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        CustomCredDetails result = userAuthenticationService.loadUserByUsername(userId, rawPassword);

        // Then
        assertEquals(null, result);
    }
}
