package dnd.danverse.global.redis.service;

import static dnd.danverse.global.exception.ErrorCode.ACCESS_TOKEN_LOGOUT;

import dnd.danverse.domain.jwt.exception.JwtException;
import dnd.danverse.global.redis.dto.LogOutTokenDto;
import dnd.danverse.global.redis.repository.LogOutTokenRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Access Token 앞에 Prefix 를 붙여
 * 로그 아웃 된 토큰으로써의 역할을 하기 위해 Redis 에 저장하는 서비스.
 */
@Service
@RequiredArgsConstructor
public class RedisLogOutService {

  private final LogOutTokenRedisRepository logOutTokenRedisRepository;
  private static final String PREFIX = "BLACKLIST_";

  /**
   * 로그 아웃 된 토큰을 Redis 에 저장한다. 기존 Access Token 이 가지는 만료 시간을 그대로 사용한다.
   *
   * @param accessToken : 로그 아웃 처리를 위한 Access Token
   * @param expiration : 기존 Access Token 의 남은 만료 시간
   */
  @Transactional
  public void saveLogOutToken(String accessToken, Long expiration) {

    String key = PREFIX + accessToken;

    LogOutTokenDto logOutTokenDto = new LogOutTokenDto(key, expiration);

    logOutTokenRedisRepository.save(logOutTokenDto);
  }


  /**
   * 로그 아웃 된 토큰인지 확인한다.
   *
   * @param blackListToken : 로그 아웃 된 토큰인지 확인할 토큰
   */
  @Transactional(readOnly = true)
  public void checkIfLogOutToken(String blackListToken) {
    logOutTokenRedisRepository.findById(PREFIX + blackListToken)
        .ifPresent(logOutTokenDto -> {
          throw new JwtException(ACCESS_TOKEN_LOGOUT);
        });
  }

}
