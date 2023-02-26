package dnd.danverse.domain.jwt.filter;

import static dnd.danverse.global.exception.ErrorCode.JWT_INVALID_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import dnd.danverse.domain.jwt.service.JwtTokenProvider;
import dnd.danverse.domain.jwt.exception.JwtException;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * 모든 요청이 해당 Filter 를 통해서 들어오지만, Header에 Authorization 이 없으면 바로 다음 필터로 넘어간다.
 * 최종적으로 securityConfig 에서 설정한 권한을 검증한다.
 * Header 에 Authorization 이 있는 경우, 토큰을 검증하고 SecurityContextHolder 에 Authentication 객체를 저장한다.
 * 객체를 저장 하더라도 최종적으로 securityConfig 에서 설정한 권한을 검증한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider tokenProvider;

  private static final List<String> EXCLUDE_URL = List.of(
      "/api/v1/member/oauth/google/login");



  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {

    // header 에서 Authorization 이 없으면 바로 다음 필터로 넘어간다. 최종적으로 securityConfig 에서 설정한 권한을 검증한다.
    String header = request.getHeader(AUTHORIZATION);
    if (hasAuthorization(header)) {
      String accessToken = header.substring(7);

      tokenProvider.validateToken(accessToken);
      Authentication authentication = tokenProvider.getAuthentication(accessToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      log.info("SecurityContextHolder 에 Authentication 객체를 저장했습니다. 인증 완료 {}",
          authentication.getName());

    }

    filterChain.doFilter(request, response);
  }

  /**
   * Header 에 Authorization 이 있는지 확인한다.
   * @param header Authorization Header
   * @return Authorization Header 가 있는 경우 true, 없는 경우 false
   */
  private boolean hasAuthorization(String header) {
    return header != null && parseBearerToken(header);
  }

  /**
   * Header 에 있는 리소스를 위한 토큰을 가져오는 행동을 한다.
   *
   * @param bearerToken Client 로 넘어온 Authorization Header 에 있는 Bearer 를 포함하는 토큰
   * @return Authorization header 에서 Bearer 뒤에 있는 토큰을 반환한다.
   */
  private boolean parseBearerToken(String bearerToken) {

    // 비어 있는 경우, Bearer 로 시작하지 않는 경우, Bearer 뒤에 아무것도 없는 경우
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")
        && bearerToken.length() >= 8) {
      return true;
    }
    throw new JwtException(JWT_INVALID_TOKEN);
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
  }

}
