package tech.abhranilnxt.kokorolistbackend.rest;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.abhranilnxt.kokorolistbackend.service.WatchlistService;

import java.util.Map;

@RestController
@RequestMapping("/api/watchlist")
public class WatchlistRestController {

    private final WatchlistService watchlistService;

    @Autowired
    public WatchlistRestController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    // GET /api/watchlist/items
    @GetMapping("/items")
    public ResponseEntity<Map<String, Object>> getWatchlistItems(
            @RequestHeader("Authorization") String bearerToken) {
        try {
            // Validate and extract the token
            if (!bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message", "Invalid token format"
                ));
            }

            // Remove "Bearer " prefix to get the actual token
            String firebaseToken = bearerToken.substring(7);

            // Fetch watchlist items using the token
            Map<String, Object> response = watchlistService.getWatchlistItems(firebaseToken);
            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "status", "error",
                    "message", "Unauthorized: Invalid Firebase token"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Internal Server Error: " + e.getMessage()
            ));
        }
    }
}


