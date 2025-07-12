package summer_project.llm_chatbot.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public final class RestTemplateUtil {
    public static ResponseEntity<String> postForm(
            RestTemplate restTemplate,
            String url,
            Map<String, String> formData,
            HttpHeaders headers
    ) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        formData.forEach(form::add);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(form, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }

    public static Map<String, String> parseCookies(List<String> setCookieHeaders) {
        if (setCookieHeaders == null) {
            return Collections.emptyMap();
        }
        return setCookieHeaders.stream()
                               .map(header -> header.split(";", 2)[0])
                               .map(kv -> kv.split("=", 2))
                               .filter(kv -> kv.length == 2)
                               .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
    }
}


