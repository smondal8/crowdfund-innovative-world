package org.soumyadev.crowdfundinnovativeworld.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomCredDetails {
    String userId;
    String role;
    String userName;
    String jwt;
}
