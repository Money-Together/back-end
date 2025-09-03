package com.example.moneytogether1.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access-exp}")
    private long accessTokenValidity;

    @Value("${jwt.refresh-exp}")
    private long refreshTokenValidity;

    // memberId를 Long 타입으로 받아서 토큰 생성
    public String generateAccessToken(String uid, Long memberId) {
        return generateToken(uid, memberId, accessTokenValidity);
    }

    public String generateRefreshToken(String uid, Long memberId) {
        return generateToken(uid, memberId, refreshTokenValidity);
    }

    private String generateToken(String uid, Long memberId, long expiration) {
        return Jwts.builder()
                .setSubject(uid)
                .claim("memberId", memberId)         // member 테이블 PK
                .claim("nickname", "사용자닉네임")    // 필요시 수정
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    public boolean isExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String getUidFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();

        Object memberId = claims.get("memberId");
        if (memberId == null) {
            return null;
        }
        if (memberId instanceof Integer) {
            return ((Integer) memberId).longValue();
        }
        if (memberId instanceof Long) {
            return (Long) memberId;
        }
        return Long.parseLong(memberId.toString());
    }
}


//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser().setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String getUidFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//
//    public boolean isExpired(String token) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                    .parseClaimsJws(token)
//                    .getBody();
//
//
//
//            Date now = new Date();
//            Date exp = claims.getExpiration();
//            System.out.println("Now: " + now);
//            System.out.println("Token Expiration: " + exp);
//
//            return claims.getExpiration().before(new Date());
//        } catch (Exception e) {
//            return true;
//        }
//    }
//
//    public String getUidFromToken(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();  // setSubject로 넣은 값은 getSubject()로 꺼냄
//    }
//}


