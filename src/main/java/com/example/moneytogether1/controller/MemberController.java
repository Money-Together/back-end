package com.example.moneytogether1.controller;

import com.example.moneytogether1.dto.MemberDto;
import com.example.moneytogether1.dto.WalletDto;
import com.example.moneytogether1.dto.request.MemberUpdateRequest;
import com.example.moneytogether1.dto.request.WalletCreateRequest;
import com.example.moneytogether1.dto.request.WalletUpdateRequest;
import com.example.moneytogether1.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<?> getMyInfo(Authentication authentication) {
        try {
            Long memberId = (Long) authentication.getPrincipal();
            MemberDto memberInfo = memberService.getMember(memberId);
            return ResponseEntity.ok(memberInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("내 정보 조회 중 오류가 발생했습니다.");
        }
    }

    /**
     * 프로필 수정 (이미지 파일 포함)
     * JSON + 파일을 같이 보내야 하므로 multipart/form-data 사용
     */
    @PatchMapping(value = "/profile", consumes = "multipart/form-data")
    public ResponseEntity<?> updateMyInfo(Authentication authentication,
                                          @RequestPart(value = "data") MemberUpdateRequest request,
                                          @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            Long memberId = (Long) authentication.getPrincipal();
            MemberDto updated = memberService.updateMember(memberId, request, imageFile);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("정보 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

//    @GetMapping("/wallet")
//    public ResponseEntity<?> getWalletDetail(Authentication authentication) {
//        try {
//            Long memberId = (Long) authentication.getPrincipal();
//            WalletDto walletDetail = memberService.getWallet(memberId);
//            return ResponseEntity.ok(walletDetail);
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("서버 에러가 발생했습니다.");
//        }
//    }

    /**
     * 지갑 등록
     */
    @PostMapping("/wallet")
    public ResponseEntity<?> createWallet(@Valid @RequestBody WalletCreateRequest request,
                                          Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        try {
            memberService.createWallet(memberId, request);
            return ResponseEntity.ok("지갑이 성공적으로 생성되었습니다.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * 지갑 수정
     */
    @PatchMapping("/wallet")
    public ResponseEntity<?> updateWallet(@Valid @RequestBody WalletUpdateRequest request,
                                          Authentication authentication) {
        Long memberId = (Long) authentication.getPrincipal();
        try {
            memberService.updateWallet(memberId, request);
            return ResponseEntity.ok("지갑 정보가 성공적으로 수정되었습니다.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

