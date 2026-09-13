package hu.bme.mit.smartmobility.accountshielddevlab.mapper;

import hu.bme.mit.smartmobility.accountshielddevlab.dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.RegisterRequestDO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.RegisterResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.model.User;
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