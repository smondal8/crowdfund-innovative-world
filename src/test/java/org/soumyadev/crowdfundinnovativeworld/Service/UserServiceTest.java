package org.soumyadev.crowdfundinnovativeworld.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.soumyadev.crowdfundinnovativeworld.DTO.RegistrationRequestDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.UserProfileDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.CredentialRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;
import org.soumyadev.crowdfundinnovativeworld.Utils.PasswordEncrypter;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private CredentialRepository credentialRepository;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() throws NoSuchAlgorithmException {
        // Given
        RegistrationRequestDTO registrationRequestDTO = new RegistrationRequestDTO();
        registrationRequestDTO.setUserId("testUser");
        registrationRequestDTO.setUserName("Test User");
        registrationRequestDTO.setUserType("Regular");
        registrationRequestDTO.setCity("Test City");
        registrationRequestDTO.setPhoneNumber("1234567890");
        registrationRequestDTO.setAboutMe("Test About Me");
        registrationRequestDTO.setPassword("testPassword");

        UsersEntity savedUser = new UsersEntity();
        savedUser.setUserId("testUser");
        savedUser.setUserName("Test User");
        savedUser.setUserType("Regular");
        savedUser.setCity("Test City");
        savedUser.setPhone("1234567890");
        savedUser.setAboutMe("Test About Me");

        when(usersRepository.save(any(UsersEntity.class))).thenReturn(savedUser);

        CredentialsEntity savedCredentials = new CredentialsEntity();
        savedCredentials.setUsersEntity(savedUser);
        savedCredentials.setEncryptedPassword(PasswordEncrypter.hashPassword("testPassword"));
        savedCredentials.setRole("Regular");

        // When
        userService.registerUser(registrationRequestDTO);

        // Then
        verify(usersRepository, times(1)).save(any(UsersEntity.class));
        verify(credentialRepository, times(1)).save(any(CredentialsEntity.class));
    }
    @Test
    void testGetProfile() {
        // Given
        String userId = "testUser";
        UsersEntity userEntity = new UsersEntity();
        userEntity.setUserId(userId);
        userEntity.setUserName("Test User");
        userEntity.setUserType("Regular");
        userEntity.setCity("Test City");
        userEntity.setPhone("1234567890");
        userEntity.setAboutMe("Test About Me");

        Optional<UsersEntity> userEntityOptional = Optional.of(userEntity);

        when(usersRepository.findByUserId(userId)).thenReturn(userEntityOptional);

        // When
        UserProfileDTO userProfileDTO = userService.getProfile(userId);

        // Then
        //assertEquals(userId, userProfileDTO.getUser());
        assertEquals("Test User", userProfileDTO.getUserName());
        assertEquals("Regular", userProfileDTO.getUserType());
        assertEquals("Test City", userProfileDTO.getCity());
        assertEquals("1234567890", userProfileDTO.getPhone());
        assertEquals("Test About Me", userProfileDTO.getAboutMe());

        verify(usersRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetProfileUserNotFound() {
        // Given
        String userId = "nonExistentUser";
        when(usersRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // When
        UserProfileDTO userProfileDTO = userService.getProfile(userId);

        // Then
        assertEquals(null, userProfileDTO);

        verify(usersRepository, times(1)).findByUserId(userId);
    }

    @Test
    void testUpdateProfile() {
        // Given
        String userId = "testUser";
        UserProfileDTO userProfileDTOInput = new UserProfileDTO();
        userProfileDTOInput.setUserName("Updated User");
        userProfileDTOInput.setUserType("Premium");
        userProfileDTOInput.setCity("Updated City");
        userProfileDTOInput.setPhone("9876543210");
        userProfileDTOInput.setAboutMe("Updated About Me");

        UsersEntity savedUser = new UsersEntity();
        savedUser.setUserId(userId);
        savedUser.setUserName("Test User");
        savedUser.setUserType("Regular");
        savedUser.setCity("Test City");
        savedUser.setPhone("1234567890");
        savedUser.setAboutMe("Test About Me");

        when(usersRepository.save(any(UsersEntity.class))).thenReturn(savedUser);

        // When
        userService.updateProfile(userId, userProfileDTOInput);

        // Then
        verify(usersRepository, times(1)).save(any(UsersEntity.class));
    }

    @Test
    void testUpdateProfileUserNotFound() {
        // Given
        String userId = "nonExistentUser";
        UserProfileDTO userProfileDTOInput = new UserProfileDTO();
        userProfileDTOInput.setUserName("Updated User");
        userProfileDTOInput.setUserType("Premium");
        userProfileDTOInput.setCity("Updated City");
        userProfileDTOInput.setPhone("9876543210");
        userProfileDTOInput.setAboutMe("Updated About Me");

        when(usersRepository.save(any(UsersEntity.class))).thenReturn(null);

        // When
        userService.updateProfile(userId, userProfileDTOInput);

        // Then
        verify(usersRepository, times(1)).save(any(UsersEntity.class));
    }
}
