package hu.bme.mit.smartmobility.accountshielddevlab.service;

import hu.bme.mit.smartmobility.accountshielddevlab.dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.UpdateUserRoleRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.UpdateUserStatusRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.mapper.UserMapper;
import hu.bme.mit.smartmobility.accountshielddevlab.model.User;
import hu.bme.mit.smartmobility.accountshielddevlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<ProfileResponseDTO> getAllUsers() {
        return userMapper.toProfileResponseList(userRepository.findAll());
    }

    public ProfileResponseDTO updateUserRole(Long targetUserId, UpdateUserRoleRequestDTO requestDTO) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + targetUserId));

        targetUser.setRole(requestDTO.getRole());
        return userMapper.toProfileResponse(userRepository.save(targetUser));
    }

    public ProfileResponseDTO updateUserStatus(Long targetUserId, UpdateUserStatusRequestDTO requestDTO) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + targetUserId));

        targetUser.setAccountLocked(requestDTO.getAccountLocked());
        targetUser.setVerified(requestDTO.getVerified());
        return userMapper.toProfileResponse(userRepository.save(targetUser));
    }

    public void deleteUser(Long targetUserId) {
        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + targetUserId));

        userRepository.delete(targetUser);
    }
}