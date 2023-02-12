package dnd.danverse.domain.event.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이벤트 (콜라보, 쉐어) Type 이 지원되지 않는 경우 발생하는 예외
 */
public class TypeNotSupportException extends BusinessException {

  protected TypeNotSupportException(ErrorCode errorCode) {
    super(errorCode);
  }
}
