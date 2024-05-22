package com.mlog.travel.controller;

import com.mlog.security.WithMockJwtAuthentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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
class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    @WithMockJwtAuthentication
    @Transactional
    public void 여행정보_저장() throws Exception {
        ResultActions result = mockMvc.perform(post("/travel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{" +
                                "\"title\": \"행복한 여행\",\n" +
                                "    \"description\": \"한줄평!! 너무 좋습니다!\",\n" +
                                "    \"image\": \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAiiiiiiiiiiiiiiiiiiiiiiiii\",\n" +
                                "    \"lat\": 35.20396241242666,\n" +
                                "    \"lng\": 126.80639199013274,\n" +
                                "    \"startDate\": \"2024-05-21T15:19:49.598Z\",\n" +
                                "    \"endDate\": \"2024-05-29T15:19:49.598Z\",\n" +
                                "    \"rating\": 5,\n" +
                                "    \"detailedSchedules\": [\n" +
                                "        {\n" +
                                "            \"seq\": 1,\n" +
                                "            \"title\": \"상세 일정1\",\n" +
                                "            \"description\": \"상세 일정 설명 1\",\n" +
                                "            \"images\": [\n" +
                                "               \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAiiiiiiiiiiiiiiiiiiiiiiiii\",\n" +
                                "               \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAiiiiiiiiiiiiiiiiiiiiiiiii\"\n" +
                                "            ]\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"seq\": 2,\n" +
                                "            \"title\": \"상세 일정2\",\n" +
                                "            \"description\": \"상세 일정 설명 2\",\n" +
                                "            \"images\": [\n" +
                                "               \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAiiiiiiiiiiiiiiiiiiiiiiiii\",\n" +
                                "               \"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAiiiiiiiiiiiiiiiiiiiiiiiii\"\n" +
                                "            ]\n" +
                                "        }\n" +
                                "    ]\n" +
                                "}")
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(TravelController.class))
                .andExpect(handler().methodName("saveTravel"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response", is(true)))
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(document("travel/save"))
        ;
    }

    @Test
    @WithMockJwtAuthentication
    public void 사용자_여행정보_리스트_가져오기() throws Exception {
        ResultActions result = mockMvc.perform(get("/travel")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(TravelController.class))
                .andExpect(handler().methodName("travelListResult"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response.travelList").exists())
                .andExpect(jsonPath("$.response.travelList[0].id").isNumber())
                .andExpect(jsonPath("$.response.travelList[0].description").isString())
                .andExpect(jsonPath("$.response.travelList[0].lat").isString())
                .andExpect(jsonPath("$.response.travelList[0].lng").isString())
                .andExpect(jsonPath("$.response.travelList[0].imageUrl").isString())
                .andExpect(jsonPath("$.response.travelList[0].title").exists())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(document("travel/list"))
        ;
    }

    @Test
    public void 사용자_여행상세정보_가져오기() throws Exception {
        ResultActions result = mockMvc.perform(get("/travel/6")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(TravelController.class))
                .andExpect(handler().methodName("travelDetailResult"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.title").isString())
                .andExpect(jsonPath("$.response.description").isString())
                .andExpect(jsonPath("$.response.lat").isString())
                .andExpect(jsonPath("$.response.lng").isString())
                .andExpect(jsonPath("$.response.imageUrl").isString())
                .andExpect(jsonPath("$.response.startDate").isString())
                .andExpect(jsonPath("$.response.endDate").isString())
                .andExpect(jsonPath("$.response.rating").isNumber())
                .andExpect(jsonPath("$.response.detailedSchedules").isArray())
                .andExpect(jsonPath("$.response.detailedSchedules[0].id").isNumber())
                .andExpect(jsonPath("$.response.detailedSchedules[0].seq").isNumber())
                .andExpect(jsonPath("$.response.detailedSchedules[0].title").isString())
                .andExpect(jsonPath("$.response.detailedSchedules[0].description").isString())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(document("travel/detail"))
        ;
    }

    @Test
    public void 사용자_여행상세사진리스트_가져오기() throws Exception {
        ResultActions result = mockMvc.perform(get("/travel/photos/12")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(TravelController.class))
                .andExpect(handler().methodName("travelDetailResultPhoto"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response").exists())
                .andExpect(jsonPath("$.response.travelPhotoList").isArray())
                .andExpect(jsonPath("$.response.travelPhotoList[0].imageUrl").isString())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(document("travel/photos"))
        ;
    }

    @Test
    @Transactional
    public void 사용자_여행정보_지우기() throws Exception {
        ResultActions result = mockMvc.perform(delete("/travel/5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(TravelController.class))
                .andExpect(handler().methodName("deleteTravel"))
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.response", is(true)))
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(document("travel/delete"))
        ;
    }
}
