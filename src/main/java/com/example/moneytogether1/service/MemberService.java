package com.example.moneytogether1.service;

import com.example.moneytogether1.dto.MemberDto;
import com.example.moneytogether1.dto.WalletDto;
import com.example.moneytogether1.dto.request.MemberUpdateRequest;
import com.example.moneytogether1.dto.request.WalletCreateRequest;
import com.example.moneytogether1.dto.request.WalletUpdateRequest;
import com.example.moneytogether1.entity.Member;
import com.example.moneytogether1.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ImageService imageService; // 이미지 처리 전담

    public MemberDto getMember(Long memberId) {
        Member member = getActiveMember(memberId);

        return MemberDto.builder()
                .nickname(member.getNickname())
                .email(member.getEmail())
                .image(member.getImage())
                .build();
    }

    public void saveProfileImageFromGoogle(Long memberId, String googleImageUrl) {
        Member member = getActiveMember(memberId);
        String savedImageUrl = imageService.saveImageFromUrl(googleImageUrl);
        member.setImage(savedImageUrl);
        memberRepository.save(member);
    }

    public MemberDto updateMember(Long memberId, MemberUpdateRequest request, MultipartFile imageFile) {
        Member member = getActiveMember(memberId);

        if (request.getNickname() != null && !request.getNickname().isBlank()) {
            member.setNickname(request.getNickname());
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = imageService.saveImage(imageFile);
                member.setImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패: " + e.getMessage(), e);
            }
        }

        Member saved = memberRepository.save(member);

        return MemberDto.builder()
                .nickname(saved.getNickname())
                .email(saved.getEmail())
                .image(saved.getImage())
                .build();
    }

    public void createWallet(Long memberId, WalletCreateRequest request) {
        Member member = getActiveMember(memberId);

//        if (request.getWalletName() == null || request.getWalletName().isBlank()) {
//            throw new IllegalArgumentException("가게 이름은 필수입니다.");
//        }
//
//        member.setWalletYn(true);
//        member.setWalletName(request.getWalleteName());
//        member.setWalletExplain(request.getWalletExplain());
        

        memberRepository.save(member);
    }

    public void updateWallet(Long memberId, WalletUpdateRequest request) {
        Member member = getActiveMember(memberId);

//        if (!Boolean.TRUE.equals(member.getWalletYn())) {
//            throw new IllegalStateException("가게가 존재하지 않아 수정할 수 없습니다.");
//        }
//
//        if (request.getWalletName() != null && !request.getWalletName().isBlank()) {
//            member.setWalletName(request.getWalletName());
//        }
//
//        if (request.getWalletExplain() != null) {
//            member.setWalletExplain(request.getWalletExplain());
//        }

        memberRepository.save(member);
    }

    public WalletDto getWallet(Long memberId) {
        Member member = getActiveMember(memberId);

//        if (Boolean.FALSE.equals(member.getWalletYn())) {
//            throw new RuntimeException("가게 정보가 없습니다.");
//        }
//
        return WalletDto.builder()
            //    .walletName(member.getWalletName())
              //  .walletExplain(member.getWalletExplain())
                .build();
    }

    private Member getActiveMember(Long memberId) {
        return memberRepository.findById(memberId)
                .filter(m -> Boolean.TRUE.equals(m.getUseYn()))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}

