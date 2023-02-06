package dnd.danverse.global.util;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.http.HttpHeaders;

/**
 * 구글 로그인 서비스 후, Spring 서버의 Access Token 을 Header 의 Authorization 에 담는다.
 */
public class HttpHeaderUtil {

  private HttpHeaderUtil() {
  }

  public static void setAccessToken(HttpHeaders headers, String accessToken) {
    // set access token header
    headers.set(AUTHORIZATION, "Bearer " + accessToken);
  }
}
