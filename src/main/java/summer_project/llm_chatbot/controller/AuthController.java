package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import summer_project.llm_chatbot.dto.LoginRequest;
import summer_project.llm_chatbot.dto.LoginResponse;
import summer_project.llm_chatbot.service.SejongAuthZService;
import summer_project.llm_chatbot.util.RestTemplateUtil;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final SejongAuthZService sejongAuthZService;

    @PostMapping("/api/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return sejongAuthZService.login(loginRequest.getId(), loginRequest.getPassword());
    }
}
