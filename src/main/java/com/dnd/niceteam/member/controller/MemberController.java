package com.dnd.niceteam.member.controller;

import com.dnd.niceteam.common.dto.ApiResult;
import com.dnd.niceteam.member.dto.DupCheck;
import com.dnd.niceteam.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/dup-check/email")
    public ResponseEntity<ApiResult<DupCheck.ResponseDto>> memberEmailDupCheck(@RequestParam @Email String email) {
        DupCheck.ResponseDto responseDto = memberService.checkEmailDuplicate(email);
        ApiResult<DupCheck.ResponseDto> apiResult = ApiResult.success(responseDto);
        return ResponseEntity.ok(apiResult);
    }

    @GetMapping("/dup-check/nickname")
    public ResponseEntity<ApiResult<DupCheck.ResponseDto>> memberNicknameDupCheck(
            @RequestParam @NotBlank @Size(max = 10) String nickname) {
        DupCheck.ResponseDto responseDto = memberService.checkNicknameDuplicate(nickname);
        ApiResult<DupCheck.ResponseDto> apiResult = ApiResult.success(responseDto);
        return ResponseEntity.ok(apiResult);
    }
}