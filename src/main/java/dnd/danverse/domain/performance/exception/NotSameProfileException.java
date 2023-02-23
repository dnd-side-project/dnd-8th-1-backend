package dnd.danverse.domain.performance.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 글 작성자와 수정 요청 사용자가 동일하지 않으면 예외가 발생한다.
 */
public class NotSameProfileException extends BusinessException {
  public NotSameProfileException(ErrorCode errorCode) {
    super(errorCode);
  }
}
