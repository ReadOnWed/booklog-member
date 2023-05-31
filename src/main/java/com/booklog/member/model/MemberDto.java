package com.booklog.member.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MemberDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int member_no;
    @Column(name = "member_id", nullable = false)
    private String member_id;
    @Column(name = "member_pw", nullable = false)
    private String member_pw;
    @Column(name = "member_name", nullable = false)
    private String member_name;
    @Column(name = "member_email", nullable = false)
    private String member_email;
    @Column(name = "member_joindate", nullable = false)
    private String member_joindate;

    @Builder
    public MemberDto(String member_id, String member_pw, String member_name, String member_email) {
        this.member_id = member_id;
        this.member_pw = member_pw;
        this.member_name = member_name;
        this.member_email = member_email;
    }
}
