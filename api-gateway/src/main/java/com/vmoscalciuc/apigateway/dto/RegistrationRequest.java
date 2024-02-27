package com.vmoscalciuc.apigateway.dto;

import com.vmoscalciuc.apigateway.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private Boolean enabled;
}
