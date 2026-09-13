package hu.bme.mit.smartmobility.accountshielddevlab.controller;


import hu.bme.mit.smartmobility.accountshielddevlab.dto.LoginRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.LoginResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.RegisterRequestDO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.RegisterResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDO registerRequestDO){
        RegisterResponseDTO registerResponseDTO = authService.register(registerRequestDO);
        return new ResponseEntity<>(registerResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(new LoginResponseDTO(token, "Bearer"));
    }
}
