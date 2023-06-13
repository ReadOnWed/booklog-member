package com.booklog.member.service;


import com.booklog.member.model.dto.MemberDto;

public interface MemberService {
    boolean registMember(MemberDto memberDto) throws Exception;

    MemberDto loginMember(MemberDto memberDto);

    void saveRefreshToken(int memberNo, String refreshToken) throws Exception;

    void deleteRefreshToken(int memberNo) throws Exception;

    String getRefreshToken(int memberNo);
}
