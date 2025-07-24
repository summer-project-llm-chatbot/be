package summer_project.llm_chatbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import summer_project.llm_chatbot.dto.ChatRequestDto;
import summer_project.llm_chatbot.dto.ChatResponseDto;
import summer_project.llm_chatbot.dto.ChatHistoryDto;
import summer_project.llm_chatbot.dto.ConversationDto;
import summer_project.llm_chatbot.entity.ChatLogEntity;
import summer_project.llm_chatbot.entity.ConversationEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.service.AIService;
import summer_project.llm_chatbot.service.ChatLogService;
import summer_project.llm_chatbot.service.ConversationService;
import summer_project.llm_chatbot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ConversationService conversationService;
    private final ChatLogService chatLogService;
    private final AIService aiService;
    private final UserService userService;

    UserEntity user;
    ConversationEntity conversation;

    // [1] 질문 보내기
    @PostMapping
    public ResponseEntity<ChatResponseDto> askQuestion(@RequestBody ChatRequestDto request) {
        // 1. 기존 대화 불러오기
        if (request.getConversationId() == null) {
            user = userService.getById(request.getUserId());
            conversation = conversationService.createConversation(user);
        } else {
            conversation = conversationService.getById(request.getConversationId());
        }

        // 2. AI에게 질문
        String answer = aiService.ask(request.getQuestion());

        // 3. DB 저장
        ChatLogEntity savedLog = chatLogService.save(conversation, request.getQuestion(), answer);

        // 대화 제목 설정 (첫 질문일 경우)
        conversationService.setTitleIfEmpty(conversation, request.getQuestion());

        // 4. 응답 반환
        ChatResponseDto response = new ChatResponseDto(
                savedLog.getQuestion(), savedLog.getAnswer(), savedLog.getCreatedAt(), conversation.getId());
        return ResponseEntity.ok(response);
    }

    // [2] 유저 대화 목록 조회
    @GetMapping("/conversations")
    public ResponseEntity<List<ConversationDto>> getUserConversations(@RequestParam Long userId) {
        List<ConversationEntity> conversations = conversationService.getConversationsByUserId(userId);
        List<ConversationDto> result = conversations.stream()
                .map(convo -> new ConversationDto(convo.getId(), convo.getTitle(), convo.getStartedAt())).toList();
        return ResponseEntity.ok(result);
    }

    // [3] 대화 로그 조회
    @GetMapping("/history")
    public ResponseEntity<ChatHistoryDto> getChatLogs(@RequestParam Long conversationId) {
        List<ChatLogEntity> logs = chatLogService.getLogsByConversationId(conversationId);
        List<ChatResponseDto> chatDtos = logs.stream()
                .map(log -> new ChatResponseDto(log.getQuestion(), log.getAnswer(), log.getCreatedAt(),
                        log.getConversation().getId()))
                .toList();
        return ResponseEntity.ok(new ChatHistoryDto(chatDtos));
    }

}
