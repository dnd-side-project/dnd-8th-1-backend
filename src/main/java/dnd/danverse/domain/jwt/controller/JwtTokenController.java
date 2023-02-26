package dnd.danverse.domain.jwt.controller;

import dnd.danverse.domain.jwt.AccessRefreshTokenDto;
import dnd.danverse.domain.jwt.service.JwtTokenReIssueService;
import dnd.danverse.global.util.CookieUtil;
import dnd.danverse.global.util.HttpHeaderUtil;
import dnd.danverse.global.response.MessageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "JwtTokenController : JWT 토큰 재발급 관련 API")
@Slf4j
public class JwtTokenController {

  private final JwtTokenReIssueService refreshTokenReIssueService;

  @GetMapping("/jwt/refresh")
  @ApiOperation(value = "JWT 토큰 재발급", notes = "JWT 토큰 재 발급을 할 수 있다.")
  public ResponseEntity<MessageResponse> reIssueToken(@CookieValue(name = "refreshToken")
   @ApiParam(value = "refresh token 을 cookie 에 'refreshToken' key 로 갖는 value 로 받는다.") String refreshToken) {

    AccessRefreshTokenDto tokenDto = refreshTokenReIssueService.reIssueToken(refreshToken);

    MessageResponse responseDto = MessageResponse.of(HttpStatus.CREATED, "Token 재발급 완료");

    HttpHeaders headers = new HttpHeaders();
    // cookie 에 refresh token 을 저장해야 한다, 그리고 access token 을 header 에 저장해야 한다.
    CookieUtil.setRefreshCookie(headers, tokenDto.getNewRefreshToken());
    HttpHeaderUtil.setAccessToken(headers, tokenDto.getNewAccessToken());

    return new ResponseEntity<>(responseDto, headers, HttpStatus.CREATED);
  }



}
