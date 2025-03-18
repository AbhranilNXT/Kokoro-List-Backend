package tech.abhranilnxt.kokorolistbackend.service;

import java.util.Map;
import com.google.firebase.auth.FirebaseAuthException;

public interface AuthService {
    Map<String, String> authenticateUser(String firebaseToken) throws FirebaseAuthException;
}
