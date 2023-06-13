package com.booklog.member.controller;


import com.booklog.jwt.service.JwtService;
import com.booklog.member.model.dto.MemberDto;
import com.booklog.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "회원 api")
@RestController
@RequestMapping("/member")
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final MemberService memberService;
    private final JwtService jwtService;


    //추후 멤버서비스와 jwt관련 서비스 주입할 예정, 생성자 주입->6/13 서비스 주입완료
    //for deployment test

    @Autowired
    public MemberController(MemberService memberService, JwtService jwtService) {
        this.memberService=memberService;
        this.jwtService=jwtService;
    }

    @PostMapping("/regist")
    @ApiOperation(value = "Form으로 입력받은 회원 정보를 토대로 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Attractions not found")
    })
    public ResponseEntity<String> regist(@RequestBody MemberDto memberDto) throws Exception {
        System.out.println(memberDto);
        //중복여부 검사는 Front에서 사전처리하는게 맞는듯?
        //혹시모르니까 재확인 로직을 세우자
        if (memberService.registMember(memberDto)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.BAD_REQUEST);
        }
    }
}
