package dnd.danverse.domain.jwt.exception;

import dnd.danverse.global.exception.ErrorCode;

/**
 * JWT 토큰 형식이 잘못되거나,
 * 만료된 경우에는 HTTP status code 401 (Unauthorized)를 사용하는 것이 적절합니다.
 * 이는 토큰이 올바르지 않거나 만료되었음을 의미합니다.
 */
public class JwtException extends RuntimeException{

  private final ErrorCode errorCode;

  public JwtException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
