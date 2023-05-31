package com.booklog.member.controller;


import com.booklog.member.model.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";


    //추후 멤버서비스와 jwt관련 서비스 주입할 예정, 생성자 주입
    @Autowired
    public MemberController() {
    }

    @PostMapping("/regist")
    public ResponseEntity<String> regist(@RequestBody MemberDto memberDto) throws Exception {
        System.out.println(memberDto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
