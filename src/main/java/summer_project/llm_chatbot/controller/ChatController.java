package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import summer_project.llm_chatbot.dto.ChatRequestDto;
import summer_project.llm_chatbot.dto.ChatResponseDto;
import summer_project.llm_chatbot.dto.ChatHistoryDto;
import summer_project.llm_chatbot.dto.ConversationDto;
import summer_project.llm_chatbot.dto.ProfileDto;
import summer_project.llm_chatbot.entity.ChatLogEntity;
import summer_project.llm_chatbot.entity.ConversationEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.service.AIService;
import summer_project.llm_chatbot.service.ChatLogService;
import summer_project.llm_chatbot.service.ConversationService;
import summer_project.llm_chatbot.service.UserQueryService;
import summer_project.llm_chatbot.service.UserService;
import summer_project.llm_chatbot.dto.ProfileDto;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final UserQueryService userQueryService;
    private final ConversationService conversationService;
    private final ChatLogService chatLogService;
    private final AIService aiService;
    private final UserService userService;

    UserEntity user;
    ConversationEntity conversation;

    // [1] 질문 보내기
    @PostMapping
    public ResponseEntity<ChatResponseDto> askQuestion(@RequestBody ChatRequestDto request) {
        // 금칙어 필터링
        System.out.println("======요청도착====== ");
        System.out.println("입력질문: " + request.getQuestion());
        if (containsForbiddenWords(request.getQuestion()) == true) {
            System.out.println("금칙어 포함 질문 감지됨. AI 호출 없이 종료.");
            return ResponseEntity.badRequest()
                    .body(new ChatResponseDto("금칙어가 포함된 질문입니다.", "다시 질문해주세요", null, null));
        }
        // 1. 기존 대화 불러오기
        if (request.getConversationId() == null) {
            user = userService.getByStudentId(request.getStudentId());
            conversation = conversationService.createConversation(user);
        } else {
            conversation = conversationService.getById(request.getConversationId());
        }
        ProfileDto profile = userQueryService.getProfile(request.getStudentId());
        String major = profile.major();
        // 2. AI에게 질문
        String answer = aiService.ask(request.getQuestion(), request.getStudentId(), major);

        // 3. DB 저장
        ChatLogEntity savedLog = chatLogService.save(conversation, request.getQuestion(), answer);

        // 대화 제목 설정 (첫 질문일 경우)
        conversationService.setTitleIfEmpty(conversation, request.getQuestion());

        // 4. 응답 반환
        ChatResponseDto response = new ChatResponseDto(
                savedLog.getQuestion(), savedLog.getAnswer(), savedLog.getCreatedAt(), conversation.getId());
        return ResponseEntity.ok(response);
    }

    private boolean containsForbiddenWords(String input) {
        if (input == null || input.isBlank())
            return false;

        // String normalized = input.replaceAll("[^가-힣a-zA-Z0-9]", ""); // 특수문자 제거
        String normalized = input.replaceAll("[^가-힣a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ]", "");
        System.out.println("normalized: '" + normalized + "'");
        String[] forbiddenPatterns = {
                "^ㅋ{2,}$", "^ㅎ{2,}$", // 반복 웃음
                "ㅅㅂ", "씨발", "시발", "병신", "ㅄ", "ㄲㅈ", "ㅈㄴ", "좆", "fuck", "shit", "개새", "좃", "ㅆㅂ"
        };

        return java.util.Arrays.stream(forbiddenPatterns)
                .anyMatch(pattern -> java.util.regex.Pattern.compile(pattern)
                        .matcher(normalized).find());
    }

    // [2] 유저 대화 목록 조회
    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getUserConversations(@RequestParam String studentId) {
        List<ConversationEntity> conversations = conversationService.getConversationsByStudentId(studentId);
        List<ConversationDto> result = conversations.stream()
                .map(convo -> new ConversationDto(convo.getId(), convo.getTitle(), convo.getStartedAt())).toList();
        return ResponseEntity.ok(result);
    }

    // [3] 대화 로그 조회
    @GetMapping("/history")
    public ResponseEntity<ChatHistoryDto> getChatLogs(@RequestParam Long conversationId
    // ,@RequestParam String studentId
    ) {
        // // 1. 대화 조회
        // ConversationEntity conversation =
        // conversationService.getById(conversationId);

        // // 2. 학생 소유 여부 확인
        // if (!conversation.getUser().getStudentId().equals(studentId)) {
        // throw new IllegalArgumentException("이 대화는 해당 학생의 것이 아닙니다.");
        // }
        List<ChatLogEntity> logs = chatLogService.getLogsByConversationId(conversationId);
        List<ChatResponseDto> chatDtos = logs.stream()
                .map(log -> new ChatResponseDto(log.getQuestion(), log.getAnswer(), log.getCreatedAt(),
                        log.getConversation().getId()))
                .toList();
        return ResponseEntity.ok(new ChatHistoryDto(chatDtos));
    }

}
