package com.example.moneytogether1.controller;

import com.example.moneytogether1.dto.request.IdTokenRequest;
import com.example.moneytogether1.dto.request.RefreshRequest;
import com.example.moneytogether1.dto.response.TokenResponse;
import com.example.moneytogether1.jwt.JwtUtil;
import com.example.moneytogether1.service.FirebaseAuthService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final JwtUtil jwtUtil;
    private final FirebaseAuthService firebaseAuthService;

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody IdTokenRequest request) {
        String idToken = request.getIdToken();

        try {
            // 유저 저장 또는 업데이트
            firebaseAuthService.verifyAndSaveMember(idToken);

            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = decodedToken.getUid();

            // DB에서 firebaseUid 기준 memberId 조회
            Long memberId = firebaseAuthService.getMemberIdByUid(uid);

            // JWT 발급 (uid와 memberId 같이 넣음)
            String accessToken = jwtUtil.generateAccessToken(uid, memberId);
            String refreshToken = jwtUtil.generateRefreshToken(uid, memberId);

            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
        } catch (Exception e) {
            log.error("Token verification failed", e);
            return ResponseEntity.status(401).body("Invalid Firebase ID token");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (jwtUtil.isExpired(refreshToken)) {
            return ResponseEntity.status(401).body("Refresh Token expired");
        }

        String uid = jwtUtil.getUidFromToken(refreshToken);
        Long memberId = jwtUtil.getMemberIdFromToken(refreshToken);

        String newAccessToken = jwtUtil.generateAccessToken(uid, memberId);

        return ResponseEntity.ok(new TokenResponse(newAccessToken, refreshToken));
    }

}
