package hu.bme.mit.smartmobility.accountshielddevlab.Service;

import hu.bme.mit.smartmobility.accountshielddevlab.Dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.UpdateUserRoleRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.UpdateUserStatusRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.ENUM.ROLE;
import hu.bme.mit.smartmobility.accountshielddevlab.Mapper.UserMapper;
import hu.bme.mit.smartmobility.accountshielddevlab.Model.User;
import hu.bme.mit.smartmobility.accountshielddevlab.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private void validateAdminAccess(String adminEmail) {
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found with email: " + adminEmail));

        if (admin.getRole() != ROLE.ROLE_ADMIN) {
            throw new RuntimeException("Access Denied: Admin privileges required!");
        }
    }

    public List<ProfileResponseDTO> getAllUsers(String adminEmail) {
        validateAdminAccess(adminEmail);
        return userMapper.toProfileResponseList(userRepository.findAll());
    }

    public ProfileResponseDTO updateUserRole(String adminEmail, Long targetUserId, UpdateUserRoleRequestDTO requestDTO) {
        validateAdminAccess(adminEmail);

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + targetUserId));

        targetUser.setRole(requestDTO.getRole());
        User savedUser = userRepository.save(targetUser);

        return userMapper.toProfileResponse(savedUser);
    }

    public ProfileResponseDTO updateUserStatus(String adminEmail, Long targetUserId, UpdateUserStatusRequestDTO requestDTO) {
        validateAdminAccess(adminEmail);

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + targetUserId));

        targetUser.setAccountLocked(requestDTO.isAccountLocked());
        targetUser.setVerified(requestDTO.isVerified());
        User savedUser = userRepository.save(targetUser);

        return userMapper.toProfileResponse(savedUser);
    }

    public void deleteUser(String adminEmail, Long targetUserId) {
        validateAdminAccess(adminEmail);

        User targetUser = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + targetUserId));

        userRepository.delete(targetUser);
    }
}