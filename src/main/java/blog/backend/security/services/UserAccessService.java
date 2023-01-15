package blog.backend.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.util.Optional;

import blog.backend.security.services.dto.JWToken;
import blog.backend.security.services.dto.LoginRequest;
import blog.backend.security.services.dto.UserData;

/**
 * Service controlling user access
 */
public interface UserAccessService {
    
    /**
     * User login action, starts client session and issues JWT for client.
     * @param loginRequest username / password login request.
     * @return {@link UserData} contrining roles and JWT.
     */

    Optional<UserData> login(LoginRequest loginRequest);
            
    /**
    * Verify if provided token has been issued properly and is correctly signed.
    * @param jwToken base64 encoded JWT/JWS token)
    * @return {@link Jws<Claims>} or empty if provided token is invalid.
    */

    Optional<Jws<Claims>> validate(JWToken jwToken);

    /**
     * Invalidate client session before JWT expires.
     * @param jwToken provided token is used to get user's identity and invalidate client.
     * @return true if login was ok, false otherwise.
     */

     boolean logout(JWToken jwToken);
}
