package com.dnd.niceteam.security;

import com.dnd.niceteam.common.RestDocsConfig;
import com.dnd.niceteam.common.dto.ApiResult;
import com.dnd.niceteam.member.domain.Member;
import com.dnd.niceteam.member.repository.MemberRepository;
import com.dnd.niceteam.security.auth.dto.AuthRequestDto;
import com.dnd.niceteam.security.jwt.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(RestDocsConfig.class)
@Transactional
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class AuthApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("로그인 - 성공")
    void loginApi_Success() throws Exception {
        //given
        memberRepository.save(Member.builder()
                .username("test-username")
                .password(passwordEncoder.encode("testPassword11!"))
                .email("test@email.com")
                .name("test-name")
                .build());
        em.flush();
        em.clear();

        AuthRequestDto.Login loginDto = new AuthRequestDto.Login();
        loginDto.setUsername("test-username");
        loginDto.setPassword("testPassword11!");

        //expected
        mockMvc.perform(post("/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ApiResult.Status.SUCCESS.name()))
                .andExpect(jsonPath("$.data.accessToken").exists())
                .andExpect(jsonPath("$.data.refreshToken").exists())
                .andDo(document("auth-login",
                        requestFields(
                                fieldWithPath("username").description("로그인 아이디"),
                                fieldWithPath("password").description("로그인 비밀번호")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("accessToken").description("JWT Access Token"),
                                fieldWithPath("refreshToken").description("JWT Refresh Token")
                        )
                ));
    }

    @Test
    @DisplayName("로그아웃 - 성공")
    void logoutApi_Success() throws Exception {
        //given
        Member member = memberRepository.save(Member.builder()
                .username("test-username")
                .password(passwordEncoder.encode("testPassword11!"))
                .email("test@email.com")
                .name("test-name")
                .build());
        String accessToken = jwtTokenProvider.createAccessToken(member.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getUsername());
        member.setRefreshToken(refreshToken);
        em.flush();
        em.clear();

        //expected
        mockMvc.perform(post("/logout")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, accessToken))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(ApiResult.Status.SUCCESS.name()))
                .andDo(document("auth-logout",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("JWT access token")
                        )
                ));
    }
}
