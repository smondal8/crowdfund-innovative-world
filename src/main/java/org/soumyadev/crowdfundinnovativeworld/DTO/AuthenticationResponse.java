package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthenticationResponse implements Serializable {

    private final String jwt;
    private final String username;
    private final String Userid;
    private final String Role;
}
