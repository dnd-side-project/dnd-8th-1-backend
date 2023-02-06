package dnd.danverse.global.redis.service;

import dnd.danverse.global.redis.dto.RefreshTokenDto;
import dnd.danverse.global.redis.repository.RefreshTokenRedisRepository;
import java.util.Optional;
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
   * @param refreshToken : Refresh Token
   * @param email : email
   */
  @Transactional
  public void saveRefreshToken(String refreshToken, String email) {
    refreshTokenRedisRepository.save(new RefreshTokenDto(refreshToken, email));
  }

  /**
   * Refresh Token 이 Redis 에 존재하는지 확인한다.
   * @param refreshToken : Refresh Token
   * @return : 존재하면 RefreshTokenDto, 존재하지 않으면 Optional.empty()
   */
  public Optional<RefreshTokenDto> isExist(String refreshToken) {
    return refreshTokenRedisRepository.findById(refreshToken);
  }
}
