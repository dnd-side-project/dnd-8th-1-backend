package dnd.danverse.domain.matching.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이벤트 신청이 존재하지 않을 때 발생하는 예외
 */
public class EventMatchNotFoundException extends BusinessException {

  public EventMatchNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
