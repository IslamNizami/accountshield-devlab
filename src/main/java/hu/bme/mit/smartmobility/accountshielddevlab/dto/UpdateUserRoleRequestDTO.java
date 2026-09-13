package hu.bme.mit.smartmobility.accountshielddevlab.dto;

import hu.bme.mit.smartmobility.accountshielddevlab.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDTO {
    @NotNull(message = "Role cannot be null")
    private Role role;
}