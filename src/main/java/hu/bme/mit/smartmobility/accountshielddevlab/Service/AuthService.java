package hu.bme.mit.smartmobility.accountshielddevlab.Service;

import hu.bme.mit.smartmobility.accountshielddevlab.Dto.LoginRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.RegisterRequestDO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.RegisterResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.ENUM.ROLE;
import hu.bme.mit.smartmobility.accountshielddevlab.Mapper.UserMapper;
import hu.bme.mit.smartmobility.accountshielddevlab.Model.User;
import hu.bme.mit.smartmobility.accountshielddevlab.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_DURATION_MINUTES = 15;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public RegisterResponseDTO register(RegisterRequestDO registerRequestDO){
        //check if the email is registered already
        if(userRepository.existsByEmail(registerRequestDO.getEmail())){
            throw new RuntimeException("Email is already in use!");
        }

        User user = userMapper.toEntity(registerRequestDO);

        user.setPassword(passwordEncoder.encode(registerRequestDO.getPassword()));

        user.setRole(ROLE.ROLE_USER);

        userRepository.save(user);

        return userMapper.toResponseDTO(user);
    }

    public String login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password!"));

        if(user.getAccountLocked()){
            if(user.getLockTime() != null && user.getLockTime().plusMinutes(LOCK_DURATION_MINUTES).isBefore(LocalDateTime.now())){
                user.setAccountLocked(false);
                user.setLockTime(null);
                user.setFailedAttemptCount(0);
                userRepository.save(user);
            }else{
                throw new RuntimeException("Account is locked due to multiple failed login attempts!");
            }
        }

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            processFailedLoginAttempt(user);
            throw new RuntimeException("Invalid email or password!");
        }

        if(user.getFailedAttemptCount() > 0){
            user.setFailedAttemptCount(0);
            userRepository.save(user);
        }

        return jwtService.generateToken(user.getEmail());
    }
    private void processFailedLoginAttempt(User user) {
        int newAttempts = user.getFailedAttemptCount() + 1;
        user.setFailedAttemptCount(newAttempts);

        if (newAttempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountLocked(true);
            user.setLockTime(LocalDateTime.now());
        }

        userRepository.save(user);
    }
}
