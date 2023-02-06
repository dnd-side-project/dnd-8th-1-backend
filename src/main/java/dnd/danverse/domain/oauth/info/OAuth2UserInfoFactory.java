package dnd.danverse.domain.oauth.info;

import static dnd.danverse.domain.oauth.info.OAuth2Provider.GOOGLE;

import dnd.danverse.domain.oauth.info.impl.GoogleOAuth2UserInfo;
import java.util.Map;

/**
 * 소셜 로그인 서비스 제공자에 따라 OAuth2UserInfo 의 구현체를 반환한다.
 */
public class OAuth2UserInfoFactory {

  private OAuth2UserInfoFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static OAuth2UserInfo getOAuth2Userinfo(OAuth2Provider oauth2Provider,
      Map<String, Object> attributes) {

    if (oauth2Provider == GOOGLE) {
      return new GoogleOAuth2UserInfo(attributes, GOOGLE);
    }
    throw new IllegalArgumentException("Invalid OAuth2Provider");
  }
}

