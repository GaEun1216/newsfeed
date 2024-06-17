package com.sparta.newspeed.entity;

import com.sparta.newspeed.user.dto.UserInfoUpdateDto;
import com.sparta.newspeed.user.entity.User;
import com.sparta.newspeed.user.entity.UserRoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    // user - entity / updateUserInfo,updateOAuth2Info,updatePassword,updateRole
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder().userSeq(1L)
                .userName("USER_NAME")
                .userId("rYkzPNb1jxYpcZu")
                .userPassword("##fDbof35uVl1oB~v9Y7Z1yC3zV$@tNaT[1nSc5vXB#!4pXdZT@7y%`")
                .userEmail("rkrk@gmail.com")
                .role(UserRoleEnum.USER)
                .build();
    }

    @DisplayName("updateUserInfo")
    @Order(1)
    @Test
    void updateUserInfo(){
        // given
        UserInfoUpdateDto dto = UserInfoUpdateDto.builder()
                .intro("intro")
                .name("update name")
                .build();

        // when
        user.updateUserInfo(dto);

        // then
        assertThat(user.getUserIntro()).isEqualTo("intro");
        assertThat(user.getUserName()).isEqualTo("update name");
    }

    @DisplayName("updateOAuth2Info")
    @Order(2)
    @Test
    void updateOAuth2Info(){
        // given
        String userName = "LeegaEun";
        String profileImageUrl = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.thesafaricollection.com%2Fgiraffes-around-the-globe%2F&psig=AOvVaw1_YaW4kKVXrMqN26v-EylX&ust=1718674993234000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCMDbz7XB4YYDFQAAAAAdAAAAABAE";

        // when
        user.updateOAuth2Info(userName,profileImageUrl);

        // then
        assertThat(user.getUserName()).isEqualTo("LeegaEun");
        assertThat(user.getProfileImageUrl()).isEqualTo("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.thesafaricollection.com%2Fgiraffes-around-the-globe%2F&psig=AOvVaw1_YaW4kKVXrMqN26v-EylX&ust=1718674993234000&source=images&cd=vfe&opi=89978449&ved=0CBAQjRxqFwoTCMDbz7XB4YYDFQAAAAAdAAAAABAE");
    }

    @DisplayName("updatePassword")
    @Order(3)
    @Test
    void updatePassword(){
        // given
        String encNewPassword = "#fDbof35uVl1oB~v9m28djakdjl2@XdZT@7y%";

        // when
        user.updatePassword(encNewPassword);

        // then
        assertThat(user.getUserPassword()).isEqualTo("#fDbof35uVl1oB~v9m28djakdjl2@XdZT@7y%");
    }

    @DisplayName("updateRole")
    @Order(4)
    @Test
    void updateRole(){
        // given
        UserRoleEnum roleEnum = UserRoleEnum.WITHDRAW;

        // when
        user.updateRole(roleEnum);

        //then
        assertThat(user.getRole()).isEqualTo(UserRoleEnum.WITHDRAW);
    }
}
