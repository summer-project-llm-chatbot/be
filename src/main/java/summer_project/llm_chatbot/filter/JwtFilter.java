package summer_project.llm_chatbot.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.service.JwtService;

import java.io.IOException;
import java.net.Authenticator;

public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = jwtService.resolveToken(request);

        if (token != null && !token.isBlank()){
            // ex
        }
        try {
            jwtService.validateJwtToken(token);
        } catch (ApplicationException e) {

        }

        Authentication authentication = jwtService.getAuthentication(token);
    }
}
