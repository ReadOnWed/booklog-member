package com.booklog.member.service;

import com.booklog.member.model.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberServiceImpl implements MemberService{

    //추후 memberRepository 의존성 주입 예정
    @Autowired
    public MemberServiceImpl() {
    }

    @Override
    public boolean registMember(MemberDto memberDto) throws Exception {
        //추후 상세 로직 구현할 예정
        return true;
    }
}
