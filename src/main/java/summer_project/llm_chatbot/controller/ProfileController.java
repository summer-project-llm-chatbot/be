package summer_project.llm_chatbot.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import summer_project.llm_chatbot.dto.AuthTokenDto;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.dto.ProfileDto;
import summer_project.llm_chatbot.service.ProfileService;
import summer_project.llm_chatbot.service.SejongAuthZService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProfileController {
    private final ProfileService profileService;
    private final SejongAuthZService sejongAuthZService;

    @Operation(
            summary = "학사정보 가져오기 API",
            description =
                    """
                    학생의 학사정보를 가져옵니다.
                    
                    `id`는 학사정보시스템 포털의 id로 기본적으로 학번과 동일합니다.
                    
                    `password`는 학사정보시스템 포털의 비밀번호입니다.
                    """
    )
    @PostMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile(@RequestBody LoginRequestDto loginRequestDto) {
        AuthTokenDto authToken = sejongAuthZService.login(loginRequestDto.id(), loginRequestDto.password());

        ResponseEntity<String> htmlResponse = profileService.getProfile(authToken);

        ProfileDto profile = profileService.parseProfileFromHtml(htmlResponse.getBody());

        return ResponseEntity.ok(profile);
    }
}
