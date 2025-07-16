package summer_project.llm_chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import summer_project.llm_chatbot.error.ErrorCode;
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

    @Operation(
            summary = "학사정보 연계 로그인 API",
            description =
    """
    학사정보 연계 로그인을 수행합니다.
    
    `id`는 학사정보시스템 포털의 id로 기본적으로 학번과 동일합니다.
    
    `password`는 학사정보시스템 포털의 비밀번호입니다.
    """
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        boolean isLoginSuccess = sejongAuthZService.login(loginRequestDto.id(), loginRequestDto.password());
        if (!isLoginSuccess) {
            throw ApplicationException.of(ErrorCode.LOGIN_FAILED);
        }
        UserEntity user = userService.register(loginRequestDto.id());
        JwtDto jwtDto = jwtService.generateJwtPair(user);

        LoginResponseDto response = LoginResponseDto.of(true, jwtDto);
        return ResponseEntity.ok(response);
    }


}
