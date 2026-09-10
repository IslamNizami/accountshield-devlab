package hu.bme.mit.smartmobility.accountshielddevlab.Dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDO {

    @NotBlank(message = "Name can't be blank")
    private String name;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email should be valid.")
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Size(min = 6,message = "Password must be at least 6 characters long")
    private String password;
}
