package com.booklog.member.model.dto;


import lombok.*;



@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private int member_no;
    private String member_id;
    private String member_pw;
    private String member_name;
    private String member_email;
    //nullable
    private String member_joindate;
    private String member_token;
    private int member_admin;
    private int member_deleted;
}
