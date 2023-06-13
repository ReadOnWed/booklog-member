package com.booklog.member.service;

import com.booklog.member.dao.MemberDao;
import com.booklog.member.model.dto.MemberDto;
import com.booklog.member.model.dto.UpdateDto;
import com.booklog.member.model.entity.Member;
import com.booklog.member.repository.MemberRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService{
    /*
    의존성 주입부
     */
    private final MemberRepository memberRepository;
    private final MemberDao memberDao;

    @Autowired
    public MemberServiceImpl(MemberDao memberDao,MemberRepository memberRepository) {
        this.memberDao=memberDao;
        this.memberRepository=memberRepository;
    }

    /*
    구현부
     */
    @Override
    public boolean registMember(MemberDto memberDto) throws Exception {
        //추후 상세 로직 구현할 예정
        //회원가입
        if (memberRepository.findByMemberId(memberDto.getMember_id())!=null){
            return false;
        }else{
            //암호화하여 저장
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String securePw = encoder.encode(memberDto.getMember_pw());
            Member memberEntity= Member.builder()
                                 .memberId(memberDto.getMember_id())
                                 .memberName(memberDto.getMember_name())
                                 .memberPw(securePw)
                                 .memberNickname(memberDto.getMember_nickname())
                                 .memberEmail(memberDto.getMember_email())
                                 .build();
            //for debug
            System.out.println(memberEntity.toString());
            memberRepository.save(memberEntity);
            return true;
        }
    }

    @Override
    public MemberDto loginMember(MemberDto loginDto) {
        Member memberEntity=memberRepository.findByMemberId(loginDto.getMember_id());
        if (memberEntity != null) { // parameter로 받은 평문과 DB에 저장된 암호화값 비교
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (encoder.matches(loginDto.getMember_pw(), memberEntity.getMemberPw())) {
                loginDto.setMember_nickname(memberEntity.getMemberNickname());
                loginDto.setMember_no(memberEntity.getMemberNo());
                System.out.println(loginDto);
                return loginDto;
            }
        }
        //for debug
        System.out.println("로그인 실패");
        return null;
    }

    @Override
    public void saveRefreshToken(int memberNo, String refreshToken) throws Exception {
        //수정을 위한 마이바티스 혼용?, setter를 열어 더티체킹을 하거나 nativequery를 사용하는 방식이 마음에 들지 않음
        Map<String, Object> map = new HashMap<>();
        map.put("member_no", memberNo);
        map.put("token", refreshToken);
        memberDao.saveRefreshToken(map);
    }

    @Override
    public void deleteRefreshToken(int memberNo) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("member_no", memberNo);
        map.put("token", null);
        memberDao.deleteRefreshToken(map);
    }

    @Override
    public String getRefreshToken(int memberNo) {
        Member memberEntity=memberRepository.findByMemberNo(memberNo);
        return memberEntity.getMemberToken();
    }

    @Override
    public boolean updateMember(UpdateDto updateDto) {
        try {
            memberDao.updateMember(updateDto);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteMember(int member_no) {
        try {
            memberDao.deleteMember(member_no);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public MemberDto getMember(int memberNo) {
        Member entity= memberRepository.findByMemberNo(memberNo);
        MemberDto memberDto=new MemberDto(entity.getMemberNo(),entity.getMemberNickname(),
                entity.getMemberId(),null,entity.getMemberName(), entity.getMemberEmail(), entity.getMemberJoindate());
        return memberDto;
    }
}
