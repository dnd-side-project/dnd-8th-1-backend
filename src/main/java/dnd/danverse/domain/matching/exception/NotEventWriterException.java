package dnd.danverse.domain.matching.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 이벤트 신청자 리스트를 조회하려는 사용자와 이벤트 작성자가 다르는 403 에러가 나타난다.
 */
public class NotEventWriterException extends BusinessException {
  public NotEventWriterException(ErrorCode errorCode) {
    super(errorCode);
  }
}
