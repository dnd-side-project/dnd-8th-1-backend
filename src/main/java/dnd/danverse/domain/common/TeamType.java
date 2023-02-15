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


  public static TeamType of(String type) {

    for (TeamType teamType : TeamType.values()) {
      if (teamType.getType().equals(type)) {
        return teamType;
      }
    }
    // TODO : TypeNotSupported Exception 으로 변경 필요 (작성일 : 2023-02-13 03:00)
    throw new IllegalArgumentException("해당 type은 존재하지 않습니다. type=" + type);
  }
}
