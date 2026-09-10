package hu.bme.mit.smartmobility.accountshielddevlab.Dto;


import hu.bme.mit.smartmobility.accountshielddevlab.ENUM.ROLE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDTO {
    private Long id;
    private String name;
    private String email;
    private ROLE role;
    private boolean verified;
}
