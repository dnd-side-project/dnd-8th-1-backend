package dnd.danverse.domain.event.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이벤트를 찾지 못 했을 경우 발생하는 예외
 */
public class EventNotFoundException extends BusinessException {

  public EventNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
