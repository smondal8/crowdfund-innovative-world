package org.soumyadev.crowdfundinnovativeworld.Service;

import org.soumyadev.crowdfundinnovativeworld.Entity.CredentialsEntity;
import org.soumyadev.crowdfundinnovativeworld.Model.CustomCredDetails;
import org.soumyadev.crowdfundinnovativeworld.Repository.CredentialRepository;
import org.soumyadev.crowdfundinnovativeworld.Utils.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserAuthenticationService implements UserDetailsService {
    @Autowired
    CredentialRepository credentialRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return null;
    }
    public CustomCredDetails loadUserByUsername(String userId, String rawPassword) throws UsernameNotFoundException {
        List<CredentialsEntity> creds = credentialRepository.findAll().stream().filter(cred -> cred.getUsersEntity().getUserId().equals(userId)).collect(Collectors.toList());
        if(Objects.nonNull(creds) && creds.size()>0 && PasswordEncrypter.checkPassword(rawPassword,creds.get(0).getEncryptedPassword())){
            return new CustomCredDetails(creds.get(0).getUsersEntity().getUserId()
                    ,creds.get(0).getRole(),creds.get(0).getUsersEntity().getUserName());
        }
        return null;
    }
}
