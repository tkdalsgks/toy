package com.project.toy.gpt.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.toy.gpt.dto.GptClient;

@Controller
@RequestMapping("/chat")
public class GptController {
    private final GptClient gptClient;

    public GptController(GptClient gptClient) {
        this.gptClient = gptClient;
    }
    
    @GetMapping("/gpt")
    public String gpt() {
    	return "gpt/chatgpt";
    }

    @PostMapping("/gpt")
    public String generateText(@RequestParam("prompt") String prompt,
                               @RequestParam("maxTokens") int maxTokens) throws IOException {
        // GPT-3 API를 사용하여 텍스트 생성
        String generatedText = gptClient.generateText(prompt, maxTokens);

        // 생성된 텍스트 반환
        return generatedText;
    }
}
