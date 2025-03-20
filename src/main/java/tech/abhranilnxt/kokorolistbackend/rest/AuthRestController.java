package tech.abhranilnxt.kokorolistbackend.rest;

import com.google.firebase.auth.FirebaseAuthException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.abhranilnxt.kokorolistbackend.service.AuthService;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    @Autowired
    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authenticateUser(@RequestHeader(value = "Authorization", required = false) String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message", "Invalid or missing Authorization header."
                ));
            }

            // Remove 'Bearer ' prefix from token
            String firebaseToken = token.substring(7);
            Map<String, String> response = authService.authenticateUser(firebaseToken);
            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", "Unauthorized: Invalid Firebase token."
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "message", "Internal Server Error: " + e.getMessage()
            ));
        }
    }

    @DeleteMapping("/delete/{targetUserId}")
    public ResponseEntity<Map<String, String>> deleteUserById(
            @PathVariable String targetUserId,
            @RequestHeader("Authorization") String bearerToken) {
        try {
            if (!bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message", "Invalid token format"
                ));
            }

            // Extract token without "Bearer "
            String firebaseToken = bearerToken.substring(7);

            // Call the service method with token and target user ID
            Map<String, String> response = authService.deleteUserById(firebaseToken, targetUserId);
            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", "Unauthorized: Invalid Firebase token"
            ));
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "message", "Internal Server Error: " + e.getMessage()
            ));
        }
    }
}

