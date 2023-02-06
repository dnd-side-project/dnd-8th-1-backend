package dnd.danverse.domain.oauth.info.impl;

import dnd.danverse.domain.oauth.info.OAuth2Provider;
import dnd.danverse.domain.oauth.info.OAuth2UserInfo;
import java.util.Map;

/**
 * 구글 로그인 서비스로부터 받은 사용자 정보를 담는 클래스
 */
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

  public GoogleOAuth2UserInfo(Map<String, Object> attributes, OAuth2Provider provider) {
    super(attributes, provider);
  }

  @Override
  public String getId() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getImageUrl() {
    return (String) attributes.get("picture");
  }
}
