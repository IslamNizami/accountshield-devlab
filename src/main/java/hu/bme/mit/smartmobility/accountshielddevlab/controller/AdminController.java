package hu.bme.mit.smartmobility.accountshielddevlab.controller;

import hu.bme.mit.smartmobility.accountshielddevlab.dto.ProfileResponseDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.UpdateUserRoleRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.dto.UpdateUserStatusRequestDTO;
import hu.bme.mit.smartmobility.accountshielddevlab.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<ProfileResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ProfileResponseDTO> updateUserRole(@PathVariable Long id,
                                                             @Valid @RequestBody UpdateUserRoleRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.updateUserRole(id, requestDTO));
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<ProfileResponseDTO> updateUserStatus(@PathVariable Long id,
                                                               @Valid @RequestBody UpdateUserStatusRequestDTO requestDTO) {
        return ResponseEntity.ok(adminService.updateUserStatus(id, requestDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}