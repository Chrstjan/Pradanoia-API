package Pradanoia.payloads.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer id;

    @NotEmpty
    @Size(min = 4, message = "Username must be at least 4 characters")
    private String name;

    @Email(message = "Email address in not valid")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 15, message = "Password must be between 3 - 15 characters long")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Set<RoleDto> roles = new HashSet<>();
}
