package hu.bme.mit.smartmobility.accountshielddevlab.Controller;


import hu.bme.mit.smartmobility.accountshielddevlab.Dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.UpdateProfileRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getProfile(Authentication authentication) {
       String email = authentication.getName();
       ProfileResponseDTO profile = userService.getProfile(email);
       return ResponseEntity.ok(profile);

   }

   @PutMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> updateProfile(Authentication authentication,
                                                            @Valid @RequestBody UpdateProfileRequestDTO requestDTO){
        String email = authentication.getName();
        ProfileResponseDTO updatedProfile = userService.updateProfile(email,requestDTO);

        return ResponseEntity.ok(updatedProfile);
    }
}
