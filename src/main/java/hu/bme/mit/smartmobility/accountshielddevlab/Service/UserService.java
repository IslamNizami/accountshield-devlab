package hu.bme.mit.smartmobility.accountshielddevlab.Service;

import hu.bme.mit.smartmobility.accountshielddevlab.Dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.UpdateProfileRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Mapper.UserMapper;
import hu.bme.mit.smartmobility.accountshielddevlab.Model.User;
import hu.bme.mit.smartmobility.accountshielddevlab.Repository.UserRepository;
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