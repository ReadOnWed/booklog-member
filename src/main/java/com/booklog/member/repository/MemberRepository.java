package com.booklog.member.repository;

import com.booklog.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMemberId(@Param("memberId") String memberId);
    Member findByMemberNo(@Param("memberNo") int memberNo);
}
