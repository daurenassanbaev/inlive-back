package pm.inlive.services;

import pm.inlive.dto.request.UpdateUserProfileRequest;
import pm.inlive.dto.response.UserResponse;
import pm.inlive.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    User getUserByKeycloakId(String keycloakId);

    @Transactional
    void syncUsersBetweenDBAndKeycloak();

    UserResponse getCurrentUser(String keycloakId);

    void updateUserProfile(String keycloakId, UpdateUserProfileRequest request);

    void updateUserPhoto(String keycloakId, MultipartFile photo);

    void deleteUserPhoto(String keycloakId);
}