package blog.backend.security.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import blog.backend.security.services.UserAccessService;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserAccessService userAccessService;

    @Autowired
    public SecurityConfiguration(UserAccessService userAccessService) {
        this.userAccessService = userAccessService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                    .addFilterBefore(new SecurityFilter(userAccessService), BasicAuthenticationFilter.class)
                    .antMatcher("/auth")
                    .crsf()
                    .disable();
    }
    
}
