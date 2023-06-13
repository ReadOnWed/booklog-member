package com.booklog.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDto {
    private String member_no;
    private String member_name;
    private String member_email;
    private String member_nickname;
}
