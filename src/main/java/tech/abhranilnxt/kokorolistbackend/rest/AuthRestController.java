package tech.abhranilnxt.kokorolistbackend.rest;

import com.google.firebase.auth.FirebaseAuthException;
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
}

