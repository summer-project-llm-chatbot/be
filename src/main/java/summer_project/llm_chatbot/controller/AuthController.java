package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summer_project.llm_chatbot.dto.JwtDto;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.dto.LoginResponseDto;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.service.JwtService;
import summer_project.llm_chatbot.service.SejongAuthZService;
import summer_project.llm_chatbot.service.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final SejongAuthZService sejongAuthZService;
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        boolean isLoginSuccess = sejongAuthZService.login(loginRequestDto.id(), loginRequestDto.password());
        if (!isLoginSuccess) {
            throw ApplicationException.of("로그인 실패", 403);
        }
        UserEntity user = userService.register(loginRequestDto.id());
        JwtDto jwtDto = jwtService.generateJwtPair(user);

        LoginResponseDto response = LoginResponseDto.of(true, jwtDto);
        return ResponseEntity.ok(response);
    }


}
