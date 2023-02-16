package dnd.danverse.domain.jwt.service;

import dnd.danverse.domain.jwt.AccessRefreshTokenDto;
import dnd.danverse.global.redis.service.RedisService;
import dnd.danverse.global.redis.dto.RefreshTokenDto;
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

    RefreshTokenDto refreshTokenDto = redisService.isRefreshTokenExist(refreshToken);

    String newAccessToken = jwtTokenProvider.createAccessToken(refreshTokenDto.getEmail());
    String newRefreshToken = jwtTokenProvider.createRefreshToken();
    redisService.saveRefreshToken(refreshTokenDto.getEmail(), newRefreshToken);

    return new AccessRefreshTokenDto(newAccessToken, newRefreshToken);
  }
}
