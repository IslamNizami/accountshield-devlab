package hu.bme.mit.smartmobility.accountshielddevlab.service;

import hu.bme.mit.smartmobility.accountshielddevlab.dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.UpdateProfileRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.mapper.UserMapper;
import hu.bme.mit.smartmobility.accountshielddevlab.model.User;
import hu.bme.mit.smartmobility.accountshielddevlab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ProfileResponseDTO getProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        return userMapper.toProfileResponse(user);
    }

    public ProfileResponseDTO updateProfile(String email, UpdateProfileRequestDTO requestDTO) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));

        user.setName(requestDTO.getName());
        User savedUser = userRepository.save(user);

        return userMapper.toProfileResponse(savedUser);
    }
}