package tech.abhranilnxt.kokorolistbackend.rest;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.abhranilnxt.kokorolistbackend.entity.Anime;
import tech.abhranilnxt.kokorolistbackend.entity.AnimeRequest;
import tech.abhranilnxt.kokorolistbackend.service.AnimeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/anime")
public class AnimeRestController {

    private final AnimeService animeService;

    @Autowired
    public AnimeRestController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public ResponseEntity<List<Anime>> getAllAnime() {
        return ResponseEntity.ok(animeService.getAllAnime());
    }

    @GetMapping("/{malId}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Long malId) {
        return ResponseEntity.ok(animeService.getAnimeById(malId));
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addAnime(
            @RequestBody AnimeRequest animeRequest,
            @RequestHeader("Authorization") String bearerToken) {
        try {
            if (!bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message", "Invalid token format"
                ));
            }

            String firebaseToken = bearerToken.substring(7);
            Map<String, String> response = animeService.addAnime(animeRequest, firebaseToken);
            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", "Unauthorized: Invalid Firebase token"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "message", "Internal Server Error: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getUserStats(
            @RequestHeader("Authorization") String bearerToken) {
        try {
            if (!bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.badRequest().body(Map.of(
                        "status", "error",
                        "message", "Invalid token format"
                ));
            }

            String firebaseToken = bearerToken.substring(7);
            Map<String, Object> response = animeService.getUserStats(firebaseToken);
            return ResponseEntity.ok(response);

        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "status", "error",
                    "message", "Unauthorized: Invalid Firebase token"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "status", "error",
                    "message", "Internal Server Error: " + e.getMessage()
            ));
        }
    }

}

