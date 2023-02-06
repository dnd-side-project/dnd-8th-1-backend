package dnd.danverse.domain.oauth.info;

import java.util.Map;

/**
 * 소셜 로그인 서비스를 통해 받은 정보를 담는다.
 */
public abstract class OAuth2UserInfo {

  protected Map<String, Object> attributes;

  protected OAuth2Provider oauth2Provider;

  protected OAuth2UserInfo(Map<String, Object> attributes, OAuth2Provider oauth2Provider) {
    this.attributes = attributes;
    this.oauth2Provider = oauth2Provider;
  }


  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public abstract String getId();

  public abstract String getName();

  public abstract String getEmail();

  public abstract String getImageUrl();

  public OAuth2Provider getOauth2Provider() {
    return oauth2Provider;
  }
}

