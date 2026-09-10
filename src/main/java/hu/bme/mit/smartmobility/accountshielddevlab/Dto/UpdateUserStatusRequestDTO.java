package hu.bme.mit.smartmobility.accountshielddevlab.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserStatusRequestDTO {
    private boolean accountLocked;
    private boolean verified;
}