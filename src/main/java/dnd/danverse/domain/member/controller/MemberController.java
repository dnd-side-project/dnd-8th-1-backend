package dnd.danverse.domain.member.controller;

import dnd.danverse.global.response.DataResponse;
import dnd.danverse.domain.member.dto.response.MemberResponse;
import dnd.danverse.domain.oauth.service.OAuth2Service;
import dnd.danverse.domain.jwt.service.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서비스 사용자 관련 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

  private final OAuth2Service oAuth2Service;

  /**
   * 클라이언트 Request 으로부터 Header 에 있는 Authorization 에 담긴 토큰을 받아서
   * 회원 가입을 하고, JWT 토큰을 생성하여 반환한다.
   * @return Header 에 Access Token , Cookie 에 Refresh Token 을 담아서 반환한다.
   */
  @GetMapping(value = "/oauth/google/login")
  public ResponseEntity<DataResponse<MemberResponse>> oauth2Login(@RequestHeader("google-token") String googleToken) {
    return oAuth2Service.oauth2Login(googleToken);
  }


  @GetMapping("resource")
  public ResponseEntity<String> resource(@AuthenticationPrincipal SessionUser user) {

    String email = user.getEmail();

    return ResponseEntity.ok(email);

  }



}
