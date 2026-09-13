package hu.bme.mit.smartmobility.accountshielddevlab.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDTO {

    @NotBlank(message = "Name can't be blank")
    private String name;
}