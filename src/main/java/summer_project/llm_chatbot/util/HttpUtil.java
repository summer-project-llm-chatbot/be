package summer_project.llm_chatbot.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public final class HttpUtil {
    static public Map<String, String> parseCookies(HttpHeaders headers) {
        List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);
        if (cookies == null || cookies.isEmpty()) {
            return Collections.emptyMap();
        }

        return cookies.stream()
                      // "NAME=VALUE; Path=/; HttpOnly" → "NAME=VALUE"
                      .map(cookieHeader -> cookieHeader.split(";", 2)[0])
                      // "NAME=VALUE" → ["NAME","VALUE"]
                      .map(nameValue -> nameValue.split("=", 2))
                      // 잘못된 포맷 건너뛰기
                      .filter(parts -> parts.length == 2)
                      // Map<이름, 값> 으로 수집 (중복 이름은 나중 값으로 덮어쓰기)
                      .collect(Collectors.toMap(
                              parts -> parts[0],
                              parts -> parts[1],
                              (first, second) -> second
                      ));
    }
}
