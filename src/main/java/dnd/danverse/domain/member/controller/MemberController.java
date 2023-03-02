package dnd.danverse.domain.member.controller;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import dnd.danverse.domain.jwt.service.SessionUser;
import dnd.danverse.domain.member.dto.response.MemberResponse;
import dnd.danverse.domain.member.service.MemberInfoSearchService;
import dnd.danverse.domain.member.service.MemberLogOutService;
import dnd.danverse.domain.member.service.MemberWithDrawService;
import dnd.danverse.domain.oauth.dto.OAuth2LoginResponseDTO;
import dnd.danverse.domain.oauth.service.OAuth2Service;
import dnd.danverse.global.response.DataResponse;
import dnd.danverse.global.response.MessageResponse;
import dnd.danverse.global.util.CookieUtil;
import dnd.danverse.global.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 회원 관련 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Api(tags = "MemberController : 멤버 관련 API")
public class MemberController {

  private final OAuth2Service oAuth2Service;
  private final MemberInfoSearchService memberInfoSearchService;
  private final MemberWithDrawService memberWithDrawService;
  private final MemberLogOutService memberLogOutService;

  /**
   * 클라이언트 Request 으로부터 Header 에 있는 Authorization 에 담긴 토큰을 받아서
   * 회원 가입을 하고, JWT 토큰을 생성하여 반환한다.
   *
   * @return Header 에 Access Token , Cookie 에 Refresh Token 을 담아서 반환한다.
   */
  @GetMapping(value = "/oauth/google/login")
  @ApiOperation(value = "구글 연동 로그인을 통한 회원가입", notes = "발급받은 구글 토큰으로 회원가입 혹은 로그인을 할 수 있습니다.")
  @ApiImplicitParam(name = "google-token", value = "구글 서비스로부터 발급받은 토큰(header의 key는 google-token 입니다)", required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<MemberResponse>> oauth2Login(@RequestHeader("google-token") String googleToken) {
    OAuth2LoginResponseDTO responseDto = oAuth2Service.oauth2Login(googleToken);

    HttpHeaders headers = new HttpHeaders();
    CookieUtil.setRefreshCookie(headers, responseDto.getRefreshToken());
    HttpHeaderUtil.setAccessToken(headers, responseDto.getAccessToken());

    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED,
        "회원 가입 및 로그인 성공", responseDto.getMemberResponse()), headers, HttpStatus.CREATED);
  }

  @GetMapping("resource")
  public ResponseEntity<String> resource(@AuthenticationPrincipal SessionUser user) {

    String email = user.getEmail();

    return ResponseEntity.ok(email);

  }

  /**
   * 액세스 토큰을 통해서 본인의 정보를 모두 조회할 수 있습니다.
   *
   * @param sessionUser sessionUser.
   * @return 사용자의 정보를 담은 Dto.
   */
  @GetMapping("/info")
  @ApiOperation(value = "본인의 정보 조회", notes = "액세스 토큰을 통해 본인의 정보를 받을 수 있습니다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<DataResponse<MemberResponse>> searchMyInfo(@AuthenticationPrincipal SessionUser sessionUser) {
    MemberResponse memberResponse = memberInfoSearchService.searchMyInfo(sessionUser.getId());
    return new ResponseEntity<>(DataResponse.of(HttpStatus.OK, "본인 정보 조회 성공", memberResponse), HttpStatus.OK);
  }

  /**
   * 회원 탈퇴를 할 수 있다.
   *
   */
  @DeleteMapping()
  @ApiOperation(value = "회원 탈퇴", notes = "회원 탈퇴를 할 수 있습니다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<MessageResponse> withdrawMember(@AuthenticationPrincipal SessionUser sessionUser) {
    memberWithDrawService.withdrawMember(sessionUser.getId());
    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "회원 탈퇴 성공"), HttpStatus.OK);
  }


  /**
   * 로그아웃을 할 수 있다.
   *
   * @param request request 로 부터 Access Token 을 받아온다.
   * @param sessionUser API 요청을 한 사용자의 정보를 받아온다.
   * @return 로그아웃 성공 메시지.
   */
  @GetMapping("/logout")
  @ApiOperation(value = "로그아웃", notes = "로그아웃을 할 수 있습니다.")
  @ApiImplicitParam(name = "Authorization", value = "Bearer access_token (서버에서 발급한 access_token)",
      required = true, dataType = "string", paramType = "header")
  public ResponseEntity<MessageResponse> logout(HttpServletRequest request, @AuthenticationPrincipal SessionUser sessionUser) {

    String accessToken = request.getHeader(AUTHORIZATION).substring(7);

    memberLogOutService.logout(accessToken, sessionUser.getEmail());

    HttpHeaders headers = new HttpHeaders();
    CookieUtil.resetRefreshToken(headers);

    return new ResponseEntity<>(MessageResponse.of(HttpStatus.OK, "로그아웃 성공"), headers, HttpStatus.OK);
  }




}
