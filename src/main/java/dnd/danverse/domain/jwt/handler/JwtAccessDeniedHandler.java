package dnd.danverse.domain.jwt.handler;

import static dnd.danverse.global.exception.ErrorCode.RESOURCE_FORBIDDEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.danverse.global.exception.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * AccessDeniedHandler 는 Spring Security 에서 접근 권한이 없을 때 실행되는 핸들러입니다.
 * 접근 권한이 없을 때 403 상태 코드를 응답하고 JSON 형식의 메시지를 응답합니다.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType("application/json;charset=UTF-8");
    objectMapper.writeValue(response.getWriter(), ErrorResponse.of(RESOURCE_FORBIDDEN));
  }
}
