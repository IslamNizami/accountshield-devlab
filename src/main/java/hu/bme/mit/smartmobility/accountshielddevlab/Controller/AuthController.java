package hu.bme.mit.smartmobility.accountshielddevlab.Controller;


import hu.bme.mit.smartmobility.accountshielddevlab.Dto.LoginRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.RegisterRequestDO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.RegisterResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Service.AuthService;
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
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
        String token = authService.login(requestDTO);
        return ResponseEntity.ok(token);
    }
}
