package com.mlog.user.controller;

import com.mlog.config.JwtTokenConfigure;
import com.mlog.security.WithMockJwtAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenConfigure jwtTokenConfigure;

    @BeforeEach
    void setUp(WebApplicationContext context,
               RestDocumentationContextProvider provider) {
        this.mockMvc = webAppContextSetup(context)
                .apply(documentationConfiguration(provider)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
                .build();
    }

    @Test
    public void 올바른_이메일_AND_비밀번호로_로그인할_경우_성공() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"principal\":\"tester@gmail.com\",\n\"credentials\":\"1234\"}")
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response.token").exists())
                .andExpect(jsonPath("$.response.token").isString())
                .andExpect(jsonPath("$.response.user.name", is("tester")))
                .andExpect(jsonPath("$.response.user.email", is("tester@gmail.com")))
                .andExpect(jsonPath("$.response.user.createdAt").exists())
                .andExpect(jsonPath("$.response.user.updatedAt").exists())
                .andDo(document("user/login"))
        ;
    }

    @Test
    public void 올바르지_않은_이메일_올바른_비밀번호로_로그인할_경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"principal\":\"wrong@gmail.com\",\"credentials\":\"1234\"}")
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    public void 올바른_이메일_올바르지_않은_비밀번호로_로그인할_경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"principal\":\"tester@gmail.com\",\"credentials\":\"0000\"}")
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    public void 올바르지_않은_이메일_올바르지_않은_비밀번호로_로그인할_경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"principal\":\"wrong@gmail.com\",\"credentials\":\"0000\"}")
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    public void 이메일이_비어있을_경우_로그인할_경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"principal\":\"\",\"credentials\":\"1234\"}")
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(400)))
        ;
    }

    @Test
    public void 비밀번호가_비어있을_경우_로그인할_경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"principal\":\"tester@gmail.com\",\"credentials\":\"\"}")
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("login"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(400)))
        ;
    }

    @Test
    public void 올바른_id로_조회했을경우_성공() throws Exception {
        ResultActions result = mockMvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("findById"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response.name", is("tester")))
                .andExpect(jsonPath("$.response.email", is("tester@gmail.com")))
                .andExpect(jsonPath("$.response.createdAt").exists())
                .andExpect(jsonPath("$.response.updatedAt").exists())
                .andDo(document("user/find"))
        ;
    }

    @Test
    public void 존재하지_않은_id로_조회했을경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(get("/user/" + Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON));
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("findById"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    @Transactional
    public void 올바른_정보를_입력하는_경우_회원가입_성공() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"ssafy\",\"email\":\"ssafy@gmail.com\",\"password\":\"1234\"}"));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response.name", is("ssafy")))
                .andExpect(jsonPath("$.response.email", is("ssafy@gmail.com")))
                .andExpect(jsonPath("$.response.createdAt").exists())
                .andExpect(jsonPath("$.response.updatedAt").exists())
                .andDo(document("user/join"))
        ;
    }

    @Test
    @Transactional
    public void 존재하는_email로_회원가입할_경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(post("/user/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"tester\",\"email\":\"tester@gmail.com\",\"password\":\"1234\"}"));
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("join"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    @Transactional
    public void 올바른_정보로_수정할경우_성공() throws Exception {
        ResultActions result = mockMvc.perform(put("/user/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"ssafy\"}"));
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(jsonPath("$.success", is(true)))
                .andDo(document("user/modify"))
        ;
    }

    @Test
    @Transactional
    public void 올바르지_않은_정보로_수정할경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(put("/user/" + Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"ssafy\"}"));
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("modify"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    @Transactional
    public void 올바른_정보로_삭제할경우_성공() throws Exception {
        ResultActions result = mockMvc.perform(delete("/user/" + 1)
                .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("$.success", is(true)))
                .andDo(document("user/delete"))
        ;
    }

    @Test
    @Transactional
    public void 올바르지_않은_정보로_삭제할경우_실패() throws Exception {
        ResultActions result = mockMvc.perform(delete("/user/" + Long.MAX_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("delete"))
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
        ;
    }

    @Test
    @WithMockJwtAuthentication
    @DisplayName("내 정보 조회 성공 테스트 (토큰이 올바른 경우)")
    void meSuccessTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/user/me")
                        .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(UserController.class))
                .andExpect(handler().methodName("me"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response.name", is("tester")))
                .andExpect(jsonPath("$.response.email", is("tester@gmail.com")))
                .andDo(document("user/me"))
        ;
    }

    @Test
    @DisplayName("내 정보 조회 실패 테스트 (토큰이 올바르지 않을 경우)")
    void meFailureTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/user/me")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(jwtTokenConfigure.getHeader(), "Bearer " + randomAlphanumeric(60))
        );
        result.andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.error").exists())
                .andExpect(jsonPath("$.error.status", is(401)))
                .andExpect(jsonPath("$.error.message", is("invalid authentication")))
        ;
    }
}
