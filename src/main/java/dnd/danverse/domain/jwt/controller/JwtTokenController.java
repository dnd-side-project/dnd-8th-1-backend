package dnd.danverse.domain.jwt.controller;

import dnd.danverse.domain.jwt.AccessRefreshTokenDto;
import dnd.danverse.domain.jwt.service.JwtTokenReIssueService;
import dnd.danverse.domain.member.dto.response.MemberResponse;
import dnd.danverse.domain.oauth.service.OAuth2Service;
import dnd.danverse.global.response.DataResponse;
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
import org.springframework.web.bind.annotation.RequestHeader;
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
  private final OAuth2Service oAuth2Service;

  @GetMapping("/jwt/refresh")
  public ResponseEntity<MessageResponse> reIssueToken(@CookieValue(name = "refreshToken") String refreshToken) {

    AccessRefreshTokenDto tokenDto = refreshTokenReIssueService.reIssueToken(refreshToken);

    MessageResponse responseDto = MessageResponse.of(HttpStatus.CREATED, "Token 재발급 완료");

    // cookie 에 refresh token 을 저장해야 한다, 그리고 access token 을 header 에 저장해야 한다.
    HttpHeaders headers = CookieUtil.setRefreshCookie(tokenDto.getNewRefreshToken());

    HttpHeaderUtil.setAccessToken(headers, tokenDto.getNewAccessToken());

    return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
  }


  /**
   * 클라이언트 Request 으로부터 Header 에 있는 Authorization 에 담긴 토큰을 받아서
   * 회원 가입을 하고, JWT 토큰을 생성하여 반환한다.
   * @return Header 에 Access Token , Cookie 에 Refresh Token 을 담아서 반환한다.
   */
  @GetMapping(value = "/google/login")
  public ResponseEntity<DataResponse<MemberResponse>> oauth2Login(@RequestHeader("google-token") String googleToken) {
    return oAuth2Service.oauth2Login(googleToken);
  }


}
