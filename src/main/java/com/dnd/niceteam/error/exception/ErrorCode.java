package com.dnd.niceteam.error.exception;

import com.dnd.niceteam.common.Domain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.dnd.niceteam.common.Domain.*;
import static javax.servlet.http.HttpServletResponse.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    HANDLE_INTERNAL_SERVER_ERROR(COMMON, SC_INTERNAL_SERVER_ERROR, "서버에 문제가 발생했습니다."),
    HANDLE_ACCESS_DENIED(COMMON, SC_FORBIDDEN, "접근이 허용되지 않습니다."),
    INVALID_INPUT_VALUE(COMMON, SC_BAD_REQUEST, "유효하지 않은 입력 값입니다."),
    INVALID_TYPE_VALUE(COMMON, SC_BAD_REQUEST,  "유효하지 않은 타입입니다."),

    HANDLE_UNAUTHORIZED(AUTH, SC_UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    INVALID_TOKEN(AUTH, SC_BAD_REQUEST, "유효하지 않은 토큰입니다."),
    USERNAME_NOT_FOUND(AUTH, SC_NOT_FOUND, "존재하지 않는 아이디 입니다."),
    REFRESH_TOKEN_IS_NULL_ERROR(AUTH, SC_UNAUTHORIZED, "로그아웃 상태입니다."),

    MEMBER_NOT_FOUND(MEMBER, SC_NOT_FOUND, "존재하지 않는 회원입니다."),

    UNIVERSITY_NOT_FOUND(UNIVERSITY, SC_NOT_FOUND, "존재하지 않는 대학교입니다."),
    INVALID_EMAIL_DOMAIN(UNIVERSITY, SC_BAD_REQUEST, "적절하지 않은 이메일 도메인입니다."),
    ;

    private final Domain domain;

    private final int status;

    private final String message;
}
