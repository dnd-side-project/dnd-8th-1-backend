package dnd.danverse.domain.jwt.service;

import dnd.danverse.domain.member.entity.Member;
import java.io.Serializable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Spring Security Context Holder 에 들어갈 객체는 Serializable 을 구현해야 한다.
 */
@Getter
@RequiredArgsConstructor
public class SessionUser implements Serializable {

  private final Long id;
  private final String name;
  private final String email;
  private final String username;
  private final String password;
  private final String profileImage;
  private final String authority;
  private final String provider;

  public SessionUser(Member member) {
    this.id = member.getId();
    this.name = member.getName();
    this.email = member.getEmail();
    this.username = member.getUsername();
    this.password = member.getPassword();
    this.profileImage = member.getProfileImage();
    this.authority = member.getRole().getAuthority();
    this.provider = member.getOauth2Provider().name();
  }

}
