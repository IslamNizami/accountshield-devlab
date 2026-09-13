package hu.bme.mit.smartmobility.accountshielddevlab.Controller;

import hu.bme.mit.smartmobility.accountshielddevlab.Dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.UpdateUserRoleRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Dto.UpdateUserStatusRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.Service.AdminService;
import hu.bme.mit.smartmobility.accountshielddevlab.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<ProfileResponseDTO>> getAllUsers(Authentication authentication){
        String adminEmail = authentication.getName();
        List<ProfileResponseDTO> users = adminService.getAllUsers(adminEmail);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ProfileResponseDTO> updateUserRole(Authentication authentication,
                                                             @PathVariable Long id,
                                                             @Valid @RequestBody UpdateUserRoleRequestDTO requestDTO) {
        String adminEmail = authentication.getName();
        ProfileResponseDTO updatedUser = adminService.updateUserRole(adminEmail, id, requestDTO);
        return ResponseEntity.ok(updatedUser);
    }
    @PatchMapping("/users/{id}/status")
    public ResponseEntity<ProfileResponseDTO> updateUserStatus(Authentication authentication,
                                                               @PathVariable Long id,
                                                               @RequestBody UpdateUserStatusRequestDTO requestDTO) {
        String adminEmail = authentication.getName();
        ProfileResponseDTO updatedUser = adminService.updateUserStatus(adminEmail, id, requestDTO);
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(Authentication authentication, @PathVariable Long id) {
        String adminEmail = authentication.getName();
        adminService.deleteUser(adminEmail, id);
        return ResponseEntity.ok("User successfully deleted by admin.");
    }
}
