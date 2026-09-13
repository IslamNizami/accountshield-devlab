package hu.bme.mit.smartmobility.accountshielddevlab.dto;


import hu.bme.mit.smartmobility.accountshielddevlab.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private boolean verified;
}
