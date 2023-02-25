package dnd.danverse.domain.profile.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * 프로필을 등록할 때 프로필이 이미 존재하면 예외가 발생한다.
 */
public class ProfileAlreadyException extends BusinessException {
  public ProfileAlreadyException(ErrorCode errorCode) {
    super(errorCode);
  }
}
