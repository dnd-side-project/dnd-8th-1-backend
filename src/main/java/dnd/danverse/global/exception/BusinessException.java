package dnd.danverse.global.exception;

import lombok.Getter;

/**
 * 사용자 정의 Exception 을 만들기 위해서는
 * 해당 Business Exception 을 상속 받아야 한다.
 */
@Getter
public abstract class BusinessException extends RuntimeException{

  private final ErrorCode errorCode;

  protected BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
