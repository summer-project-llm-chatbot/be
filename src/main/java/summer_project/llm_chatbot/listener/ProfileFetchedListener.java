package summer_project.llm_chatbot.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;
import summer_project.llm_chatbot.event.ProfileFetchedEvent;
import summer_project.llm_chatbot.service.ProfileService;

@Component
@RequiredArgsConstructor
public class ProfileFetchedListener {
    private final ProfileService profileService;

    @Async
    @EventListener
    public void handleProfileFetched(ProfileFetchedEvent event) {
        try {
            profileService.fetchAndSaveProfile(event.getToken());
        } catch (Exception e) {
            throw ApplicationException.of(ErrorCode.CRAWLING_FAILED);
        }
    }
}
