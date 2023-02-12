package dnd.danverse.domain.event.entitiy;


import static org.springframework.util.StringUtils.hasText;

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

  /**
   * client 로 부터 넘겨 받은 String type 을 enum 으로 변환
   * enum에 해당 type이 없을 경우 IllegalArgumentException 발생
   * null 혹은 "" 빈 문자열일 경우 null 반환하여 동적 쿼리에 활용될 수 있도록 한다.
   * @param type client로 부터 넘겨 받은 String type
   * @return enum type
   */
  public static EventType of(String type) {

    if (!hasText(type)) {
      return null;
    }

    for (EventType eventType : EventType.values()) {
      if (eventType.getType().equals(type)) {
        return eventType;
      }
    }
    // TODO : TypeNotSupported Exception 으로 변경 필요 (작성일 : 2023-02-13 03:00)
    throw new IllegalArgumentException("해당 type은 존재하지 않습니다. type=" + type);
  }
}
