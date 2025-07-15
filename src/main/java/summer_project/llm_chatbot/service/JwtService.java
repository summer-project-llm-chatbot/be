package summer_project.llm_chatbot.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import summer_project.llm_chatbot.dto.JwtDto;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.util.JwtKeyProvider;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtKeyProvider jwtKeyProvider;

    private final static Long ACCESS_TOKEN_EXPIRE_MS = 60 * 60 * 1000L;
    private final static Long REFRESH_TOKEN_EXPIRE_MS = 7 * 12 * 60 * 60 * 1000L;

    public String generateAccessToken(UserEntity user) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_MS);
        String accessToken = Jwts.builder()
                                 .claim("role", "user")
                                 .claim("userId", user.getId())
                                 .issuedAt(now)
                                 .expiration(expiredAt)
                                 .signWith(jwtKeyProvider.getHmacKey(), SignatureAlgorithm.HS256)
                                 .compact();

        return accessToken;
    }

    public String generateRefreshToken(UserEntity user) {
        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_MS);
        String refreshToken = Jwts.builder()
                                  .claim("userId", user.getId())
                                  .issuedAt(now)
                                  .expiration(expiredAt)
                                  .signWith(jwtKeyProvider.getHmacKey(), SignatureAlgorithm.HS256)
                                  .compact();
        return refreshToken;
    }

    public JwtDto generateJwtPair(UserEntity user) {
        String accessToken = generateAccessToken(user);
        String refreshToken = generateRefreshToken(user);
        return JwtDto.of(accessToken, refreshToken);
    }

    // jwt 생성
    // 클라이언트가 헤더에 같이 보낸 jwt 검증

}
