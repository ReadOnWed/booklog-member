package com.booklog.member.controller;


import com.booklog.jwt.service.JwtService;
import com.booklog.member.model.dto.MemberDto;
import com.booklog.member.model.dto.UpdateDto;
import com.booklog.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "회원 api")
@RestController
@RequestMapping("/member")
public class MemberController {
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final MemberService memberService;
    private final JwtService jwtService;


    //추후 멤버서비스와 jwt관련 서비스 주입할 예정, 생성자 주입->6/13 서비스 주입완료
    //토큰은 redis연결 전까지는 mysql의 임의 컬럼에 저장하고 test할 예정
    //비밀번호 변경, 휴면? 정도
    //merge login to dev
    //merger update to dev
    @Autowired
    public MemberController(MemberService memberService, JwtService jwtService) {
        this.memberService=memberService;
        this.jwtService=jwtService;
    }


    /**
     * API 구현부
     *
     */
    @PostMapping("/regist")
    @ApiOperation(value = "Form으로 입력받은 회원 정보를 토대로 회원가입")
    public ResponseEntity<String> regist(@RequestBody MemberDto memberDto) throws Exception {
        //for debug
        System.out.println(memberDto);
        //중복여부 검사는 Front에서 사전처리하는게 맞는듯?
        if (memberService.registMember(memberDto)){
            return new ResponseEntity<String>(SUCCESS, HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<String>(FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    @ApiOperation(value = "Form으로 입력받은 회원 정보를 토대로 로그인, 토큰 반환")
    public ResponseEntity<Map<String, Object>> login(@RequestBody MemberDto memberDto){
        Map<String, Object> resultMap= new HashMap<>();
        HttpStatus status=null;
        try{
            MemberDto loginMember = memberService.loginMember(memberDto);
            if (loginMember!=null){
                //고유식별자로 토큰 발급
                String accessToken=jwtService.createAccessToken("member_no",loginMember.getMember_no());
                String refreshToken=jwtService.createRefreshToken("member_no",loginMember.getMember_no());
                memberService.saveRefreshToken(loginMember.getMember_no(), refreshToken);
                //for debug
                System.out.println(loginMember.getMember_no());
                resultMap.put("message", SUCCESS);
                resultMap.put("access-token",accessToken);
                resultMap.put("refresh-token",refreshToken);
                resultMap.put("member_nickname",loginMember.getMember_nickname());
                resultMap.put("member_no",loginMember.getMember_no());
                status=HttpStatus.ACCEPTED;
            }else{
                resultMap.put("message",FAIL);
                status=HttpStatus.ACCEPTED;
            }
        }catch(Exception e){
            resultMap.put("message", e.getMessage());
            status=HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
    @GetMapping("/logout/{member_no}")
    public ResponseEntity<?> logout(@PathVariable("member_no") int memberNo) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            memberService.deleteRefreshToken(memberNo);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            resultMap.put("message",FAIL);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Form으로 입력받은 데이터를 토대로 회원 정보 수정")
    public ResponseEntity<String> update(@RequestBody UpdateDto updateDto) throws Exception{
        System.out.println(updateDto);
        HttpStatus status=null;
        try {
            if (memberService.updateMember(updateDto)) {
                status=HttpStatus.ACCEPTED;
                return new ResponseEntity<String>(SUCCESS,status);
            }else{
                status=HttpStatus.BAD_REQUEST;
                return new ResponseEntity<String>(FAIL,status);
            }
        } catch (Exception e) {
            status=HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<String>(FAIL,status);
        }
    }

    @DeleteMapping("/delete/{member_no}")
    @ApiOperation(value = "회원 번호를 토대로 회원 정보 삭제")
    public ResponseEntity<String> delete(@PathVariable("member_no") int member_no) throws Exception{
        HttpStatus status=null;
        try {
            memberService.deleteMember(member_no);
            status=HttpStatus.ACCEPTED;
            return new ResponseEntity<String>(SUCCESS,status);
        } catch (Exception e) {
            status=HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<String>(FAIL,status);
        }
    }

    @GetMapping("/memberinfo/{member_no}")
    @ApiOperation(value = "토큰 만료 여부를 확인해 회원정보 반환")
    public ResponseEntity<Map<String,Object>> getMemberInfo(@PathVariable("member_no") int member_no, HttpServletRequest request) throws Exception{
        Map<String,Object> resultMap= new HashMap<>();
        HttpStatus status=HttpStatus.UNAUTHORIZED;
        if (jwtService.checkToken(request.getHeader("access-token"))){
            try {
                MemberDto memberDto=memberService.getMember(member_no);
                resultMap.put("message",SUCCESS);
                resultMap.put("memberInfo",memberDto);
                status=HttpStatus.ACCEPTED;
            } catch (Exception e) {
                resultMap.put("message",e.getMessage());
                status=HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }else{
            //사용불가능한 토큰임
            resultMap.put("message",FAIL);
            status=HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String,Object>>(resultMap,status);
    }

    /**
     * for token
     */
    @PostMapping("/refresh")
    @ApiOperation(value = "만료된 회원의 access-token을 전달받고 refresh-token으로 새로운 토큰 발급.")
    public ResponseEntity<?> refresh(MemberDto memberDto, HttpServletRequest request) throws  Exception{
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refresh-token");
        if (jwtService.checkToken(token)) {
            if (token.equals(memberService.getRefreshToken(memberDto.getMember_no()))) {
                String accessToken = jwtService.createAccessToken("member_no", memberDto.getMember_no());
                resultMap.put("access-token", accessToken);
                resultMap.put("message", SUCCESS);
                status = HttpStatus.ACCEPTED;
            }
        } else {
            status = HttpStatus.UNAUTHORIZED;
            resultMap.put("message", FAIL);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
