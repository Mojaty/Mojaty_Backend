package com.motivation.mojaty.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
@RequestMapping("/test")
public class TestController {

    @GetMapping("/v1")
    public String test(HttpServletResponse res) {
        log.info(">>>>>>>>>>>>>test success");
        Cookie cookie = new Cookie("wow", "wow");
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        return "테스트임";
    }
}
