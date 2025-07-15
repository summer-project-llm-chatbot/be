package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.dto.LoginResponseDto;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.service.SejongAuthZService;
import summer_project.llm_chatbot.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final SejongAuthZService sejongAuthZService;
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = sejongAuthZService.login(loginRequestDto.id(), loginRequestDto.password());
        if (!response.success()) {
            throw ApplicationException.of("로그인 실패", 403);
        }
        UserEntity entity = userService.register(loginRequestDto.id());
        return response;
    }
}
