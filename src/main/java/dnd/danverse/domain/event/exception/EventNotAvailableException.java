package dnd.danverse.domain.event.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이벤트 신청이 불가능한 경우 발생하는 예외
 */
public class EventNotAvailableException extends BusinessException {

  public EventNotAvailableException(ErrorCode errorCode) {
    super(errorCode);
  }
}
