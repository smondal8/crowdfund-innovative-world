package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String userId;
    private String userName;
    private String password;
    private String aboutMe;
    private String city;
    private String phoneNumber;
    private String userType;
}
