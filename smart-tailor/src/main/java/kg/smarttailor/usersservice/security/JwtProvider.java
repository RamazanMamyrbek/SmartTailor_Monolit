package kg.smarttailor.usersservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import kg.smarttailor.usersservice.props.JwtProperties;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtProvider {
    final JwtProperties jwtProperties;
    Key key;

    @PostConstruct
    void init() {
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String generateAccessToken(JwtUserDetails userDetails) {
        Claims claims = Jwts.claims();
        claims.setSubject(userDetails.getUsername());
        claims.put("id", userDetails.getUser().getId());
        claims.put("permissions", userDetails.getAuthorities());
        claims.setIssuer("Smart-tailor-app");
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(new Date().getTime() + jwtProperties.getAccess()));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(JwtUserDetails userDetails) {
        Claims claims = Jwts.claims();
        claims.setSubject(userDetails.getUsername());
        claims.put("id", userDetails.getUser().getId());
        claims.setIssuer("Smart-tailor-app");
        claims.setIssuedAt(new Date());
        claims.setExpiration(new Date(new Date().getTime() + jwtProperties.getRefresh()));
        return Jwts.builder()
                .setClaims(claims)
                .signWith(key)
                .compact();
    }
    public boolean validateToken(String token) {
        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date()) && claims.getIssuer().equals("Smart-tailor-app");
    }
    private Claims parseClaims(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public String getEmail(String token) {
        return parseClaims(token).getSubject();
    }
}
