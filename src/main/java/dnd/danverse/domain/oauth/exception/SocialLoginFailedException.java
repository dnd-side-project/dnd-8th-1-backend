package dnd.danverse.domain.oauth.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

/**
 * Client 로 넘겨 받은 구글 로그인 서비스를 위한 Access Token 이 유효하지 않을 경우 발생하는 Exception
 */
public class SocialLoginFailedException extends BusinessException {

  public SocialLoginFailedException(ErrorCode errorCode) {
    super(errorCode);
  }

}
