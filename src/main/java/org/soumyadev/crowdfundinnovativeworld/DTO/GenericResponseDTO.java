package org.soumyadev.crowdfundinnovativeworld.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class GenericResponseDTO<T> {
    private String code;
    private String message;
    private T response;
}

