package dnd.danverse.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자의 권한을 담는 Enum.
 */
@Getter
@RequiredArgsConstructor
public enum Role {

  USER_PROFILE_YES("ROLE_USER_PROFILE_YES"),
  USER_PROFILE_NO("ROLE_USER_PROFILE_NO");

  private final String authority;
}
