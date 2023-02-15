package dnd.danverse.domain.oauth.service;

import dnd.danverse.domain.member.entity.Role;
import dnd.danverse.domain.member.service.SignUpResult;
import dnd.danverse.domain.oauth.dto.OAuth2LoginResponseDTO;
import dnd.danverse.domain.oauth.info.OAuth2UserInfo;
import dnd.danverse.domain.jwt.service.JwtTokenProvider;
import dnd.danverse.global.redis.service.RedisService;
import dnd.danverse.domain.member.dto.response.MemberResponse;
import dnd.danverse.domain.member.service.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * OAuth2 로그인을 위한 서비스 클래스. 1. google 에서 받은 googleToken 을 통해서 google service 에게 유저 정보를 요청한다. 2.
 * google service 에서 받은 유저 정보를 통해서 member 를 저장하거나 업데이트한다. 3. 생성된 member 를 통해서 jwt 토큰을 생성한다. 4. 생성된
 * jwt 토큰을 쿠키와 헤더에 담아서 클라이언트에게 전달한다.
 */
@Service
@RequiredArgsConstructor
public class OAuth2Service {

  private final HttpSocialLoginRequest httpSocialLoginRequest;
  private final MemberSignUpService memberSignUpService;
  private final JwtTokenProvider tokenProvider;
  private final RedisService redisService;

  @Value("${google.user.info.url}")
  private String googleUserInfoUrl;


  /**
   * googleToken 을 통해서 google service 에게 유저 정보를 요청하고, member 를 생성하거나 업데이트하고, jwt 토큰을 발급한다.
   *
   * @param googleToken : client 에서 받은 googleToken
   * @return : jwt 토큰을 담은 response
   */
  public OAuth2LoginResponseDTO oauth2Login(String googleToken) {

    OAuth2UserInfo userInfo = getUserInfoFromGoogle(googleToken);
    SignUpResult signUpResult = memberSignUpService.signUpOrUpdate(userInfo);
    String email = getEmail(signUpResult);
    String accessToken = createAccessToken(email, signUpResult.getMember().getRole());
    String refreshToken = createRefreshToken();
    saveRefreshTokenToRedis(email, refreshToken);

    MemberResponse memberResponse = new MemberResponse(signUpResult);
    return new OAuth2LoginResponseDTO(accessToken, refreshToken, memberResponse);
  }



  private void saveRefreshTokenToRedis(String email,String refreshToken) {
    redisService.saveRefreshToken(email, refreshToken);
  }

  private String createRefreshToken() {
    return tokenProvider.createRefreshToken();
  }

  private String createAccessToken(String email, Role role) {
    return tokenProvider.createAccessToken(email, role);
  }

  private OAuth2UserInfo getUserInfoFromGoogle(String googleToken) {
    String urlInfo = String.format(googleUserInfoUrl, googleToken);
    return httpSocialLoginRequest.getUserInfo(urlInfo);
  }

  /**
   * SignUpResult 에서 email 을 추출한다.
   *
   * @param signUpResult 회원 가입 결과
   * @return email
   */
  private String getEmail(SignUpResult signUpResult) {
    return signUpResult.getMember().getEmail();
  }


}
