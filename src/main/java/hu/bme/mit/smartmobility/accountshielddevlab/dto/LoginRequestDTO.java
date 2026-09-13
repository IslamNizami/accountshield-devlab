package hu.bme.mit.smartmobility.accountshielddevlab.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password can't be blank")
    private String password;
}
