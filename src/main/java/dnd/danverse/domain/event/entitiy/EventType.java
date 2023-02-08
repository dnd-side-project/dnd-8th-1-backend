package dnd.danverse.domain.event.entitiy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 이벤트 활동 Type
 * 콜라보, 쉐어
 */
@Getter
@RequiredArgsConstructor
public enum EventType {

  COLLABORATION("콜라보"),
  SHARE("쉐어");

  private final String type;
}
