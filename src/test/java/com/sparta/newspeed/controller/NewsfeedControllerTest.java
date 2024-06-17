package com.sparta.newspeed.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newspeed.Mock.UserMock;
import com.sparta.newspeed.comment.service.CommentService;
import com.sparta.newspeed.config.WebSecurityConfig;
import com.sparta.newspeed.mvc.MockSpringSecurityFilter;
import com.sparta.newspeed.newsfeed.controller.NewsfeedController;
import com.sparta.newspeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newspeed.newsfeed.service.NewsfeedService;
import com.sparta.newspeed.security.service.UserDetailsImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(    // NewsfeedController
        controllers = {NewsfeedController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
public class NewsfeedControllerTest implements UserMock {

    private MockMvc mvc;

    private Principal mockPrincipal; //가짜 인증이 필요해서

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    NewsfeedService newsfeedService;

    @BeforeEach
    public void setup() {
        // mock security로 필터 설정
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    @Test
    @DisplayName("Newsfeed 등록")
    @Order(1)
    void createNewsfeed() throws Exception {
        // given
        NewsfeedRequestDto requestDto = NewsfeedRequestDto.builder()
                .title("넷플팟 급9@@@@@@ 2자리")
                .content("댓글 ㄱ")
                .ottName("Netflix")
                .remainMember(2)
                .build();

        UserDetailsImpl testUserDetails = new UserDetailsImpl(userMock);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when, then
        mvc.perform(post("/api/newsfeeds")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Newsfeed 전체 조회")
    @Order(2)
    void getNewsfeeds() throws Exception{
        // given
        // 페이징 관련해서 검사해도 여기에서 하면 될듯

        // when, then
        mvc.perform(get("/api/newsfeeds")
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Newsfeed 선택 조회")
    @Order(3)
    void getNewsfeed() throws Exception{
        // given
        // 페이징 관련해서 검사해도 여기에서 하면 될듯
        Long newsfeedSeq = 1L;

        // when, then
        mvc.perform(get("/api/newsfeeds/"+newsfeedSeq)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Newsfeed 수정")
    @Order(4)
    void updateNewsfeed() throws Exception{
        // given
        Long newsfeedSeq = 1L;
        NewsfeedRequestDto requestDto = NewsfeedRequestDto.builder()
                .title("넷플 -> 왓챠로 변경")
                .content("다같이 왓챠로 갈아탑시다")
                .ottName("Watcha")
                .remainMember(1)
                .build();

        String putInfo = objectMapper.writeValueAsString(requestDto);

        UserDetailsImpl testUserDetails = new UserDetailsImpl(userMock);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());


        // when, then
        mvc.perform(put("/api/newsfeeds/"+newsfeedSeq)
                .content(putInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        ).andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Newsfeed 삭제")
    @Order(5)
    void deleteNewsfeed() throws Exception{
        // given
        Long newsfeedSeq = 1L;

        UserDetailsImpl testUserDetails = new UserDetailsImpl(userMock);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        // when, then
        mvc.perform(delete("/api/newsfeeds/"+newsfeedSeq)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        ).andExpect(status().is2xxSuccessful());
    }
}