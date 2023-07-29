package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthenticationResponseDTO implements Serializable {

    private final String jwt;
    private final String Userid;
    private final String Role;
}
