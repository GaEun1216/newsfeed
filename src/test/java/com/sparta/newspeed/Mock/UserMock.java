package com.sparta.newspeed.Mock;

import com.sparta.newspeed.user.entity.User;
import com.sparta.newspeed.user.entity.UserRoleEnum;

public interface UserMock {
    User userMock = User.builder().userSeq(1L)
                .userName("USER_NAME")
                .userId("rYkzPNb1jxYpcZu")
                .userPassword("##fDbof35uVl1oB~v9Y7Z1yC3zV$@tNaT[1nSc5vXB#!4pXdZT@7y%`")
                .userEmail("rkrk@gmail.com")
                .role(UserRoleEnum.USER)
                .build();
}
