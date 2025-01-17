package com.mysite.restaurant.kdh.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class NoticeService {

    private final WebClient webClient;

    // WebClient.Builder를 주입받아서 WebClient 인스턴스를 생성합니다.
    public NoticeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://jennysoft.kr:8080").build();
    }
    public Mono<String> getNotices() {
        return webClient
                .get()
                .uri("/board?bbsId=BBSMSTR_AAAAAAAAAAAA&pageIndex=1&searchCnd=0&searchWrd=")
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class);
    }
    public Mono<String> getDetail(String bbsId, int nttId) {
        return webClient
                .get()
                .uri("board/{bbsId}/{nttId}",bbsId,nttId)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToMono(String.class);
    }

}
