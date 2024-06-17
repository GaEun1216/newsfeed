package com.sparta.newspeed.entity;

import com.sparta.newspeed.comment.dto.CommentRequestDto;
import com.sparta.newspeed.comment.entity.Comment;
import com.sparta.newspeed.newsfeed.entity.Newsfeed;
import com.sparta.newspeed.newsfeed.entity.Ott;
import com.sparta.newspeed.user.entity.User;
import com.sparta.newspeed.user.entity.UserRoleEnum;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentTest {
    // comment - entity / update,increase,decrease
    private User user;
    private Newsfeed newsfeed;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = User.builder().userSeq(1L)
                .userName("USER_NAME")
                .userId("rYkzPNb1jxYpcZu")
                .userPassword("##fDbof35uVl1oB~v9Y7Z1yC3zV$@tNaT[1nSc5vXB#!4pXdZT@7y%`")
                .userEmail("rkrk@gmail.com")
                .role(UserRoleEnum.USER)
                .build();

        Ott ott = Ott.builder()
                .ottSeq(1L)
                .ottName("Netflix")
                .price(17000)
                .maxMember(4)
                .build();

        newsfeed = Newsfeed.builder()
                .newsFeedSeq(1L)
                .title("제목").content("CONTENT")
                .remainMember(2)
                .like(0L).user(user).ott(ott)
                .build();

        comment = Comment.builder()
                .commentSeq(1L)
                .content("Content")
                .like(0L)
                .user(user)
                .newsfeed(newsfeed)
                .build();
    }

    @DisplayName("Comment update")
    @Order(1)
    @Test
    void updateComment(){
        // given
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("수정 댓글");

        // when
        comment.update(requestDto);

        // then

        assertThat(comment.getContent()).isEqualTo("수정 댓글");
    }

    @DisplayName("Like increase")
    @Order(2)
    @Test
    void increaseLike(){
        // when
        comment.increaseLike();

        // then
        assertThat(comment.getLike()).isEqualTo(1L);
    }

    @DisplayName("Like decrease")
    @Order(3)
    @Test
    void decreaseLike(){
        // given
        comment.increaseLike();

        // when
        comment.decreaseLike();

        // then
        assertThat(comment.getLike()).isEqualTo(0L);
    }
}
