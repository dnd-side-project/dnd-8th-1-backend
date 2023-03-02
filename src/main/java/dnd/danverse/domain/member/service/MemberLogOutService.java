package dnd.danverse.domain.member.service;

import dnd.danverse.domain.jwt.service.JwtTokenProvider;
import dnd.danverse.global.redis.service.RedisLogOutService;
import dnd.danverse.global.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 로그 아웃을 위한 복합 Service
 */
@Service
@RequiredArgsConstructor
public class MemberLogOutService {

  private final RedisService redisService;
  private final JwtTokenProvider jwtTokenProvider;
  private final RedisLogOutService redisLogOutService;

  /**
   * 로그 아웃을 할 수 있다.
   * 1. Redis 에서 Refresh Token 을 삭제한다.
   * 2. 로그 아웃을 요청한 시점의 Access Token 만료 시간을 가져온다.
   * 3. Redis 에 로그 아웃 된 토큰을 저장한다. (저장 시, 만료 시간은 Access Token 의 만료 시간을 그대로 사용한다.)
   *
   * @param accessToken : Client 로 넘어온 Access Token
   * @param email : 사용자 email
   */
  public void logout(String accessToken, String email) {
    redisService.deleteRefreshTokenByEmail(email);

    Long leftExpiration = jwtTokenProvider.getExpiration(accessToken);

    redisLogOutService.saveLogOutToken(accessToken, leftExpiration);
  }


}
