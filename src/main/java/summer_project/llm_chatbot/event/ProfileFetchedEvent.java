package summer_project.llm_chatbot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import summer_project.llm_chatbot.dto.AuthTokenDto;

@Getter
@RequiredArgsConstructor
public class ProfileFetchedEvent {
    private final AuthTokenDto token;
}
