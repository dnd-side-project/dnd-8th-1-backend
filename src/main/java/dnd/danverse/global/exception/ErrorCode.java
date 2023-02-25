package dnd.danverse.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ErrorCode 를 정의하는 Enum Class 이다. ErrorCode 는 HttpStatus, ErrorCode, ErrorMessage 를 가진다.
 */
@Getter
public enum ErrorCode {

  // Common (공통적으로 사용)
  INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "값이 올바르지 않습니다."),
  METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "지원하지 않는 Http Method 입니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 에러"),
  INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C004", "입력 값의 타입이 올바르지 않습니다."),
  HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C005", "접근이 거부 되었습니다."),

  // Depend on Entity (도메인에 따라서 달라지는 경우)
  // 이벤트
  EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "E001", "이메일 중복 입니다."),
  EVENT_NOT_FOUND(HttpStatus.NOT_FOUND, "E002", "존재하지 않는 이벤트입니다."),
  EVENT_OVER_DEADLINE(HttpStatus.BAD_REQUEST, "E003", "신청 기간이 지났습니다."),
  EVENT_TYPE_NOT_MATCH(HttpStatus.BAD_REQUEST, "E004", "모집 유형이 일치하지 않습니다."),

  // 이벤트 매치
  EVENT_MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "EM001", "지원자는 해당 이벤트에 지원한 기록이 없습니다."),

  EVENT_MATCH_NOT_WRITER(HttpStatus.FORBIDDEN, "EM002", "작성자 외에 접근권한이 없습니다."),

  EVENT_ALREADY_APPLIED(HttpStatus.BAD_REQUEST, "EM003", "이미 지원한 이벤트입니다. 중복으로 지원할 수 없습니다."),

  // 공연
  PERFORMANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "PF001", "존재하지 않는 공연 정보입니다."),

  PERFORMANCE_NOT_WRITER(HttpStatus.FORBIDDEN, "PF002", "작성자 외에 접근 권한이 없습니다."),

  // 회원 member
  MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "존재하지 않는 회원입니다."),

  // 프로필
  PROFILE_NOT_FOUND(HttpStatus.BAD_REQUEST, "P001", "프로필 등록이 필요한 서비스입니다."),
  PROFILE_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "P002", "이미 프로필이 존재합니다."),

  // Enum Type
  TYPE_NOT_FOUND(HttpStatus.BAD_REQUEST, "T001", "해당 타입은 존재하지 않습니다."),


  // JWT (Json Web Token)
  JWT_INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "J001", "유효하지 않은 토큰입니다."),
  JWT_EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "J002", "만료된 토큰입니다."),
  JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "J003", "만료된 리프레시 토큰입니다."),

  // 리소스 요청에 대한 권한이 없는 경우
  RESOURCE_FORBIDDEN(HttpStatus.FORBIDDEN, "R001", "해당 리소스에 대한 권한이 없습니다."),
  RESOURCE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "R002", "인증이 필요합니다. 로그인을 해주세요."),

  // 소셜 로그인 서비스에 access token 을 통해 사용자 데이터 요청하는데 실패한 경우
  SOCIAL_ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "S001", "유효하지 않은 소셜 로그인 토큰입니다."),

  // S3를 사용해 이미지를 업로드할 때
  IMAGE_WRONG_FILE_FORMAT(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "I001", "파일 형식이 잘못되었습니다.");


  private final HttpStatus status;
  private final String code;
  private final String message;

  public int getStatus() {
    return this.status.value();
  }

  ErrorCode(HttpStatus status, String code, String message) {
    this.status = status;
    this.code = code;
    this.message = message;
  }

}
