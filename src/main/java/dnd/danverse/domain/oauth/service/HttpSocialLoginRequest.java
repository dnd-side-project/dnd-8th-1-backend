package dnd.danverse.domain.oauth.service;

import static dnd.danverse.global.exception.ErrorCode.SOCIAL_ACCESS_TOKEN_INVALID;
import static dnd.danverse.domain.oauth.info.OAuth2Provider.GOOGLE;

import dnd.danverse.domain.oauth.exception.SocialLoginFailedException;
import dnd.danverse.domain.oauth.info.response.GoogleInfoDto;
import dnd.danverse.domain.oauth.info.OAuth2UserInfo;
import dnd.danverse.domain.oauth.info.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 소셜 로그인 서비스로 access token 을 보내고, 사용자 정보를 받아오는 클래스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HttpSocialLoginRequest {

  private final RestTemplate restTemplate;

  @Value("${google.user.info.url}")
  private final String googleUserInfoUrl;



  // https://www.googleapis.com/oauth2/v3/userinfo?access_token=
  public OAuth2UserInfo getUserInfo(String googleToken) {

    GoogleInfoDto dto;
    try {
      dto = restTemplate.getForObject(googleUserInfoUrl + googleToken,
          GoogleInfoDto.class);
      assert dto != null;
    } catch (Exception e) {
      log.error("Google API 호출 실패");
      throw new SocialLoginFailedException(SOCIAL_ACCESS_TOKEN_INVALID);
    }

    return OAuth2UserInfoFactory.getOAuth2Userinfo(GOOGLE, dto.getAttributes());
  }
}
