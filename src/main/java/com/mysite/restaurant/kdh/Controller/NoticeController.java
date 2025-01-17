package com.mysite.restaurant.kdh.Controller;

import com.mysite.restaurant.kdh.Service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @GetMapping
    public Mono<String> getBoardList() {
        return noticeService.getNotices();
    }
    @GetMapping("/detail")
    public Mono<String> getBoardDetails(@RequestParam String bbsId,@RequestParam int nttId) {
        return noticeService.getDetail(bbsId,nttId);
    }
//    @GetMapping("/detail")
//    public ResponseEntity<?> noticeDetail(@RequestParam int bbsId, @RequestParam int nttId){
//        try {
//            Mono<Map<String, Object>> detail = noticeService.getDetails(bbsId,nttId);
//            return ResponseEntity.ok(detail);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("API 호출 중 오류 발생: " + e.getMessage());
//        }
//
//    }
}