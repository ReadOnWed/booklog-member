package com.booklog.member.service;


import com.booklog.member.model.dto.MemberDto;
import com.booklog.member.model.dto.UpdateDto;

public interface MemberService {
    boolean registMember(MemberDto memberDto) throws Exception;

    MemberDto loginMember(MemberDto loginDto);

    void saveRefreshToken(int memberNo, String refreshToken) throws Exception;

    void deleteRefreshToken(int memberNo) throws Exception;

    String getRefreshToken(int memberNo);

    boolean updateMember(UpdateDto updateDto);

    boolean deleteMember(int member_no);

    MemberDto getMember(int memberNo);
}
