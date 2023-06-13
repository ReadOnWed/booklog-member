package com.booklog.member.model.entity;

import lombok.*;

import javax.persistence.*;
@Entity
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder
@ToString
public class Member {
    @Id
    @Column(name = "member_no", nullable = true, insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;
    @Column(name = "member_id", nullable = false)
    private String memberId;
    @Column(name = "member_pw", nullable = false)
    private String memberPw;
    @Column(name = "member_name", nullable = false)
    private String memberName;
    @Column(name = "member_email", nullable = false)
    private String memberEmail;
    @Column(name = "member_joindate", nullable = true, insertable = false)
    private String memberJoindate;
    @Column(name = "member_admin", nullable = true,insertable = false)
    private String memberAdmin;
    @Column(name = "member_deleted", nullable = true, insertable = false)
    private String memberDeleted;
    @Column(name = "member_token", nullable = true, insertable = false)
    private String memberToken;
}