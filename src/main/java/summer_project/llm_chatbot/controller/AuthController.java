package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.dto.LoginResponseDto;
import summer_project.llm_chatbot.service.SejongAuthZService;

@RequiredArgsConstructor
@RestController
public class AuthController {
    private final SejongAuthZService sejongAuthZService;

    @PostMapping("/api/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return sejongAuthZService.login(loginRequestDto.id(), loginRequestDto.password());
    }
}
