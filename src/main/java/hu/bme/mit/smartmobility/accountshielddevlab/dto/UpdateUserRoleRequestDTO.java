package hu.bme.mit.smartmobility.accountshielddevlab.Dto;

import hu.bme.mit.smartmobility.accountshielddevlab.ENUM.ROLE;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequestDTO {
    @NotNull(message = "Role cannot be null")
    private ROLE role;
}