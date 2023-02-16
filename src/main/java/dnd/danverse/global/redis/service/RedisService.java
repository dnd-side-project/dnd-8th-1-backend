package dnd.danverse.global.redis.service;

import static dnd.danverse.global.exception.ErrorCode.JWT_REFRESH_TOKEN_EXPIRED;

import dnd.danverse.domain.jwt.exception.JwtException;
import dnd.danverse.global.redis.dto.RefreshTokenDto;
import dnd.danverse.global.redis.repository.RefreshTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Redis 에 저장되는 객체를 관리하는 서비스.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RedisService {

  private final RefreshTokenRedisRepository refreshTokenRedisRepository;

  /**
   * Refresh Token 을 Redis 에 저장한다.
   * save 기본 동작은 ID 로 설정된 email 이라는 key 가 존재하면 update, 존재하지 않으면 insert 를 수행한다.
   */
  @Transactional
  public void saveRefreshToken(String email, String refreshToken) {
    refreshTokenRedisRepository.save(new RefreshTokenDto(email, refreshToken));
  }

  /**
   * Refresh Token 이 Redis 에 존재하는지 확인한다.
   * Redis 에 저장된 객체에서 value 값이 refresh token은 index 로 설정되어 있기 때문에
   * findByRefreshToken 으로 조회가 가능하다.
   * @param refreshToken : Refresh Token
   * @return : 존재하면 RefreshTokenDto, 존재하지 않으면 Optional.empty()
   */
  public RefreshTokenDto isRefreshTokenExist(String refreshToken) {
    return refreshTokenRedisRepository.findByRefreshToken(refreshToken)
        .orElseThrow(() -> new JwtException(JWT_REFRESH_TOKEN_EXPIRED));
  }
}
