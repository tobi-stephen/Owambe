package org.lamlam.owambe.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author laplace zen
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistration {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Size(min=12)
    private String email;

    @NotNull
    @Size(min=10)
    private String password;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Role role;

    private String address;
}
