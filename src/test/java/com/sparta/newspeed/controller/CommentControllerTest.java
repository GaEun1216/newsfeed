package com.sparta.newspeed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.newspeed.Mock.UserMock;
import com.sparta.newspeed.comment.controller.CommentController;
import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.service.CommentService;
import com.sparta.newspeed.config.WebSecurityConfig;
import com.sparta.newspeed.mvc.MockSpringSecurityFilter;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // get - post - delete - update
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(    //commentcontroller만
        controllers = {CommentController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)

class CommentControllerTest implements UserMock{
    private MockMvc mvc;

    private Principal mockPrincipal; //가짜 인증이 필요해서

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CommentService commentService;

    @BeforeEach
    public void setup() {
        // mock security로 필터 설정
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    @Test
    @DisplayName("Comment 생성")
    @Order(1)
    void createComment() throws Exception {
        // given
        Long newsfeedId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("new comment");

        UserDetailsImpl testUserDetails = new UserDetailsImpl(userMock);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        String postInfo = objectMapper.writeValueAsString(requestDto);

        // when, then
        mvc.perform(post("/api/newsfeeds/" + newsfeedId + "/comments")
                        .content(postInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("Comment 조회")
    void getComments() throws Exception {
        //given
        Long newsfeedId = 1L;
        // when, then
        mvc.perform(get("/api/newsfeeds/" + newsfeedId + "/comments")
        ).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @DisplayName("Comment 수정")
    void updateComment() throws Exception {
        //given
        Long newsfeedId = 1L;
        Long commentId = 1L;

        UserDetailsImpl testUserDetails = new UserDetailsImpl(userMock);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("수정된 댓글");

        String putInfo = objectMapper.writeValueAsString(requestDto);

        // when, then
        mvc.perform(put("/api/newsfeeds/" + newsfeedId + "/comments/" + commentId)
                .content(putInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        ).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    @DisplayName("Comment 삭제")
    void deleteComment() throws Exception {
        //given
        Long newsfeedId = 1L;
        Long commentId = 1L;

        UserDetailsImpl testUserDetails = new UserDetailsImpl(userMock);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());

        // when, then
        mvc.perform(delete("/api/newsfeeds/" + newsfeedId + "/comments/"+commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        ).andExpect(status().is2xxSuccessful());
    }
}