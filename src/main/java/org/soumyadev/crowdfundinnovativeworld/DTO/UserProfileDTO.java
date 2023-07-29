package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class UserProfileDTO {
    private String userName;
    private String aboutMe;
    private String city;
    private String phone;
    private String userType;
}
