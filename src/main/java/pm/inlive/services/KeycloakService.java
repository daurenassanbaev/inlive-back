package pm.inlive.services;

import pm.inlive.dto.request.UpdatePasswordRequest;
import pm.inlive.dto.response.AuthResponse;
import pm.inlive.security.keycloak.KeycloakBaseUser;
import pm.inlive.security.keycloak.KeycloakRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakService {
    UserRepresentation createUserByRole(KeycloakBaseUser keycloakBaseUser, KeycloakRole keycloakRole);

    AuthResponse getAuthResponse(String email, String password, HttpServletResponse response);

    AuthResponse registerUser(KeycloakBaseUser registrationRequest, KeycloakRole keycloakRole, HttpServletResponse response);

    AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response);

    void logout(HttpServletRequest request, HttpServletResponse response);

    void deleteUserById(String userId);

    List<UserRepresentation> getAllUsers();

    List<UserRepresentation> getAllUsersByRole(KeycloakRole keycloakRole);

    UserResource getUserById(String id);

    UserResource updateUser(KeycloakBaseUser keycloakBaseUser);

    void updatePassword(String keycloakId, UpdatePasswordRequest updatePassword);
}