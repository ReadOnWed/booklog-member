package com.booklog.member.controller;


import com.booklog.member.model.MemberDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    //추후 멤버서비스와 jwt관련 서비스 주입할 예정, 생성자 주입
    //dev branch test
    //feat branch test
    //dev branch merge test
    @Autowired
    public MemberController() {
    }

    @PostMapping("/regist")
    @ApiOperation(value = "Form으로 입력받은 회원 정보를 토대로 회원가입")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Attractions not found")
    })
    public ResponseEntity<String> regist(@RequestBody MemberDto memberDto) throws Exception {
        System.out.println(memberDto);
        return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
    }
}
