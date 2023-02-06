package dnd.danverse.global.exception;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception 을 Handling 하는 클래스
 * Controller 까지 올라온 Exception 을 처리한다.
 * Spring Security FilterChain 에서 발생하는 Exception 은 여기서 처리하지 않는다.
 */
@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {


  /**
   * javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
   * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
   * 주로 @RequestBody, @RequestPart 어노테이션에서 발생
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("MethodArgumentNotValidException", ex);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, ex.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
   * 주로 int 형으로 받아야하는데 문자열로 받을 경우 발생
   */
  @ExceptionHandler(HttpMessageConversionException.class)
  protected ResponseEntity<ErrorResponse> handleHttpMessageConversionException(HttpMessageConversionException ex) {
    log.error("HttpMessageConversionException", ex);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_TYPE_VALUE);
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  /**
   * ModelAttribute 으로 binding error 발생시 BindException 발생한다.
   */
  @ExceptionHandler(BindException.class)
  protected ResponseEntity<ErrorResponse> handleBindException(BindException ex) {
    log.error("BindException", ex);
    final ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, ex.getBindingResult());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }


  /**
   * 개발자가 정의한 사용자 Error 를 처리한다.
   * 사용자가 정의한 Exception 이 만약 BusinessException 을 상속하고 있다면,
   * 해당 Handler 가 처리하게 된다.
   * 개발자가 정의한 HttpStatus 에 맞게 처리한다.
   * 개발자가 정의한 Exception 은 Warn 로그를 가진다.
   */
  @ExceptionHandler(BusinessException.class)
  protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e) {
    log.warn("BusinessException", e);
    final ErrorCode errorCode = e.getErrorCode();
    final ErrorResponse response = ErrorResponse.of(errorCode);
    return new ResponseEntity<>(response, HttpStatus.valueOf(errorCode.getStatus()));
  }



}
