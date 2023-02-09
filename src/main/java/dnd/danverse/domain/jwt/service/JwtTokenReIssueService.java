package dnd.danverse.domain.jwt.service;

import dnd.danverse.domain.jwt.AccessRefreshTokenDto;
import dnd.danverse.global.redis.service.RedisService;
import dnd.danverse.global.redis.dto.RefreshTokenDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Refresh Token 을 이용하여 Access Token 과 Refresh Token 을 재발급 받는 Service
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtTokenReIssueService {

  private final JwtTokenProvider jwtTokenProvider;
  private final RedisService redisService;

  /**
   * Refresh Token 을 이용하여 Access Token 과 Refresh Token 을 재발급 받는다.
   * @param refreshToken : Client 로 넘어온 Refresh Token
   * @return : Access Token 과 Refresh Token 을 담은 AccessRefreshTokenDto
   */
  public AccessRefreshTokenDto reIssueToken(String refreshToken) {

    jwtTokenProvider.validateToken(refreshToken);

    Optional<RefreshTokenDto> tokenDto = redisService.isRefreshTokenExist(refreshToken);

    String newAccessToken;
    String newRefreshToken;
    // Refresh Token 이 존재하면, 새로운 Access Token 과 Refresh Token 을 발급한다.
    if (tokenDto.isPresent()) {
      String email = tokenDto.get().getEmail();
      newAccessToken = jwtTokenProvider.createAccessToken(email);
      newRefreshToken = jwtTokenProvider.createRefreshToken();
      redisService.saveRefreshToken(email, newRefreshToken);
    } else {
      // Refresh Token 이 존재하지 않으면, Refresh Token 이 Redis 에서도 만료 됨을 의미, 그 만큼(1주일) 오랜 기간 접속하지 않았다.
      throw new IllegalArgumentException("Refresh Token 이 존재하지 않습니다. 새롭게 로그인 해주세요");
    }

    return new AccessRefreshTokenDto(newAccessToken, newRefreshToken);
  }
}
