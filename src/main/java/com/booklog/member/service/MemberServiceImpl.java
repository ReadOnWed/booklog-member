package com.booklog.member.service;

import com.booklog.member.model.dto.MemberDto;
import com.booklog.member.model.entity.Member;
import com.booklog.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    //추후 memberRepository 의존성 주입 예정->6/13주입 완료
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository=memberRepository;
    }

    @Override
    public boolean registMember(MemberDto memberDto) throws Exception {
        //추후 상세 로직 구현할 예정
        //회원가입
        if (memberRepository.findByMemberId(memberDto.getMember_id())!=null){
            return false;
        }else{
            Member memberEntity= Member.builder()
                                 .memberId(memberDto.getMember_id())
                                 .memberName(memberDto.getMember_name())
                                 .memberPw(memberDto.getMember_pw())
                                 .memberEmail(memberDto.getMember_email())
                                 .build();
            System.out.println(memberEntity.toString());
            memberRepository.save(memberEntity);
            return true;
        }
    }
}
