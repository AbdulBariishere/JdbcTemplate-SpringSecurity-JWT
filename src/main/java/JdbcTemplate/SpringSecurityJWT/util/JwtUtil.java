package JdbcTemplate.SpringSecurityJWT.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        }catch (Exception ex){

            System.out.println("exception : "+ex.getMessage());
            return ex.getMessage();
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Calendar cal = Calendar.getInstance(Locale.US);
        Calendar cal1 = Calendar.getInstance(Locale.US);
        cal1.setTime(cal.getTime());
        cal1.add(Calendar.SECOND, 300);
        Calendar cal2=Calendar.getInstance(Locale.US);
        System.out.println("cal time"+cal.getTime());
        System.out.println("cal 2 time "+cal1.getTime());

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(cal.getTime())
                .setExpiration(cal1.getTime())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}