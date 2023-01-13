package blog.backend.security.auth;

import org.hibernate.validator.constraints.Length;

public class AuthRequest {

    @Length
    private String username;

    @Length
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

}
