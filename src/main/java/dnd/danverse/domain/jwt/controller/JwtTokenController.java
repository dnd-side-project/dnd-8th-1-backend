package dnd.danverse.domain.jwt.controller;

import dnd.danverse.domain.jwt.AccessRefreshTokenDto;
import dnd.danverse.domain.jwt.service.JwtTokenReIssueService;
import dnd.danverse.global.util.CookieUtil;
import dnd.danverse.global.util.HttpHeaderUtil;
import dnd.danverse.global.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JWT 토큰 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth/")
@Slf4j
public class JwtTokenController {

  private final JwtTokenReIssueService refreshTokenReIssueService;

  @GetMapping("/jwt/refresh")
  public ResponseEntity<MessageResponse> reIssueToken(@CookieValue(name = "refreshToken") String refreshToken) {

    AccessRefreshTokenDto tokenDto = refreshTokenReIssueService.reIssueToken(refreshToken);

    MessageResponse responseDto = MessageResponse.of(HttpStatus.CREATED, "Token 재발급 완료");

    HttpHeaders headers = new HttpHeaders();
    // cookie 에 refresh token 을 저장해야 한다, 그리고 access token 을 header 에 저장해야 한다.
    CookieUtil.setRefreshCookie(headers, tokenDto.getNewRefreshToken());
    HttpHeaderUtil.setAccessToken(headers, tokenDto.getNewAccessToken());

    return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
  }



}
