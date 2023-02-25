package dnd.danverse.domain.review.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 리뷰 작성자가 아닌 사용자가 리뷰를 수정하거나 삭제하려고 할 때 발생하는 예외.
 */
public class NotReviewWriterException extends BusinessException {
  public NotReviewWriterException(ErrorCode errorCode) {
    super(errorCode);
  }
}
