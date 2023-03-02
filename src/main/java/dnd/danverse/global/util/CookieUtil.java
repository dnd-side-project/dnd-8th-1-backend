package dnd.danverse.global.util;

import static dnd.danverse.domain.jwt.service.JwtTokenProvider.REFRESH_TOKEN_EXPIRE_LENGTH_MS;


import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;


/**
 * Cookie 관련 유틸리티 클래스.
 */
public class CookieUtil {

  private CookieUtil() {
    throw new IllegalStateException("Utility class");
  }

  /**
   * Header 에 Refresh Token Cookie 를 추가한다.
   * httpOnly(true)
   * -> httpOnly 속성이 true로 설정된 쿠키는 자바스크립트를 통해 접근할 수 없습니다.
   * -> XSS(Cross-Site Scripting) 공격을 할 때 쿠키를 훔치는 것을 방지하기 위한 보안 기능입니다.
   * secure(true)
   * -> secure 속성이 true로 설정된 쿠키는 HTTPS 프로토콜을 사용하여만 전송되어야 합니다.
   * -> HTTP 환경에서는 이 쿠키에 접근할 수 없습니다.\
   * -> 이 속성을 사용할 경우, 백엔드 서버는 SSL 을 적용해야 한다.
   * sameSite("None")
   * -> sameSite=None: 쿠키가 모든 사이트에서 요청이 왔을 때 전송됩니다.
   * -> 즉, 쿠키의 사용에 제약이 없습니다. 그러나 이 경우에는 반드시 secure 속성을 true 로 설정해야 합니다.
   *
   * @param refreshToken 서버에서 생성한 Refresh Token
   */
  public static void setRefreshCookie(HttpHeaders httpHeaders, String refreshToken) {

    ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
//        .secure(true)
        .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH_MS)
        .path("/")
//        .sameSite("None")
        .build();

    httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  public static void resetRefreshToken(HttpHeaders headers) {
    ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
        .maxAge(0)
        .build();

    headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
  }
}
