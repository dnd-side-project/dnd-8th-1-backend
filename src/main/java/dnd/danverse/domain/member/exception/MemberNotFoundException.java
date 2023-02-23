package dnd.danverse.domain.member.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 회원을 찾지 못했을 때 발생하는 예외.
 */
public class MemberNotFoundException extends BusinessException {

  public MemberNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
