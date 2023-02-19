package dnd.danverse.domain.performance.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 조회하고자 하는 공연 정보글이 없을 때 예외처리.
 */
public class PerformanceNotFoundException extends BusinessException {
  public PerformanceNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
