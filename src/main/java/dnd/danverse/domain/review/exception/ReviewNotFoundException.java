package dnd.danverse.domain.review.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 후기를 찾을 수 없을 때 발생하는 예외
 */
public class ReviewNotFoundException extends BusinessException {

  public ReviewNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
