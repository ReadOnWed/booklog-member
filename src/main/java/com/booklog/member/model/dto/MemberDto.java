package com.booklog.member.model.dto;


import lombok.*;



@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    //public data
    @Setter
    private int member_no;
    @Setter
    private String member_nickname;

    //private data
    private String member_id;
    private String member_pw;
    private String member_name;
    private String member_email;
    //nullable
    private String member_joindate;
}
