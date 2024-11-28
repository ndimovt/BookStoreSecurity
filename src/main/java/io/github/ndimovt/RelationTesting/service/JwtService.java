package io.github.ndimovt.RelationTesting.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The class JwtService
 */
@Service
public class JwtService {
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extracts single claim
     * @param token String object
     * @param claimsResolver Function object
     * @return Function object
     * @param <T> Type object
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts username from jwt token.
     * @param token String object
     * @return String object
     */
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Generates Jwt token based on UserDetails
     * @param userDetails UserDetails object
     * @return String object
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Builds Jwt token along with expiration time
     * @param claims Map object
     * @param userDetails UserDetails object
     * @return String object
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails){
        return buildToken(claims, userDetails, jwtExpiration);
    }

    /**
     * Return expiration date
     * @return long primitive
     */
    public long getJwtExpiration(){
        return jwtExpiration;
    }

    /**
     * Checks Jwt token validity.
     * @param token String object
     * @param userDetails UserDetails object
     * @return boolean primitive
     */
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = userDetails.getUsername();
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration){
        return Jwts.
                builder().
                claims(extraClaims).
                subject(userDetails.getUsername()).
                issuedAt(new Date(System.currentTimeMillis())).
                expiration(new Date(System.currentTimeMillis() + expiration)).
                signWith(getSignInKey()).compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts.
                parser().
                verifyWith((SecretKey) getSignInKey()).
                build().
                parseSignedClaims(token).
                getPayload();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
