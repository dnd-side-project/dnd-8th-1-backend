package dnd.danverse.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 프로필 유형.
 */
@Getter
@RequiredArgsConstructor
public enum TeamType {
  INDIVIDUAL("개인"), TEAM("팀");

  private final String type;


}
