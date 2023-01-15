package blog.backend.security.jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import blog.backend.security.services.JWTUtils;
import blog.backend.security.services.UserAccessService;
import blog.backend.security.services.dto.JWToken;
import blog.backend.security.services.dto.RoleId;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors; 

public class SecurityFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);

    private final UserAccessService userAccessService;

    public SecurityFilter(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        String authorization = httpServletRequest.getHeader("Authorization");
        if (authorization != null) {
            JWToken jwToken = JWToken.from(JWTUtils.extractJwtToken(authorization));
            Optional<Jws<Claims>> verifiedClaims = userAccessService.validate(jwToken);
            if (verifiedClaims.isPresent()) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                String userIdFromJWT = verifiedClaims.get().getBody().getSubject();
                List<String> rolesFromJWT = (List<String>) verifiedClaims.get().getBody().get("roles");
                Set<RoleId> setOfRoles = rolesFromJWT.stream().map(RoleId::new).collect(Collectors.toSet());
                securityContext.setAuthentication(new AuthenticationImpl(userIdFromJWT, setOfRoles));
                chain.doFilter(request, response);
                return;
            } else {
                LOG.error("not authorized !");
            }
        } else {
            LOG.error("not authorized: header \"Authorization\" is missing !");
        }
        ((HttpServletResponse) httpServletRequest).setStatus(HttpStatus.FORBIDDEN.value());
    }
    
}
