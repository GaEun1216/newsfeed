package com.sparta.newspeed.controller;

import com.sparta.newspeed.auth.controller.AuthController;
import com.sparta.newspeed.auth.dto.SignUpRequestDto;
import com.sparta.newspeed.auth.service.AuthService;
import com.sparta.newspeed.awss3.S3Service;
import com.sparta.newspeed.common.util.RedisUtil;
import com.sparta.newspeed.security.util.JwtUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = {AuthController.class})
class AuthControllerTest {
    private MockMvc mvc;
    private ValidatorFactory factory;
    private Validator validator;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    AuthService authService;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JavaMailSender mailSender;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    S3Service s3Service;

    @MockBean
    RedisUtil redisUtil;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @DisplayName("회원가입")
    @Test
    void test1() throws Exception {
        // given

        // when

        // then
    }

}