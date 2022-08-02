package com.dnd.niceteam.emailauth.controller;

import com.dnd.niceteam.common.RestDocsConfig;
import com.dnd.niceteam.emailauth.dto.EmailAuthKeySendRequestDto;
import com.dnd.niceteam.emailauth.service.EmailAuthService;
import com.dnd.niceteam.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(RestDocsConfig.class)
@AutoConfigureRestDocs
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(controllers = EmailAuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class) })
class EmailAuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmailAuthService emailAuthService;

    @Test
    @WithMockUser
    @DisplayName("이메일 인증번호 전송")
    void emailAuthKeySend_Success() throws Exception {
        // given
        EmailAuthKeySendRequestDto requestDto = new EmailAuthKeySendRequestDto();
        requestDto.setEmail("teat@teamgoo.ac.kr");
        requestDto.setUnivName("팀구대학교");

        // expected
        mockMvc.perform(post("/email-auth/send").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andDo(document("email-auth-send",
                        requestFields(
                                fieldWithPath("email").description("인증할 이메일")
                                        .attributes(key("constraint").value("해당 대학교의 이메일 도메인과 일치")),
                                fieldWithPath("univName").description("대학교 이름")
                        ),
                        responseFields(
                                fieldWithPath("success").description("요청 성공 -> 인증번호 해당 이메일로 발송")
                        )
                ));
    }
}