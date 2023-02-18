package dnd.danverse.domain.matching.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이미 하나의 이벤트에 대해 신청한 이력이 있어 신청할 수 없는 경우 발생하는 예외
 */
public class AlreadyAppliedException extends BusinessException {

  public AlreadyAppliedException(ErrorCode errorCode) {
    super(errorCode);
  }
}
