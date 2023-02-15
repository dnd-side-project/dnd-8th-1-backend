package dnd.danverse.domain.event.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이벤트 글을 작성할 때 프로필 미등록이면 발생하는 예외.
 */
public class NoProfileException extends BusinessException {
  public NoProfileException(ErrorCode errorCode) {
    super(errorCode);
  }
}
