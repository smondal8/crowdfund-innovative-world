package org.soumyadev.crowdfundinnovativeworld.Service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.soumyadev.crowdfundinnovativeworld.DTO.RegistrationRequestDTO;
import org.soumyadev.crowdfundinnovativeworld.DTO.UserProfileDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.CredentialRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;
import org.soumyadev.crowdfundinnovativeworld.Utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    CredentialRepository credentialRepository;
    @Autowired
    UsersRepository usersRepository;
    public boolean checkUser(String user){
        long count = credentialRepository.findAll().stream().filter(item -> item.getUsersEntity().getUserId().equals(user)).count();
        return count>0?true:false;
    }
    @Transactional
    public void registerUser(RegistrationRequestDTO registrationRequestDTO) throws NoSuchAlgorithmException {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(registrationRequestDTO.getUserId());
        usersEntity.setUserName(registrationRequestDTO.getUserName());
        usersEntity.setUserType(registrationRequestDTO.getUserType());
        usersEntity.setCity(registrationRequestDTO.getCity());
        usersEntity.setPhone(registrationRequestDTO.getPhoneNumber());
        usersEntity.setAboutMe(registrationRequestDTO.getAboutMe());
        usersEntity = usersRepository.save(usersEntity);
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsersEntity(usersEntity);
        credentialsEntity.setEncryptedPassword(PasswordEncrypter.hashPassword(registrationRequestDTO.getPassword()));
        credentialsEntity.setRole(registrationRequestDTO.getUserType());
        credentialRepository.save(credentialsEntity);

    }

    public UserProfileDTO getProfile(String id) {
        UserProfileDTO userProfileDTO = null;
        Optional<UsersEntity> userEntityOptional = usersRepository.findByUserId(id);
        if(userEntityOptional.isPresent()){
            UsersEntity usersEntity = userEntityOptional.get();
            userProfileDTO = new UserProfileDTO();
            userProfileDTO.setUserName(usersEntity.getUserName());
            userProfileDTO.setAboutMe(usersEntity.getAboutMe());
            userProfileDTO.setCity(usersEntity.getCity());
            userProfileDTO.setPhone(usersEntity.getPhone());
            userProfileDTO.setUserType(usersEntity.getUserType());
        }
        return userProfileDTO;
    }

    public void updateProfile(String userId,UserProfileDTO userProfileDTOInput) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(userId);
        usersEntity.setUserName(userProfileDTOInput.getUserName());
        usersEntity.setAboutMe(userProfileDTOInput.getAboutMe());
        usersEntity.setCity(userProfileDTOInput.getCity());
        usersEntity.setPhone(userProfileDTOInput.getPhone());
        usersEntity.setUserType(userProfileDTOInput.getUserType());
        usersRepository.save(usersEntity);
    }
}
