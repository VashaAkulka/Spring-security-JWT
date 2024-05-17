package ru.sermyazhko.Springsecurity.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sermyazhko.Springsecurity.entity.User;

import javax.crypto.SecretKey;

@Service
public class JWTService {
    private final String SECRET_KEY = "c73a55a8c837f76ea64ccf721c5b88507ba7e6d902c5c01cddb76d2d7670615c";

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey() {
        byte[] keyByte = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isValid(String token, UserDetails user) {
        return extractUsername(token).equals(user.getUsername());
    }
}
