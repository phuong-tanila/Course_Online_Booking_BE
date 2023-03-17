package fa.training.backend.helpers;

import fa.training.backend.entities.User;
import io.jsonwebtoken.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
@AllArgsConstructor
@Getter @Setter
//@ConfigurationProperties(prefix ="token")
class TokenInfo{
    public static  String accessSecretKey = "UkXp2s5v8y/B?E(H+MbQeThWmYq3t6w9z$C&F)J@NcRfUjXn2r4u7x!A%D*G-KaP";
    public static Long accessExpiration = 300000L;
    public static String refreshSecretKey = "QeThWmZq4t7w!z%C&F)J@NcRfUjXn2r5u8x/A?D(G-KaPdSgVkYp3s6v9y$B&E)H";
    public static Long refreshExpiration = 604800000L;
}
@Slf4j
@Configuration
public class JwtProvider {

    @Autowired
    private static TokenInfo tokenInfo;

    public static String generateToken(User user, String jwtSecret, long jwtExpiration) {
        System.out.println(jwtSecret);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpiration);
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public static String generateRefreshToken(User user) {
        return generateToken(user, tokenInfo.refreshSecretKey, tokenInfo.refreshExpiration);
    }

    public static String generateAccessToken(User user) {
        return generateToken(user, tokenInfo.accessSecretKey, tokenInfo.accessExpiration);
    }
    public static String getUserEmailFromJWT(String token, String jwtSecret) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public static String getUserEmailFromAccessToken(String token) {
        return getUserEmailFromJWT(token,tokenInfo.accessSecretKey);
    }
    public static String getUserEmailFromRefreshToken(String token) {
        return getUserEmailFromJWT(token,tokenInfo.refreshSecretKey);
    }
    public static boolean validateRefreshToken(String token){
        return validateToken(token, tokenInfo.refreshSecretKey);
    }
    public static boolean validateAccessToken(String token){
        return validateToken(token, tokenInfo.accessSecretKey);
    }

    public static boolean validateToken(String authToken, String jwtSecret) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
