package dnd.danverse.global.response;

import lombok.Getter;

/**
 * 공통된 응답 형식을 제공하기 위한 응답 DTO .
 */
@Getter
public abstract class DefaultResponse {

  protected int status;
  protected String message;

  protected DefaultResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

}
