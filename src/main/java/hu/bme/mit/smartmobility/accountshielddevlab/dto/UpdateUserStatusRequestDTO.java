package hu.bme.mit.smartmobility.accountshielddevlab.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserStatusRequestDTO {

    @NotNull(message = "accountLocked field is required")
    private Boolean accountLocked;

    @NotNull(message = "verified field is required")
    private Boolean verified;
}