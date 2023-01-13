package blog.backend.security.jwt;

import java.io.IOException;

import jakarta.servlet.*;

public class JwtTokenFilterWrapper extends GenericFilter {
    private final JwtTokenFilter jwtTokenFilter;

    public JwtTokenFilterWrapper(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

  
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        jwtTokenFilter.doFilter(request, response, chain);
        
    }
    
}