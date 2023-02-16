package dnd.danverse.domain.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자의 권한을 담는 Enum.
 */
@Getter
@RequiredArgsConstructor
public enum Role {

  ROLE_USER("ROLE_USER");

  private final String authority;
}
