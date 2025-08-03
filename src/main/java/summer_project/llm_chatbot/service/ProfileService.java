package summer_project.llm_chatbot.service;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import summer_project.llm_chatbot.constant.AuthEndpoint;
import summer_project.llm_chatbot.constant.ProfileEndpoint;
import summer_project.llm_chatbot.constant.ProfileField;
import summer_project.llm_chatbot.dto.AuthTokenDto;
import summer_project.llm_chatbot.dto.LoginRequestDto;
import summer_project.llm_chatbot.dto.ProfileDto;
import summer_project.llm_chatbot.entity.ProfileEntity;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;
import summer_project.llm_chatbot.repository.ProfileRepository;
import summer_project.llm_chatbot.repository.UserRepository;
import summer_project.llm_chatbot.util.HttpUtil;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> getProfile(AuthTokenDto authToken){
        RestTemplate restTemplate = new RestTemplate();
        String profileUrl = ProfileEndpoint.PROFILE.url();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, HttpUtil.toCookieHeader(authToken.toMap()));

        HttpEntity<String> request = new HttpEntity<>(headers);

        return restTemplate.exchange(
                profileUrl,
                HttpMethod.GET,
                request,
                String.class
        );
    }

    @Transactional
    public ProfileDto parseProfileFromHtml(String html) {
        String TABLE_ROWS_SELECTOR = "table.b-board-table tbody tr";

        try {
            Document doc = Jsoup.parse(html);
            Elements rows = doc.select(TABLE_ROWS_SELECTOR);
            Map<String, String> profileData = rows.stream()
                                                  .collect(Collectors.toMap(
                                                          row -> row.select("th.td-left").text().trim(),
                                                          row -> row.select("td.td-left").text().trim(),
                                                          (oldVal, newVal) -> newVal
                                                  ));

            if (profileData.isEmpty()) {
                throw ApplicationException.of(ErrorCode.PROFILE_PARSE_FAILED);
            }

            String major = profileData.getOrDefault(ProfileField.MAJOR.getLabel(), "");
            String studentCode = profileData.getOrDefault(ProfileField.STUDENT_CODE.getLabel(), "");
            String name = profileData.getOrDefault(ProfileField.NAME.getLabel(), "");
            int grade = parseInt(profileData.getOrDefault(ProfileField.GRADE.getLabel(), "-1"), -1);
            String userStatus = profileData.getOrDefault(ProfileField.USER_STATUS.getLabel(), "");
            int totalSemesters = parseSemester(profileData.getOrDefault(ProfileField.TOTAL_SEMESTERS.getLabel(), "0"));
            int readingVerifiedSemesters = parseSemester(profileData.getOrDefault(ProfileField.VERIFIED_SEMESTERS.getLabel(), "0"));
            String readingCertification = profileData.getOrDefault(ProfileField.CERTIFICATION.getLabel(), "");

            return ProfileDto.of(
                    major,
                    studentCode,
                    name,
                    grade,
                    userStatus,
                    totalSemesters,
                    readingVerifiedSemesters,
                    readingCertification
            );

        } catch (Exception e) {
            throw ApplicationException.of(ErrorCode.PROFILE_IO_FAILED);
        }
    }

    @Transactional
    public ProfileDto fetchAndSaveProfile(AuthTokenDto token) {
        String html = getProfile(token).getBody();
        ProfileDto dto = parseProfileFromHtml(html);
        saveProfileEntity(dto, dto.studentCode());
        return dto;
    }

    private void saveProfileEntity(ProfileDto dto, String studentCode) {
        UserEntity user = userRepository.findByStudentId(studentCode)
                                        .orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. 학번: " + studentCode));

        profileRepository.findByStudentCode(dto.studentCode())
                         .orElseGet(() -> profileRepository.save(ProfileEntity.builder()
                                                                              .studentCode(dto.studentCode())
                                                                              .major(dto.major())
                                                                              .name(dto.name())
                                                                              .grade(dto.grade())
                                                                              .userStatus(dto.userStatus())
                                                                              .totalSemesters(dto.totalSemesters())
                                                                              .readingVerifiedSemesters(dto.readingVerifiedSemesters())
                                                                              .readingCertification(dto.readingCertification())
                                                                              .user(user)
                                                                              .build()));
    }

    private int parseInt(String raw, int defaultValue) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private int parseSemester(String raw) {
        try {
            return Integer.parseInt(raw.split(" ")[0]);
        } catch (Exception ex) {
            return -1;
        }
    }
}
