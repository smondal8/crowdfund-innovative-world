package org.soumyadev.crowdfundinnovativeworld.Service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.soumyadev.crowdfundinnovativeworld.DTO.RegistrationRequestDTO;
import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Entity.UsersEntity;
import org.soumyadev.crowdfundinnovativeworld.Repository.CredentialRepository;
import org.soumyadev.crowdfundinnovativeworld.Repository.UsersRepository;
import org.soumyadev.crowdfundinnovativeworld.Utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public void registerUser(RegistrationRequestDTO registrationRequestDTO) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUserId(registrationRequestDTO.getUserId());
        usersEntity.setUserName(registrationRequestDTO.getUserName());
        usersEntity.setUserType(registrationRequestDTO.getUserType());
        usersEntity.setCity(registrationRequestDTO.getCity());
        usersEntity.setPhone(registrationRequestDTO.getPhoneNumber());
        usersEntity = usersRepository.save(usersEntity);
        CredentialsEntity credentialsEntity = new CredentialsEntity();
        credentialsEntity.setUsersEntity(usersEntity);
        credentialsEntity.setEncryptedPassword(PasswordEncrypter.encrypt(registrationRequestDTO.getPassword()));
        credentialRepository.save(credentialsEntity);

    }
}
