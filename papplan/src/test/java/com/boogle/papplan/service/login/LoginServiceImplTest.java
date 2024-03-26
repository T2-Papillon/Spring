package com.boogle.papplan.service.login;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceImplTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void 패스워드_암호화_복호화_매칭확인() {
        String password = "12345";
        List<String> hashes = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            String encrypt = passwordEncoder.encode(password);

            System.out.println("encrypt=" + encrypt);
            hashes.add(encrypt);
        }

        for (int i = 0; i < 10; i++) {
            assertTrue(passwordEncoder.matches(password, hashes.get(i)));
        }
    }
}