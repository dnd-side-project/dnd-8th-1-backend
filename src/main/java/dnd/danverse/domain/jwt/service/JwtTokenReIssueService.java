package dnd.danverse.domain.jwt.service;

import static dnd.danverse.global.exception.ErrorCode.JWT_REFRESH_TOKEN_EXPIRED;

import dnd.danverse.domain.jwt.AccessRefreshTokenDto;
import dnd.danverse.domain.jwt.exception.JwtException;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.repository.MemberRepository;
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
  private final MemberRepository memberRepository;

  /**
   * Refresh Token 을 이용하여 Access Token 과 Refresh Token 을 재발급 받는다.
   * @param refreshToken : Client 로 넘어온 Refresh Token
   * @return : Access Token 과 Refresh Token 을 담은 AccessRefreshTokenDto
   */
  public AccessRefreshTokenDto reIssueToken(String refreshToken) {

    jwtTokenProvider.validateToken(refreshToken);

    Optional<RefreshTokenDto> refreshTokenDto = redisService.isRefreshTokenExist(refreshToken);

    // redis 에 저장된 refresh token 이 없으면 만료된 것으로 간주한다.
    if (refreshTokenDto.isEmpty()) {
      throw new JwtException(JWT_REFRESH_TOKEN_EXPIRED);
    }

    String email = refreshTokenDto.get().getEmail();

    Member member = memberRepository.findByEmail(email).get();

    String newAccessToken = jwtTokenProvider.createAccessToken(email, member.getRole());
    String newRefreshToken = jwtTokenProvider.createRefreshToken();
    redisService.saveRefreshToken(email, newRefreshToken);

    return new AccessRefreshTokenDto(newAccessToken, newRefreshToken);
  }
}
