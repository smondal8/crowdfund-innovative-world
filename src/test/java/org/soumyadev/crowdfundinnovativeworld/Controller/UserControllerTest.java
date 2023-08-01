package org.soumyadev.crowdfundinnovativeworld.Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.DTO.AuthenticationRequestDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.GenericResponseDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.RegistrationRequestDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.UserProfileDTO;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.UserAlreadyExists;
import org.soumyadev.crowdfundinnovativeworld.ExceptionHandling.UserValidationException;
import org.soumyadev.crowdfundinnovativeworld.Model.CustomCredDetails;
import org.soumyadev.crowdfundinnovativeworld.Service.UserAuthenticationService;
import org.soumyadev.crowdfundinnovativeworld.Service.UserService;
import org.soumyadev.crowdfundinnovativeworld.Utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtTokenUtil;

    @Mock
    private UserAuthenticationService userDetailsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAuthenticationToken_ValidUser() throws Exception {
        // Mocking
        String username = "testUser";
        String password = "testPassword";
        String jwt = "testJwtToken";
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO(username, password);
        CustomCredDetails userDetails = new CustomCredDetails(username, "Funder", "ROLE_USER",null);
        userDetails.setJwt(jwt);
        when(userDetailsService.loadUserByUsername(username, password)).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn(jwt);

        // Test
        ResponseEntity<?> response = userController.createAuthenticationToken(authenticationRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CustomCredDetails returnedUserDetails = (CustomCredDetails) response.getBody();
        assertEquals(userDetails.getUserName(), returnedUserDetails.getUserName());
        assertEquals(userDetails.getUserId(), returnedUserDetails.getUserId());
        assertEquals(userDetails.getRole(), returnedUserDetails.getRole());
        assertEquals(jwt, returnedUserDetails.getJwt());
    }

    @Test
    void testCreateAuthenticationToken_InvalidUser() throws Exception {
        // Mocking
        String username = "testUser";
        String password = "testPassword";
        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO(username, password);
        when(userDetailsService.loadUserByUsername(username, password)).thenReturn(null);

        // Test & Verify
        UserValidationException exception = assertThrows(
                UserValidationException.class,
                () -> userController.createAuthenticationToken(authenticationRequestDTO)
        );
        assertEquals("User credential is not valid", exception.getMessage());
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        // Mocking
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        //when(userService.registerUser(registrationRequestDTO)).thenReturn(true);
        doNothing().when(userService).registerUser(registrationRequestDTO);
        // Test
        ResponseEntity<?> response = userController.registerUser(registrationRequestDTO);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GenericResponseDTO<String> responseBody = (GenericResponseDTO<String>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK.toString(), responseBody.getCode());
        assertEquals("User registered", responseBody.getMessage());
    }

    @Test
    void testCheckUserId_UserNotFound() throws Exception {
        // Mocking
        String userId = "testUserId";
        when(userService.checkUser(userId)).thenReturn(false);

        // Test
        ResponseEntity<?> response = userController.checkUserId(userId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        GenericResponseDTO<String> responseBody = (GenericResponseDTO<String>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(HttpStatus.OK.toString(), responseBody.getCode());
        assertEquals("No user found", responseBody.getMessage());
    }

    @Test
    void testCheckUserId_UserAlreadyExists() throws Exception {
        // Mocking
        String userId = "testUserId";
        when(userService.checkUser(userId)).thenReturn(true);

        // Test & Verify
        UserAlreadyExists exception = assertThrows(
                UserAlreadyExists.class,
                () -> userController.checkUserId(userId)
        );
        assertEquals("Username already exist please choose something else!", exception.getMessage());
    }

    @Test
    void testGetProfile_UserFound() throws Exception {
        // Mocking
        String userId = "testUserId";
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        when(userService.getProfile(userId)).thenReturn(userProfileDTO);

        // Test
        ResponseEntity<?> response = userController.getProfile(userId);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserProfileDTO responseBody = (UserProfileDTO) response.getBody();
        assertNotNull(responseBody);
        assertEquals(userProfileDTO, responseBody);
    }

    @Test
    void testGetProfile_UserNotFound() throws Exception {
        // Mocking
        String userId = "testUserId";
        when(userService.getProfile(userId)).thenReturn(null);

        // Test & Verify
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userController.getProfile(userId)
        );
        assertEquals("User not found !!", exception.getMessage());
    }

    @Test
    void testUpdateProfile_UserFound() throws Exception {
        // Mocking
        String userId = "testUserId";
        UserProfileDTO userProfileDTOInput = new UserProfileDTO();
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        when(userService.getProfile(userId)).thenReturn(userProfileDTO);
        //when(userService.updateProfile(userId, userProfileDTOInput)).thenReturn(true);
        doNothing().when(userService).updateProfile(userId,userProfileDTOInput);
        // Test
        ResponseEntity<?> response = userController.updateProfile(userId, userProfileDTOInput);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserProfileDTO responseBody = (UserProfileDTO) response.getBody();
        assertNotNull(responseBody);
        assertEquals(userProfileDTOInput, responseBody);
    }

    @Test
    void testUpdateProfile_UserNotFound() throws Exception {
        // Mocking
        String userId = "testUserId";
        UserProfileDTO userProfileDTOInput = new UserProfileDTO();
        when(userService.getProfile(userId)).thenReturn(null);

        // Test & Verify
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> userController.updateProfile(userId, userProfileDTOInput)
        );
        assertEquals("User not found !!", exception.getMessage());
    }
}
