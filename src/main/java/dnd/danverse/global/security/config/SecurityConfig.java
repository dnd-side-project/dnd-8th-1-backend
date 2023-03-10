package dnd.danverse.global.security.config;

import dnd.danverse.domain.jwt.exception.JwtExceptionFilter;
import dnd.danverse.domain.jwt.filter.JwtAuthenticationFilter;
import dnd.danverse.domain.jwt.handler.JwtAccessDeniedHandler;
import dnd.danverse.domain.jwt.handler.JwtAuthenticationEntryPointHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * Spring Security 설정 권한이 필요한 요청에 대해서 정의할 수 있게 한다.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPointHandler jwtAuthenticationEntryPointHandler;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtExceptionFilter jwtExceptionFilter;

  /**
   * 인증이 필요한 부분에 대해서만 권한 설정을 하고 나머지는 모두 허용한다.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPointHandler) // 인증 되지 않는 사용자가 접근할 경우
        .accessDeniedHandler(jwtAccessDeniedHandler); // 인증은 했으나 권한이 없는 경우

    final String userRole = "ROLE_USER";
    http.authorizeRequests()

        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() //PreFlight 요청 무시하기

        .antMatchers("/api/manager/resource").hasAuthority("ROLE_MANAGER")
        .antMatchers(HttpMethod.POST, "/api/v1/events", "/api/v1/events/apply",
            "/api/v1/events/image", "/api/v1/performances/image", "/api/v1/profiles/image", "/api/v1/performances",
            "/api/v1/performances/{performId}/reviews", "/api/v1/profiles")
          .hasAuthority(userRole)
        .antMatchers(HttpMethod.DELETE, "/api/v1/events/{eventId}/cancel-apply", "/api/v1/events/{eventId}", "/api/v1/performances/{performId}"
        ,"/api/v1/performances/reviews/{reviewId}", "/api/v1/profiles", "/api/v1/member")
          .hasAuthority(userRole)
        .antMatchers(HttpMethod.GET, "/api/v1/events/{eventId}/applicants", "/api/v1/mypage/performances/reviews", "/api/v1/mypage/performances",
            "/api/v1/mypage/events", "/api/v1/mypage/events/applications", "/api/v1/member/info", "/api/v1/member/logout").hasAuthority(userRole)
        .antMatchers(HttpMethod.PATCH, "/api/v1/events/{eventId}/accept", "/api/v1/events/deadline", "/api/v1/performances",
            "/api/v1/performances/reviews")
          .hasAuthority(userRole)
        .anyRequest().permitAll();

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);

    return http.build();
  }




}

