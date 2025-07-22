package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final static Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Transactional
    public UserEntity register(String studentId){
        UserEntity user = userRepository.findByStudentId(studentId).orElseGet(() -> {
            UserEntity entity = UserEntity.builder().studentId(studentId).build();
            UserEntity savedUser = userRepository.save(entity);
            logger.info("사용자 신규 가입, 학번: {}", studentId);
            return savedUser;
        });

        return user;
    }

    // getUserProfile

    // getUser수강과목
}
