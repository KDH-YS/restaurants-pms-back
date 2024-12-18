package com.mysite.restaurant.kdh.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MapService {

    private final WebClient webClient;

    @Value("${api.consumer.key}")
    private String consumerKey;

    @Value("${api.consumer.secret}")
    private String consumerSecret;

    public MapService(WebClient.Builder webClientBuilder) {
        // WebClient.Builder로 WebClient 인스턴스 생성
        this.webClient = webClientBuilder.baseUrl("https://sgisapi.kostat.go.kr").build();
    }

    // 인증 토큰을 가져오고 데이터를 가져오는 메서드
    public Mono<String> fetchDataWithToken(String address) {
        String authUrl = "/OpenAPI3/auth/authentication.json";

        // 1. 인증 토큰 요청
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(authUrl)
                        .queryParam("consumer_key", consumerKey)
                        .queryParam("consumer_secret", consumerSecret)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    String token = parseAccessToken(response);
                    if (token == null) {
                        return Mono.error(new RuntimeException("Failed to fetch access token"));
                    }
                    System.out.println("Token: " + token);

                    // 2. 토큰을 이용하여 데이터 요청
                    return fetchStageData(token, address);
                });
    }

    // 토큰을 이용하여 stage 데이터를 가져오는 메서드
    private Mono<String> fetchStageData(String token, String address) {
        String stageUrl = "/OpenAPI3/addr/geocode.json";

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(stageUrl)
                        .queryParam("accessToken", token)
                        .queryParam("address", address)
                        .queryParam("pagenum", 0)
                        .queryParam("resultcount", 5)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Stage Data: " + response))
                .onErrorResume(e -> Mono.just("Error fetching data: " + e.getMessage()));
    }

    // API 응답에서 accessToken 추출
    private String parseAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = rootNode.path("result");
            JsonNode accessTokenNode = resultNode.path("accessToken");

            return accessTokenNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
