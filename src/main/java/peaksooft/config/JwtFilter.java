package peaksooft.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import peaksooft.exceptions.NotFoundException;
import peaksooft.models.User;
import peaksooft.repository.UserRepository;
import peaksooft.services.impls.JwtService;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String tokenHeader = request.getHeader("Authorization");
    if (tokenHeader != null && tokenHeader.startsWith("Bearer ")){
        String token = tokenHeader.substring(7);
        if (StringUtils.hasText(token)){
            try {
                String username = null;
                try {
                    username = jwtService.validateToken(token);
                }catch (MalformedJwtException e){
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
                String finalUserName = username;
                User user = userRepository.findUserByEmail(username).orElseThrow(()->new NotFoundException("User with userName: %s not found ".formatted(finalUserName)));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities()));
            }
            catch (JWTVerificationException e){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
        filterChain.doFilter(request,response);
    }
}
