package com.booklog.member.service;


import com.booklog.member.model.MemberDto;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    boolean registMember(MemberDto memberDto) throws Exception;
}
