package dnd.danverse.domain.common;

import static dnd.danverse.global.exception.ErrorCode.*;

import dnd.danverse.domain.event.exception.TypeNotSupportException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자 프로필 유형.
 */
@Getter
@RequiredArgsConstructor
public enum TeamType {
  INDIVIDUAL("댄서"), TEAM("댄스팀");

  private final String type;


  public static TeamType of(String type) {

    for (TeamType teamType : TeamType.values()) {
      if (teamType.getType().equals(type)) {
        return teamType;
      }
    }
    throw new TypeNotSupportException(TYPE_NOT_FOUND);
  }
}
