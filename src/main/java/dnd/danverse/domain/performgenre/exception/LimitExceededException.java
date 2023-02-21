package dnd.danverse.domain.performgenre.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 장르를 3개 초과하여 선택하면 예외 발생한다.
 */
public class LimitExceededException extends BusinessException {
  public LimitExceededException(ErrorCode errorCode) {
    super(errorCode);
  }
}
