package dnd.danverse.domain.oauth.service;

import dnd.danverse.domain.oauth.info.OAuth2UserInfo;
import dnd.danverse.global.util.HttpHeaderUtil;
import dnd.danverse.global.response.DataResponse;
import dnd.danverse.domain.jwt.service.JwtTokenProvider;
import dnd.danverse.global.redis.service.RedisService;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.dto.response.MemberResponse;
import dnd.danverse.domain.member.service.MemberSignUpService;
import dnd.danverse.global.util.CookieUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OAuth2 로그인을 위한 서비스 클래스.
 * 1. google 에서 받은 googleToken 을 통해서 google service 에게 유저 정보를 요청한다.
 * 2. google service 에서 받은 유저 정보를 통해서 member 를 저장하거나 업데이트한다.
 * 3. 생성된 member 를 통해서 jwt 토큰을 생성한다.
 * 4. 생성된 jwt 토큰을 쿠키와 헤더에 담아서 클라이언트에게 전달한다.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OAuth2Service {

  private final HttpSocialLoginRequest httpSocialLoginRequest;
  private final MemberSignUpService memberSignUpService;
  private final JwtTokenProvider tokenProvider;
  private final RedisService redisService;

  @Value("${google.user.info.url}")
  private String googleUserInfoUrl;


  @Transactional
  public ResponseEntity<DataResponse<MemberResponse>> oauth2Login(String googleToken) {

    String urlInfo = String.format(googleUserInfoUrl, googleToken);

    // httpRequestUtil 를 통해서 google service 에게 googleToken 을 보내자.
    OAuth2UserInfo userInfo = httpSocialLoginRequest.getUserInfo(urlInfo);

    // google 에서 받은 정보를 통해서 member 를 생성하거나 업데이트하자.
    Map<String, Object> map = memberSignUpService.signUpOrUpdate(userInfo);
    Member member = (Member) map.get("member");
    boolean isSignUp = (boolean) map.get("isSignUp");

    // JWT 토큰을 발급해야 한다.
    // 해당 정보를 통해서 회원 가입을 진행하며, 회원 가입이 완료되면 jwt 토큰을 만들어서 response 에 넣어준다.
    String accessToken = tokenProvider.createAccessToken(member.getEmail());
    String refreshToken = tokenProvider.createRefreshToken();

    // refresh token은 redis server에 저장해야 한다.
    redisService.saveRefreshToken(refreshToken, member.getEmail());

    DataResponse<MemberResponse> response = DataResponse.of(HttpStatus.CREATED,
        "회원 가입 및 로그인 성공", new MemberResponse(member,isSignUp));

    // cookie 에 refresh token 을 저장해야 한다, 그리고 access token 을 header 에 저장해야 한다.
    HttpHeaders httpHeaders = CookieUtil.setRefreshCookie(refreshToken);
    HttpHeaderUtil.setAccessToken(httpHeaders, accessToken);

    return new ResponseEntity<>(response, httpHeaders, HttpStatus.CREATED);
  }




}
