package com.project.toy.gpt.dto;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GptClient {
    private final String apiKey; // GPT-3 API 키

    public GptClient(@Value("${apiKey}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String generateText(String prompt, int maxTokens) throws IOException {
        String url = "https://api.openai.com/v1/engines/davinci-codex/completions";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost request = new HttpPost(url);
        request.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);
        request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // API 요청에 필요한 JSON 데이터 생성
        String requestData = String.format("{\"prompt\": \"%s\", \"max_tokens\": %d}", prompt, maxTokens);
        StringEntity entity = new StringEntity(requestData);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        HttpEntity responseEntity = response.getEntity();
        if (responseEntity != null) {
            String responseString = EntityUtils.toString(responseEntity);
            
            // JSON 데이터 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseString);
            String generatedText = jsonNode.get("choices").get(0).get("text").asText();

            // 파싱된 텍스트 반환
            return generatedText;
        }

        return null;
    }
}
