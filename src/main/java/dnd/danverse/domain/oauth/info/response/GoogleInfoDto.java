package dnd.danverse.domain.oauth.info.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * {
 *  "sub": "110248495921238986420",
 *  "name": "Aaron Parecki",
 *  "given_name": "Aaron",
 *  "family_name": "Parecki",
 *  "picture": "https://lh4.googleusercontent.com/-kw-iMgD
 *    _j34/AAAAAAAAAAI/AAAAAAAAAAc/P1YY91tzesU/photo.jpg",
 *  "email": "aaron.parecki@okta.com",
 *  "email_verified": true,
 *  "locale": "en",
 *  "hd": "okta.com"
 * }
 */
@Getter
@NoArgsConstructor
public class GoogleInfoDto {

  private String locale;
  private String email;
  private String picture;
  private String name;
  private String sub;

  public Map<String, Object> getAttributes() {
    Map<String, Object> attributes = new HashMap<>();
    attributes.put("locale", locale);
    attributes.put("email", email);
    attributes.put("picture", picture);
    attributes.put("name", name);
    attributes.put("sub", sub);
    return attributes;
  }

}
