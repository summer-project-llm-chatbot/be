package summer_project.llm_chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import summer_project.llm_chatbot.dto.*;
import summer_project.llm_chatbot.entity.ProfileEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;
import summer_project.llm_chatbot.event.GradeCrawledEvent;
import summer_project.llm_chatbot.event.ProfileFetchedEvent;
import summer_project.llm_chatbot.service.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
    private final SejongAuthZService sejongAuthZService;
    private final JwtService jwtService;
    private final UserService userService;
    private final ProfileService profileService;
    private final CourseService courseService;
    private final CrawlController crawlController;
    private final ApplicationEventPublisher publisher;

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
        AuthTokenDto token = sejongAuthZService.login(loginRequestDto.id(), loginRequestDto.password());
        UserEntity user = userService.register(loginRequestDto.id());

        publisher.publishEvent(new ProfileFetchedEvent(token));

        publisher.publishEvent(new GradeCrawledEvent(
                new CrawlingLoginDto(loginRequestDto.id(), loginRequestDto.password())
        ));

        JwtDto jwtDto = jwtService.generateJwtPair(user);
        LoginResponseDto response = LoginResponseDto.of(true, jwtDto);
        return ResponseEntity.ok(response);
    }


}
