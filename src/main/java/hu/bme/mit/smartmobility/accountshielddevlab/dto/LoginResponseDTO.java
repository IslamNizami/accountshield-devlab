package hu.bme.mit.smartmobility.accountshielddevlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType;
}