package com.mysite.restaurant.kdh.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class MapService {

    private final RestTemplate restTemplate;

    @Value("${api.consumer.key}")
    private String consumerKey;

    @Value("${api.consumer.secret}")
    private String consumerSecret;

    public MapService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 인증 토큰을 가져오고 데이터를 가져오는 메서드
    public String fetchDataWithToken(String address) {
        // 1. 인증 토큰을 가져오기 위한 URL 설정
        String authUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/auth/authentication.json";
        String token = null;

        try {
            // 인증 요청을 위한 파라미터 설정
            UriComponentsBuilder authBuilder = UriComponentsBuilder.fromUriString(authUrl)
                    .queryParam("consumer_key", consumerKey)
                    .queryParam("consumer_secret", consumerSecret);

            // 인증 API 호출
            ResponseEntity<String> authResponse = restTemplate.getForEntity(authBuilder.toUriString(), String.class);
            token = parseAccessToken(authResponse.getBody());

            if (token == null) {
                return "Failed to fetch access token";
            }
            System.out.print(token);
            // 2. 토큰을 이용하여 데이터 요청
            String stageUrl = "https://sgisapi.kostat.go.kr/OpenAPI3/addr/geocode.json";

            UriComponentsBuilder stageBuilder = UriComponentsBuilder.fromUriString(stageUrl)
                    .queryParam("accessToken", token)
                    .queryParam("address", address)
                    .queryParam("pagenum", 0)
                    .queryParam("resultcount",5);


            // 데이터 요청
            ResponseEntity<String> stageResponse = restTemplate.getForEntity(stageBuilder.toUriString(), String.class);

            System.out.print(stageResponse);

            return stageResponse.getBody();

        } catch (HttpClientErrorException e) {
            e.printStackTrace();
            return "Error fetching data: " + e.getMessage();
        }
    }

    // API 응답에서 accessToken 추출 (응답 형식에 맞게 처리)
    private String parseAccessToken(String responseBody) {
        try {
            // Jackson ObjectMapper를 사용하여 JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = rootNode.path("result");
            JsonNode accessTokenNode = resultNode.path("accessToken");

            // accessToken 값을 추출하여 반환
            return accessTokenNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // 파싱 오류 발생 시 null 반환
        }
    }
}
