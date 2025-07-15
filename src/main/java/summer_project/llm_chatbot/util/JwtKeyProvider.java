package summer_project.llm_chatbot.util;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;

@Component
public class JwtKeyProvider {
    @Value("${jwt.secret-key}")
    private String plainSecret;

    @Getter
    private Key hmacKey;

    @Getter
    private String base64Secret;

    @PostConstruct
    private void init() {
        base64Secret = Encoders.BASE64.encode(plainSecret.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.hmacKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * RSA 키쌍 생성
     */
    public KeyPair generateRsaKeyPair() {
        return Keys.keyPairFor(SignatureAlgorithm.RS256);
    }
}
