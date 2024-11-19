package com.mysite.restaurant.kdh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 홈 페이지 요청을 처리하는 메서드
    @GetMapping("/")
    public String home() {
        // 홈 페이지 로직 처리
        return "home"; // home.html 페이지를 반환
    }

    // 검색 요청을 처리하는 메서드
    @GetMapping("/search")
    public String search() {
        // 검색 페이지 로직 처리
        return "search"; // search.html 페이지를 반환
    }
}