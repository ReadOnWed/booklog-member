package com.booklog.member.service;


import com.booklog.member.model.dto.MemberDto;

public interface MemberService {
    boolean registMember(MemberDto memberDto) throws Exception;
}
