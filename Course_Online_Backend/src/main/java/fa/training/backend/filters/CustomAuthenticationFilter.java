package fa.training.backend.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import fa.training.backend.helpers.jwt.JwtProvider;
import fa.training.backend.model.ExceptionResponse;
import fa.training.backend.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    public UserService userService;

    public CustomAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Lấy jwt từ request
            String jwt = getJwtFromRequest(request);
            System.err.println(StringUtils.hasText(jwt) && JwtProvider.validateAccessToken(jwt));
            if (StringUtils.hasText(jwt) && JwtProvider.validateAccessToken(jwt)) {
                String userEmail = JwtProvider.getUserEmailFromAccessToken(jwt);
                UserDetails userDetails = userService.loadUserByUsername(userEmail);
                System.out.println(userDetails);
                if(userDetails != null) {
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            response.setStatus(FORBIDDEN.value());
            response.setHeader("Exception", "Expired JWT token");
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                    "Expired JWT token",
                    "Your JWT Token is expired"
            );
            response.setContentType("application/json");
            response.getWriter().println(new ObjectMapper().writeValueAsString(exceptionResponse));
        } catch (Exception ex) {
            log.error("failed on set user authentication", ex);
        }


    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        System.out.println(bearerToken);
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
