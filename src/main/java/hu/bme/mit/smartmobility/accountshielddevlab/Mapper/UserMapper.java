package hu.bme.mit.smartmobility.accountshielddevlab.Mapper;

import hu.bme.mit.smartmobility.accountshielddevlab.Dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.RegisterRequestDO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.RegisterResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "verified", expression = "java(false)")
    @Mapping(target = "failedAttemptCount", expression = "java(0)")
    @Mapping(target = "accountLocked", expression = "java(false)")
    @Mapping(target = "lockTime", ignore = true)
    User toEntity(RegisterRequestDO registerRequestDO);

    RegisterResponseDTO toResponseDTO(User user);

    ProfileResponseDTO toProfileResponse(User user);

    List<ProfileResponseDTO> toProfileResponseList(List<User> users);
}