package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class AuthenticationRequestDTO implements Serializable {
    private String username;
    private String password;
}
