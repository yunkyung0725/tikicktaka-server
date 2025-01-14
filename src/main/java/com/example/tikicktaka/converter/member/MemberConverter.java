package com.example.tikicktaka.converter.member;

import com.example.tikicktaka.domain.enums.MemberRole;
import com.example.tikicktaka.domain.mapping.member.MemberTerm;
import com.example.tikicktaka.domain.member.Auth;
import com.example.tikicktaka.domain.member.Member;
import com.example.tikicktaka.domain.member.Term;
import com.example.tikicktaka.web.dto.member.MemberRequestDTO;
import com.example.tikicktaka.web.dto.member.MemberResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDTO request, BCryptPasswordEncoder encoder){
        return Member.builder()
                .nickname(request.getNickname())
                .name(request.getName())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .phone(request.getPhone())
                .memberRole(MemberRole.MEMBER)
                .memberTermList(new ArrayList<>())
                .build();
    }

    public static MemberResponseDTO.LoginResultDTO toLoginResultDTO(String jwt){
        return MemberResponseDTO.LoginResultDTO.builder()
                .jwt(jwt)
                .build();
    }

    public static MemberResponseDTO.LoginIdDuplicateConfirmResultDTO toLoginIdDuplicateConfirmResultDTO(Boolean checkLoginId){
        return MemberResponseDTO.LoginIdDuplicateConfirmResultDTO.builder()
                .checkLoginId(checkLoginId)
                .build();
    }

    public static MemberResponseDTO.NicknameDuplicateConfirmResultDTO toNicknameDuplicateConfirmResultDTO(Boolean checkNickname){
        return MemberResponseDTO.NicknameDuplicateConfirmResultDTO.builder()
                .checkNickname(checkNickname)
                .build();
    }

    public static List<MemberTerm> toMemberTermList(HashMap<Term, Boolean> termList) {

        return termList.entrySet().stream()
                .map(entry -> MemberTerm.builder()
                        .term(entry.getKey())
                        .memberAgree(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public static MemberResponseDTO.EmailAuthSendResultDTO toEmailAuthSendResultDTO(Auth auth){
        return MemberResponseDTO.EmailAuthSendResultDTO.builder()
                .email(auth.getEmail())
                .authCode(auth.getCode())
                .build();
    }

    public static Auth toEmailAuth(String email, String code, Boolean expired){
        return Auth.builder()
                .email(email)
                .code(code)
                .expireDate(LocalDateTime.now().plusMinutes(5))
                .expired(expired)
                .build();
    }

    public static MemberResponseDTO.EmailAuthConfirmResultDTO toEmailAuthConfirmResultDTO(Boolean checkEmail){
        return MemberResponseDTO.EmailAuthConfirmResultDTO.builder()
                .checkEmail(checkEmail)
                .build();
    }
}
