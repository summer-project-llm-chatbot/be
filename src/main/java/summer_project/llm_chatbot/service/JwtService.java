package summer_project.llm_chatbot.service;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import summer_project.llm_chatbot.constant.JwtClaim;
import summer_project.llm_chatbot.dto.JwtDto;
import summer_project.llm_chatbot.entity.UserEntity;
import summer_project.llm_chatbot.error.ApplicationException;
import summer_project.llm_chatbot.error.ErrorCode;
import summer_project.llm_chatbot.repository.UserRepository;
import summer_project.llm_chatbot.util.JwtKeyProvider;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtKeyProvider jwtKeyProvider;

    private final static Long ACCESS_TOKEN_EXPIRE_MS = 60 * 60 * 1000L;
    private final static Long REFRESH_TOKEN_EXPIRE_MS = 7 * 12 * 60 * 60 * 1000L;
    private final UserRepository userRepository;

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

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 클라이언트가 헤더에 같이 보낸 jwt 검증
    // 올바른 토큰인지 검증 -> 아니라면 예외 핸들러 처리
    public void validateJwtToken(String token) {
        try{
            Jwts.parser()
                    .setSigningKey(jwtKeyProvider.getHmacKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e){
            throw ApplicationException.of(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e ){
            throw ApplicationException.of(ErrorCode.INVALID_TOKEN_FORMAT);
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                            .verifyWith((SecretKey) jwtKeyProvider.getHmacKey())    // HS256 키
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        Long userId = claims.get(JwtClaim.USER_ID.getKey(), Long.class);

        Optional<UserEntity> userDetails = userRepository.findByStudentId(String.valueOf(userId));

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                token
        );
    }

    // DB에 Refresh Token 저장
    // access token 만료시 재발급
}
