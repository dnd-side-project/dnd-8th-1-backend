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
   * sameSite 설정을 위해서 secure 속성도 함께 가져가야 한다.
   * https://velog.io/@pjh612/JWTCORS-%EC%9D%B4%EC%8A%88-%EC%83%81%ED%99%A9%EC%97%90%EC%84%9C-%EC%BF%A0%ED%82%A4%EC%A0%84%EB%8B%AC
   *
   * @param refreshToken 서버에서 생성한 Refresh Token
   */
  public static void setRefreshCookie(HttpHeaders httpHeaders, String refreshToken) {

    ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
        .httpOnly(true)
        .secure(true)
        .maxAge(REFRESH_TOKEN_EXPIRE_LENGTH_MS)
        .path("/")
        .sameSite("None")
        .build();

    httpHeaders.add(HttpHeaders.SET_COOKIE, cookie.toString());
  }
}
