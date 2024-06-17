package com.sparta.newspeed.entity;

import com.sparta.newspeed.comment.entity.Comment;
import com.sparta.newspeed.newsfeed.dto.NewsfeedRequestDto;
import com.sparta.newspeed.newsfeed.entity.Newsfeed;
import com.sparta.newspeed.newsfeed.entity.Ott;
import com.sparta.newspeed.user.entity.User;
import com.sparta.newspeed.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NewsfeedTest {
    // newsfeed - entity / update,increase,decrease
    private User user;
    private Newsfeed newsfeed;

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
    }

    @DisplayName("Newsfeed update")
    @Order(1)
    @Test
    void updateNewsfeed(){
        // given
        Ott ott = Ott.builder()
                .ottSeq(2L)
                .ottName("Watcha")
                .price(17000)
                .maxMember(4)
                .build();

        NewsfeedRequestDto  requestDto = NewsfeedRequestDto.builder()
                .title("Update title")
                .content("Update content")
                .remainMember(1)
                .build();

        // when
        newsfeed.updateNewsfeed(requestDto,ott);

        // then
        assertThat(newsfeed.getTitle()).isEqualTo("Update title");
        assertThat(newsfeed.getContent()).isEqualTo("Update content");
        assertThat(newsfeed.getRemainMember()).isEqualTo(1);
        assertThat(newsfeed.getOtt()).isEqualTo(ott);
    }

    @DisplayName("Like increase")
    @Order(2)
    @Test
    void increaseLike(){
        // when
        newsfeed.increaseLike();

        // then
        assertThat(newsfeed.getLike()).isEqualTo(1L);
    }

    @DisplayName("Like decrease")
    @Order(3)
    @Test
    void decreaseLike(){
        // given
        newsfeed.increaseLike();

        // when
        newsfeed.decreaseLike();

        // then
        assertThat(newsfeed.getLike()).isEqualTo(0L);
    }

}
