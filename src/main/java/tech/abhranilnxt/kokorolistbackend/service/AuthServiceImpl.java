package tech.abhranilnxt.kokorolistbackend.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.abhranilnxt.kokorolistbackend.dao.UserDAO;
import tech.abhranilnxt.kokorolistbackend.entity.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDAO userDAO;
    private final FirebaseAuth firebaseAuth;

    @Autowired
    public AuthServiceImpl(UserDAO userDAO, FirebaseAuth firebaseAuth) {
        this.userDAO = userDAO;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    @Transactional
    public Map<String, String> authenticateUser(String firebaseToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = firebaseAuth.verifyIdToken(firebaseToken);
        String userId = decodedToken.getUid();
        String email = decodedToken.getEmail();

        // Extract display name from email
        String displayName = (email != null) ? email.split("@")[0] : "User_" + userId.substring(0, 6);

        Optional<User> userOptional = userDAO.getUserById(userId);
        boolean isNewUser = false;

        User user;
        if (userOptional.isEmpty()) {
            user = new User(userId, displayName, null, null);
            isNewUser = true;
        } else {
            user = userOptional.get();
        }

        userDAO.saveOrUpdateUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", isNewUser ? "User created successfully" : "User logged in successfully");
        return response;
    }
}