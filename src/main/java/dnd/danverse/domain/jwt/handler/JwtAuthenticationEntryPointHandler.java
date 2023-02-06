package dnd.danverse.domain.jwt.handler;

import static dnd.danverse.global.exception.ErrorCode.RESOURCE_UNAUTHORIZED;

import com.fasterxml.jackson.databind.ObjectMapper;
import dnd.danverse.global.exception.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * AuthenticationEntryPoint는 Spring Security에서 인증이 필요한 요청에 대해 인증을 시작하는 진입점입니다. 인증이 필요한 요청에 대해 401 상태
 * 코드를 응답하고 JSON 형식의 메시지를 응답합니다.
 */
@Component
public class JwtAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json;charset=UTF-8");
    objectMapper.writeValue(response.getWriter(), ErrorResponse.of(RESOURCE_UNAUTHORIZED));
  }
}
