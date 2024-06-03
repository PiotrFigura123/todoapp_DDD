package pl.piotrFigura.ToDoApp.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
class JWTServiceImpl implements JWTService{

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getSignKey(), SignatureAlgorithm.HS256)
            .compact();
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignKey() {
        byte[] key = Decoders.BASE64.decode("12312412312321421321341241");
        return Keys.hmacShaKeyFor(key);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String usrename = extractUserName(token);
        return (usrename.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
