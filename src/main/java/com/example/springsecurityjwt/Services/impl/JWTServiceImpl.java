package com.example.springsecurityjwt.Services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import com.example.springsecurityjwt.Entities.User;
import com.example.springsecurityjwt.Services.JWTService;
@Service
public class JWTServiceImpl implements JWTService{
    //Added the @Override annotation: This helps clarify that the method is implementing an interface method.
    String secretkey="413F4428472B4B625065536856605970337336763979244226452948404D6351";
    //3
    @Override
    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
    //2
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }
    // public String extractEmail(String token) {
    //     return extractClaim(token, claims -> claims.get("email", String.class));
    // }
    @SuppressWarnings("deprecation")
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode("413F4428472B4B625065536856605970337336763979244226452948404D6351");//Decodes the secretKey, which is stored as a Base64URL-encoded string. Base64URL encoding is commonly used for encoding binary data in URL-safe formats.
        return Keys.hmacShaKeyFor(keyBytes); // Use hmacShaKeyFor to create the signing key
    }
    //1
    @Override
    public String generateToken(UserDetails userDetails) {
        @SuppressWarnings("deprecation")
        String token = Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Set expiration to 24 minutes
                .signWith(getSigninKey(), SignatureAlgorithm.HS256) // Use key and HS256 algorithm
                .compact();

        return token;
    }  
@Override
public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    @SuppressWarnings("deprecation")
    String token = Jwts.builder()
            .setClaims(extraClaims)  // Add extra claims to the token
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 604800000)) // valid upto 7 days
            .signWith(getSigninKey(), SignatureAlgorithm.HS256) // Use key and HS256 algorithm
            .compact();

    return token;
}
    //4
    @Override
    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username=extractUserName(token);
        // return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());//check if expiration is before the current date
    }
}

// package com.example.springsecurityjwt.Services.impl;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import java.util.function.Function;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import java.security.Key;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import com.example.springsecurityjwt.Entities.User;
// import com.example.springsecurityjwt.Services.JWTService;
// @Service
// public class JWTServiceImpl implements JWTService{
//     private String secretkey="413F4428472B4B625065536856605970337336763979244226452948404D6351";
//     private long jwtExpiration=86400000;//1 day
//     private long refreshExpiration=604800000;//7 days
//     //Added the @Override annotation: This helps clarify that the method is implementing an interface method.
//     //3
//     public String extractUsername(String token) {
//     return extractClaim(token, Claims::getSubject);
//   }

//   public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//     final Claims claims = extractAllClaims(token);
//     return claimsResolver.apply(claims);
//   }

//   public String generateToken(UserDetails userDetails) {
//     return generateToken(new HashMap<>(), userDetails);
//   }

//   public String generateToken(
//       Map<String, Object> extraClaims,
//       UserDetails userDetails
//   ) {
//     return buildToken(extraClaims, userDetails, jwtExpiration);
//   }

//   public String generateRefreshToken(
//       UserDetails userDetails
//   ) {
//     return buildToken(new HashMap<>(), userDetails, refreshExpiration);
//   }

//   @SuppressWarnings("deprecation")
// private String buildToken(
//           Map<String, Object> extraClaims,
//           UserDetails userDetails,
//           long expiration
//   ) {
//     return Jwts
//             .builder()
//             .setClaims(extraClaims)
//             .setSubject(userDetails.getUsername())
//             .setIssuedAt(new Date(System.currentTimeMillis()))
//             .setExpiration(new Date(System.currentTimeMillis() + expiration))
//             .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//             .compact();
//   }

//   public boolean isTokenValid(String token, UserDetails userDetails) {
//     final String username = extractUsername(token);
//     return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//   }

//   private boolean isTokenExpired(String token) {
//     return extractExpiration(token).before(new Date());
//   }

//   private Date extractExpiration(String token) {
//     return extractClaim(token, Claims::getExpiration);
//   }

//   @SuppressWarnings("deprecation")
// private Claims extractAllClaims(String token) {
//             return Jwts
//                     .parser()
//                     .setSigningKey(getSignInKey())
//                     .build()
//                     .parseClaimsJws(token)
//                     .getBody();
//         }

//   private Key getSignInKey() {
//     byte[] keyBytes = Decoders.BASE64.decode(secretkey);
//     return Keys.hmacShaKeyFor(keyBytes);
//   }
// }

